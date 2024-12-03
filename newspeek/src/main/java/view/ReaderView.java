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

    private static final String VIEW_NAME = "reader";

    // STYLE
    // Whole page
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final int BORDER_THICKNESS = 10;
    private static final Dimension WINDOW_SIZE = new Dimension(850, 800);
    private static final int ELEMENT_SPACING = 10;
    private static final Color TEXTAREA_BACKGROUND_COLOR = new Color(255, 242, 194);

    // NewsPeek title
    private static final Color NEWSPEEK_COLOR = new Color(70, 130, 180);
    private static final Font NEWSPEEK_TITLE_FONT = new Font("SansSerif", Font.BOLD, 36);

    // Article title
    private static final Font ARTICLE_TITLE_FONT = new Font("SansSerif", Font.BOLD, 20);

    // Article
    private static final Color ARTICLE_BACKGROUND_COLOR = new Color(255, 250, 240);
    private static final Font ARTICLE_FONT = new Font("Monospaced", Font.PLAIN, 14);

    // Buttons
    private static final Color BUTTONS_PANEL_BACKGROUND_COLOR = new Color(230, 230, 250);
    private static final Color BUTTON_BACKGROUND_COLOR = new Color(60, 130, 180);
    private static final LineBorder BUTTON_BORDER_STYLE = new LineBorder(new Color(6, 6, 56), 2, true);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);

    // Censorship summary
    private static final Font CENSORSHIP_SUMMARY_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Color CENSORSHIP_SUMMARY_COLOR = new Color(70, 130, 180);

    // Uncensored Notification
    private static final Font UNCENSORED_NOFITICATION_FONT = new Font("SansSerif", Font.BOLD, 24);
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
        final JPanel buttonsPanel = new JPanel();
        // Set to use vertical alignment
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(BUTTONS_PANEL_BACKGROUND_COLOR);
        buttonsPanel.setBorder(new EmptyBorder(
                BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS
        ));

        // Create and initialize the dropdown (hidden by default)
        loadArticleDropdown = new JComboBox<>();
        loadArticleDropdown.setVisible(false); // Initially hidden
        loadArticleDropdown.addItem("Select an article...");
        Dimension dropdownSize = new Dimension(170, loadArticleDropdown.getPreferredSize().height);
        loadArticleDropdown.setPreferredSize(dropdownSize);
        loadArticleDropdown.setMaximumSize(dropdownSize);
        loadArticleDropdown.setMinimumSize(dropdownSize);

        // Initialize buttons
        JButton randomArticleButton = new JButton("Random Article");
        JButton loadArticleFromURL = new JButton("Load from URL");
        JButton saveArticleButton = new JButton("Save Article");
        JButton loadArticleButton = new JButton("Load Article");
        JButton loadRuleSetButton = new JButton("Load Ruleset");

        // Style buttons and set alignment
        styleButton(randomArticleButton);
        styleButton(loadArticleFromURL);
        styleButton(saveArticleButton);
        styleButton(loadArticleButton);
        styleButton(loadRuleSetButton);
        styleDropdownMenu(loadArticleDropdown);

        // Add components to the panel
        buttonsPanel.add(randomArticleButton);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(loadArticleFromURL);git
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(saveArticleButton);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(loadArticleButton);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(loadArticleDropdown);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(loadRuleSetButton);
        buttonsPanel.add(censoredSummary);
        buttonsPanel.add(replacedSummary);
        buttonsPanel.add(uncensoredNotif);
        Dimension panelSize = new Dimension(220, buttonsPanel.getPreferredSize().height);
        buttonsPanel.setPreferredSize(panelSize);
        buttonsPanel.setMaximumSize(panelSize);
        buttonsPanel.setMinimumSize(panelSize);

        loadArticleDropdown.addActionListener(evt -> {
            String selectedArticleTitle = (String) loadArticleDropdown.getSelectedItem();
            String selectedArticleId = getKeyByValue(this.viewModel.getState().getSavedArticleList(), selectedArticleTitle);
            if (selectedArticleId != null && !"Select an article...".equals(selectedArticleId)) {
                // Hide the dropdown after selection
                loadArticleDropdown.setVisible(false);
                loadArticleController.execute(selectedArticleId);
            }
        });

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

        saveArticleButton.addActionListener(evt -> {
            saveArticleController.execute(this.viewModel.getState().getArticle());
        });

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
                inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "recensorArticle");
            }
        };

        Action recensorArticle = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                uncensoredNotif.setVisible(false);
                articleTitle.setText(viewModel.getState().getCensoredArticle().getTitle());
                articleTextArea.setText(viewModel.getState().getCensoredArticle().getText());
                inputmap.remove(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
                inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "uncensorArticle");
            }
        };

        inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0), "uncensorArticle");

        actionmap.put("uncensorArticle", uncensorArticle);
        actionmap.put("recensorArticle", recensorArticle);

        loadArticleButton.addActionListener(evt -> toggleLoadArticleDropdown());
    }

    private void styleArticle() {
        articleTextArea.setFont(ARTICLE_FONT);
        articleTextArea.setBackground(ARTICLE_BACKGROUND_COLOR);
        articleTextArea.setBorder(new LineBorder(Color.GRAY, 2));
        articleTextArea.setLineWrap(true);
        articleTextArea.setWrapStyleWord(true);
        articleTextArea.setBackground(TEXTAREA_BACKGROUND_COLOR);
    }

    private void styleDropdownMenu(JComboBox loadArticleDropdown) {
        loadArticleDropdown.setFont(ARTICLE_FONT);
        loadArticleDropdown.setBackground(BUTTON_BACKGROUND_COLOR);
        loadArticleDropdown.setBorder(new LineBorder(Color.GRAY, 2));
        loadArticleDropdown.setForeground(Color.WHITE);
        loadArticleDropdown.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loadArticleDropdown.setMaximumSize(BUTTON_SIZE);
        loadArticleDropdown.setAlignmentX(Component.LEFT_ALIGNMENT);
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
        loadArticleDropdown.addItem("Select an article...");
        Map<String, String> savedArticles = state.getSavedArticleList();
        for (Map.Entry<String, String> entry : savedArticles.entrySet()) {
            loadArticleDropdown.addItem(entry.getValue());
        }
    }

    /**
     * Return the name of the current view.
     *
     * @return the name of the current view.
     */
    public String getViewName() {
        return VIEW_NAME;
    }

    /**
     * Attach the controller for the Random Article use case.
     * Must be executed before showing the view to the user to prevent a program crash.
     *
     * @param controller the controller to attach.
     */
    public void setRandomArticleController(RandomArticleController controller) {
        this.randomArticleController = controller;
    }

    public void setLoadURLController(LoadURLController loadURLController) {
        this.loadURLController = loadURLController;
    }

    public void setPopulateListController(PopulateListController controller) {
        this.populateListController = controller;
    }

    /**
     * Attach the controller for the Choose Rule Set use case.
     * Must be executed before showing the view to the user to prevent a program crash.
     *
     * @param controller the controller to attach.
     */
    public void setChooseRuleSetController(ChooseRuleSetController controller) {
        this.chooseRuleSetController = controller;
    }

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
    }

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
