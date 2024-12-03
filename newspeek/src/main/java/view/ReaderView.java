package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import interface_adapter.ReaderState;
import interface_adapter.ReaderViewModel;
import interface_adapter.choose_rule_set.ChooseRuleSetController;
import interface_adapter.load_URL.LoadURLController;
import interface_adapter.load_article.LoadArticleController;
import interface_adapter.populate_list.PopulateListController;
import interface_adapter.random_article.RandomArticleController;
import interface_adapter.save_article.SaveArticleController;
import use_case.helpers.CensorshipService;

/**
 * The View for when the user is reading a censored article.
 */
public class ReaderView extends JPanel implements PropertyChangeListener {
    // Example: 150px width, 30px height
    public static final Dimension BUTTON_SIZE = new Dimension(170, 30);
    private static final int DEFAULT_TEXTAREA_ROWS = 20;
    private static final int DEFAULT_TEXTAREA_COLUMNS = 40;
    private static final int BUTTONS_PANEL_BOX_HEIGHT = 10;
    private static final int BUTTONS_PANEL_WIDTH = 220;
    private static final int DROPDOWN_WIDTH = 170;

    private static final String VIEW_NAME = "reader";

    private static final String DEFAULT_ARTICLE_TEXT = "Select an article...";
    private static final String UNCENSOR_ARTICLE_ACTION_STRING = "uncensorArticle";
    private static final String RECENSOR_ARTICLE_ACTION_STRING = "recensorArticle";

    // STYLE
    // Whole page
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final int BORDER_THICKNESS = 10;
    private static final Dimension WINDOW_SIZE = new Dimension(850, 800);
    private static final int ELEMENT_SPACING = 10;
    private static final Color TEXTAREA_BACKGROUND_COLOR = new Color(255, 242, 194);

    // NewsPeek title
    private static final Color NEWSPEEK_COLOR = new Color(70, 130, 180);
    private static final Font NEWSPEEK_TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 36);

    // Article title
    private static final Font ARTICLE_TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 20);

    // Article
    private static final Color ARTICLE_BACKGROUND_COLOR = new Color(255, 250, 240);
    private static final Font ARTICLE_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);

    // Buttons
    private static final Color BUTTONS_PANEL_BACKGROUND_COLOR = new Color(230, 230, 250);
    private static final Color BUTTON_BACKGROUND_COLOR = new Color(60, 130, 180);
    private static final LineBorder BUTTON_BORDER_STYLE = new LineBorder(new Color(6, 6, 56), 2, true);
    private static final Font BUTTON_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 14);
    private static final Color BUTTON_BORDER_COLOUR = new Color (6,6,56);
    // Censorship summary
    private static final Font CENSORSHIP_SUMMARY_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
    private static final Color CENSORSHIP_SUMMARY_COLOR = new Color(70, 130, 180);

    // Uncensored Notification
    private static final Font UNCENSORED_NOFITICATION_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
    private static final Color UNCENSORED_NOTIFICATION_COLOR = new Color(255, 30, 30);

    private final ReaderViewModel viewModel;

    // Use cases
    private RandomArticleController randomArticleController;
    private LoadURLController loadURLController;
    private ChooseRuleSetController chooseRuleSetController;
    private SaveArticleController saveArticleController;
    private LoadArticleController loadArticleController;
    private PopulateListController populateListController;

    // Swing objects
    private final JLabel title;
    private final JLabel articleTitle;
    private final JLabel censoredSummary;
    private final JLabel replacedSummary;
    private final JLabel uncensoredNotif;
    private final JTextArea articleTextArea;
    private final JFileChooser fileChooser;
    private final JComboBox<String> loadArticleDropdown;

    private final CensorshipService censorshipService;

    public ReaderView(ReaderViewModel viewModel, CensorshipService censorshipService) {
        this.fileChooser = new JFileChooser();
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        this.censorshipService = censorshipService;
        this.setLayout(new BorderLayout(ELEMENT_SPACING, ELEMENT_SPACING));
        this.setBackground(BACKGROUND_COLOR);
        this.setPreferredSize(WINDOW_SIZE);

        // Title Section
        this.title = new JLabel("Newspeek");
        styleNewspeekTitle();

        this.censoredSummary = new JLabel("Censored words:");
        this.replacedSummary = new JLabel("Replaced words:");
        styleCensorshipSummary();

        this.uncensoredNotif = new JLabel("UNCENSORED");
        uncensoredNotif.setFont(UNCENSORED_NOFITICATION_FONT);
        uncensoredNotif.setForeground(UNCENSORED_NOTIFICATION_COLOR);
        uncensoredNotif.setVisible(false);

        // Buttons Panel
        final JPanel buttonsPanel = getjPanel();

        // Create and initialize the dropdown (hidden by default)
        loadArticleDropdown = getStringJComboBox();

        // Load and style buttons
        JButton randomArticleButton = new JButton("Random Article");
        styleButton(randomArticleButton);

        JButton loadArticleFromURL = new JButton("Load from URL");
        styleButton(loadArticleFromURL);

        JButton saveArticleButton = new JButton("Save Article");
        styleButton(saveArticleButton);

        JButton loadArticleButton = new JButton("Load Article");
        styleButton(loadArticleButton);

        JButton loadRuleSetButton = new JButton("Load Ruleset");
        styleButton(loadRuleSetButton);

        styleDropdownMenu(loadArticleDropdown);

        loadArticleDropdown.addActionListener(evt -> {
            String selectedArticleTitle = (String) loadArticleDropdown.getSelectedItem();
            String selectedArticleId = getKeyByValue(this.viewModel.getState().getSavedArticleList(),
                    selectedArticleTitle);
            if (selectedArticleId != null && !DEFAULT_ARTICLE_TEXT.equals(selectedArticleId)) {
                // Hide the dropdown after selection
                loadArticleDropdown.setVisible(false);
                loadArticleController.execute(selectedArticleId);
            }
        });

        // Add components to the panel
        componentsAdded(buttonsPanel, randomArticleButton, loadArticleFromURL, saveArticleButton, loadArticleButton, loadRuleSetButton);

        // Article Title
        this.articleTitle = new JLabel("No article loaded");
        styleArticleTitle();

        // Article Text Area
        articleTextArea = new JTextArea(DEFAULT_TEXTAREA_ROWS, DEFAULT_TEXTAREA_COLUMNS);
        articleTextArea.setEditable(false);
        styleArticle();

        JScrollPane articleScrollPane = new JScrollPane(articleTextArea);
        articleScrollPane.setBorder(new EmptyBorder(
                BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS
        ));

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(ELEMENT_SPACING, ELEMENT_SPACING));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.add(articleTitle, BorderLayout.NORTH);
        contentPanel.add(articleScrollPane, BorderLayout.CENTER);

        // Add components to layout
        this.add(title, BorderLayout.NORTH);
        this.add(buttonsPanel, BorderLayout.EAST);
        this.add(contentPanel, BorderLayout.CENTER);

        // Button actions
        randomArticleButton.addActionListener(evt -> {
            String country = "us";
            randomArticleController.execute(country);
        });

        saveArticleButton.addActionListener(evt -> saveArticleController.execute(this.viewModel.getState().getArticle())
        );

        loadArticleFromURL.addActionListener(evt -> chooseURL());

        loadRuleSetButton.addActionListener(evt -> chooseRuleSet());

        InputMap inputmap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionmap = getActionMap();

        Action uncensorArticle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uncensoredNotif.setVisible(true);
                articleTitle.setText(viewModel.getState().getArticle().getTitle());
                articleTextArea.setText(viewModel.getState().getArticle().getText());
                inputmap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
                inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), RECENSOR_ARTICLE_ACTION_STRING);
            }
        };

        Action recensorArticle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uncensoredNotif.setVisible(false);
                articleTitle.setText(viewModel.getState().getCensoredArticle().getTitle());
                articleTextArea.setText(viewModel.getState().getCensoredArticle().getText());
                inputmap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
                inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), UNCENSOR_ARTICLE_ACTION_STRING);
            }
        };

        inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), UNCENSOR_ARTICLE_ACTION_STRING);

        actionmap.put(UNCENSOR_ARTICLE_ACTION_STRING, uncensorArticle);
        actionmap.put(RECENSOR_ARTICLE_ACTION_STRING, recensorArticle);

        loadArticleButton.addActionListener(evt -> toggleLoadArticleDropdown());
    }

    private void componentsAdded(JPanel buttonsPanel, JButton randomArticleButton, JButton loadArticleFromURL, JButton saveArticleButton, JButton loadArticleButton, JButton loadRuleSetButton) {
        buttonsPanel.add(randomArticleButton);
        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_PANEL_BOX_HEIGHT));
        buttonsPanel.add(loadArticleFromURL);
        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_PANEL_BOX_HEIGHT));
        buttonsPanel.add(saveArticleButton);
        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_PANEL_BOX_HEIGHT));
        buttonsPanel.add(loadArticleButton);
        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_PANEL_BOX_HEIGHT));
        buttonsPanel.add(loadArticleDropdown);
        buttonsPanel.add(Box.createVerticalStrut(BUTTONS_PANEL_BOX_HEIGHT));
        buttonsPanel.add(loadRuleSetButton);
        buttonsPanel.add(censoredSummary);
        buttonsPanel.add(replacedSummary);
        buttonsPanel.add(uncensoredNotif);
        Dimension panelSize = new Dimension(BUTTONS_PANEL_WIDTH, buttonsPanel.getPreferredSize().height);
        buttonsPanel.setPreferredSize(panelSize);
        buttonsPanel.setMaximumSize(panelSize);
        buttonsPanel.setMinimumSize(panelSize);
    }

    private JComboBox<String> getStringJComboBox() {
        final JComboBox<String> loadArticleDropdown;
        loadArticleDropdown = new JComboBox<>();
        // Initially hidden
        loadArticleDropdown.setVisible(false);
        loadArticleDropdown.addItem(DEFAULT_ARTICLE_TEXT);
        Dimension dropdownSize = new Dimension(DROPDOWN_WIDTH, loadArticleDropdown.getPreferredSize().height);
        loadArticleDropdown.setPreferredSize(dropdownSize);
        loadArticleDropdown.setMaximumSize(dropdownSize);
        loadArticleDropdown.setMinimumSize(dropdownSize);
        return loadArticleDropdown;
    }

    private static JPanel getjPanel() {
        final JPanel buttonsPanel = new JPanel();
        // Set to use vertical alignment
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(BUTTONS_PANEL_BACKGROUND_COLOR);
        buttonsPanel.setBorder(new EmptyBorder(
                BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS
        ));
        return buttonsPanel;
    }

    private void styleArticle() {
        articleTextArea.setFont(ARTICLE_FONT);
        articleTextArea.setBackground(ARTICLE_BACKGROUND_COLOR);
        articleTextArea.setBorder(new LineBorder(Color.GRAY, 2));
        articleTextArea.setLineWrap(true);
        articleTextArea.setWrapStyleWord(true);
        articleTextArea.setBackground(TEXTAREA_BACKGROUND_COLOR);
    }

    private void styleDropdownMenu(JComboBox articleDropdown) {
        articleDropdown.setFont(ARTICLE_FONT);
        articleDropdown.setBackground(BUTTON_BACKGROUND_COLOR);
        articleDropdown.setBorder(new LineBorder(BUTTON_BORDER_COLOUR, 2));
        articleDropdown.setForeground(Color.WHITE);
        articleDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        articleDropdown.setMaximumSize(BUTTON_SIZE);
        articleDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void styleArticleTitle() {
        articleTitle.setHorizontalAlignment(SwingConstants.CENTER);
        articleTitle.setFont(ARTICLE_TITLE_FONT);
        articleTitle.setBorder(new EmptyBorder(BORDER_THICKNESS, 0, BORDER_THICKNESS, 0));
    }

    private void styleCensorshipSummary() {
        censoredSummary.setFont(CENSORSHIP_SUMMARY_FONT);
        censoredSummary.setForeground(CENSORSHIP_SUMMARY_COLOR);
        replacedSummary.setFont(CENSORSHIP_SUMMARY_FONT);
        replacedSummary.setForeground(CENSORSHIP_SUMMARY_COLOR);
    }

    private void styleNewspeekTitle() {
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(NEWSPEEK_TITLE_FONT);
        title.setBorder(new EmptyBorder(BORDER_THICKNESS, 0, BORDER_THICKNESS, 0));
        title.setForeground(NEWSPEEK_COLOR);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("article")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            censorAndUpdateArticle(state);
        } else if (evt.getPropertyName().equals("ruleset")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, "Rule set loaded: " + state.getCensorshipRuleSet().getRuleSetName());
            censorAndUpdateArticle(state);
        } else if (evt.getPropertyName().equals("error")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            showError(state);
        } else if (evt.getPropertyName().equals("alert")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            showAlert(state);
        } else if (evt.getPropertyName().equals("article-list")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            updateDropdown(state);
        }
    }

    private void toggleLoadArticleDropdown() {
        // Toggle visibility of the dropdown
        boolean isVisible = loadArticleDropdown.isVisible();
        if (!isVisible) {
            populateListController.execute();
        }
        loadArticleDropdown.setVisible(!isVisible);
    }

    private void updateDropdown(ReaderState state) {
        loadArticleDropdown.removeAllItems();
        loadArticleDropdown.addItem(DEFAULT_ARTICLE_TEXT);
        Map<String, String> savedArticles = state.getSavedArticleList();
        for (Map.Entry<String, String> entry : savedArticles.entrySet()) {
            loadArticleDropdown.addItem(entry.getValue());
        }
    }

    /**
     * Getter for the current view name.
     * @return the name of the current view.
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Attach the controller for the Random Article use case.
     * Must be executed before showing the view to the user to prevent a program crash.
     * @param controller the controller to attach.
     */
    public void setRandomArticleController(RandomArticleController controller) {
        this.randomArticleController = controller;
    }

    /**
     * Attach the controller for the Load URL use case.
     * @param controller the controller to attach.
     */

    public void setLoadURLController(LoadURLController controller) {
        this.loadURLController = controller;
    }

    /**
     * Attach the controller for the Set Populate List use case.
     * @param controller the controller to attach.
     */
    public void setPopulateListController(PopulateListController controller) {
        this.populateListController = controller;
    }

    /**
     * Attach the controller for the Choose Rule Set use case.
     * Must be executed before showing the view to the user to prevent a program crash.
     * @param controller the controller to attach.
     */
    public void setChooseRuleSetController(ChooseRuleSetController controller) {
        this.chooseRuleSetController = controller;
    }

    /**
     * Attach the controller for the Load Article use case.
     * Must be executed before showing the view to the user to prevent a program crash.
     * @param controller the controller to attach.
     */
    public void setLoadArticleController(LoadArticleController controller) {
        this.loadArticleController = controller;
    }

    /**
     * Attach the controller for the Save Article use case.
     * Must be executed before showing the view to the user to prevent a program crash.
     * @param controller the controller to attach.
     */
    public void setSaveArticleController(SaveArticleController controller) {
        this.saveArticleController = controller;
    }

    /**
     * Handle a change to the article being displayed.
     * @param state the new state of the ReaderView.
     */
    private void censorAndUpdateArticle(ReaderState state) {
        if (state.getArticle() != null) {
            uncensoredNotif.setVisible(false);
            state.setCensoredArticle(censorshipService.censor(state.getArticle(), state.getCensorshipRuleSet()));
            articleTitle.setText(state.getCensoredArticle().getTitle());
            articleTextArea.setText(state.getCensoredArticle().getText());
            updateSummaryLabel(state);
        }
    }

    private void updateSummaryLabel(ReaderState state) {
        if (state.getArticle() != null) {
            censoredSummary.setText(" Censored words: " + state.getCensoredArticle().getCensoredWords());
            replacedSummary.setText(" Replaced words: " + state.getCensoredArticle().getReplacedWords());
        }
    }

    /**
     * Display a file chooser and load the censorship ruleset from the chosen file.
     */
    private void chooseRuleSet() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            chooseRuleSetController.execute(selectedFile);
        }
    }

    /**
     * Display a text box that allows the user to input a URL.
     */
    private void chooseURL() {
        String url = JOptionPane.showInputDialog("Enter URL");
        loadURLController.execute(url);
    }

    private void showError(ReaderState state) {
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAlert(ReaderState state) {
        if (state.getAlert() != null) {
            JOptionPane.showMessageDialog(this, state.getAlert(), "Alert", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_BACKGROUND_COLOR);
        button.setBorder(BUTTON_BORDER_STYLE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(BUTTON_SIZE);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        // macOS fix
        button.setOpaque(true);
    }

    /**
     * Get a key in a map by a value.
     * @param map the map to search for the key in.
     * @param value the value to use to search.
     * @return the key that matches the value, or null otherwise.
     */
    public String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        // Return null if no matching key is found
        return null;
    }
}
