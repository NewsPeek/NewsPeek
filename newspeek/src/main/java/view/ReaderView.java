package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import entity.article.Article;
import interface_adapter.ReaderState;
import interface_adapter.ReaderViewModel;
import interface_adapter.choose_rule_set.ChooseRuleSetController;
import interface_adapter.random_article.RandomArticleController;
import interface_adapter.save_article.SaveArticleController;
import use_case.helpers.CensorshipService;

/**
 * The View for when the user is reading a censored article.
 */
public class ReaderView extends JPanel implements PropertyChangeListener {
    private static final int DEFAULT_TEXTAREA_ROWS = 20;
    private static final int DEFAULT_TEXTAREA_COLUMNS = 40;

    private static final String VIEW_NAME = "reader";

    // STYLE
    // Whole page
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final int BORDER_THICKNESS = 10;
    private static final Dimension WINDOW_SIZE = new Dimension(800, 800);
    private static final int ELEMENT_SPACING = 10;

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
    private static final LineBorder BUTTON_BORDER_STYLE = new LineBorder(new Color(230, 230, 250), 7, false);
    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);

    // Censorship summary
    private static final Font CENSORSHIP_SUMMARY_FONT = new Font("SansSerif", Font.BOLD, 16);
    private static final Color CENSORSHIP_SUMMARY_COLOR = new Color(70, 130, 180);

    private final ReaderViewModel viewModel;

    // Use cases
    private RandomArticleController randomArticleController;
    private ChooseRuleSetController chooseRuleSetController;
    private SaveArticleController saveArticleController;

    // Swing objects
    private final JLabel title;
    private final JLabel articleTitle;
    private final JLabel censoredSummary;
    private final JLabel replacedSummary;
    private final JTextArea articleTextArea;
    private final JFileChooser fileChooser;

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

        // Buttons Panel
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(BUTTONS_PANEL_BACKGROUND_COLOR);
        buttonsPanel.setBorder(new EmptyBorder(
                BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS, BORDER_THICKNESS
        ));

        JButton randomArticleButton = new JButton("Random Article");
        JButton saveArticleButton = new JButton("Save Article");
        JButton loadRuleSetButton = new JButton("Open censorship data File");
        styleButton(randomArticleButton);
        styleButton(saveArticleButton);
        styleButton(loadRuleSetButton);
        buttonsPanel.add(randomArticleButton);
        buttonsPanel.add(saveArticleButton);
        buttonsPanel.add(loadRuleSetButton);
        buttonsPanel.add(censoredSummary);
        buttonsPanel.add(replacedSummary);

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

        loadRuleSetButton.addActionListener(evt -> chooseRuleSet());
    }

    private void styleArticle() {
        articleTextArea.setFont(ARTICLE_FONT);
        articleTextArea.setBackground(ARTICLE_BACKGROUND_COLOR);
        articleTextArea.setBorder(new LineBorder(Color.GRAY, 2));
        articleTextArea.setLineWrap(true);
        articleTextArea.setWrapStyleWord(true);
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
            updateArticleText(state);
            updateSummaryLabel(state);
        } else if (evt.getPropertyName().equals("ruleset")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, "Rule set loaded: " + state.getCensorshipRuleSet().getRuleSetName());
            updateArticleText(state);
            updateSummaryLabel(state);
        } else if (evt.getPropertyName().equals("error")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            showError(state);
        }
    }

    /**
     * Return the name of the current view.
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
     * Attach the controller for the Choose Rule Set use case.
     * Must be executed before showing the view to the user to prevent a program crash.
     * @param controller the controller to attach.
     */
    public void setChooseRuleSetController(ChooseRuleSetController controller) {
        this.chooseRuleSetController = controller;
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
    private void updateArticleText(ReaderState state) {
        if (state.getArticle() != null) {
            Article censoredArticle = this.censorshipService.censor(state.getArticle(), state.getCensorshipRuleSet());
            articleTextArea.setText(censoredArticle.getText());
            articleTitle.setText(censoredArticle.getTitle());
        }
    }

    private void updateSummaryLabel(ReaderState state) {
        if (state.getArticle() != null) {
            censoredSummary.setText(" Censored words: " + state.getArticle().getCensoredWords());
            replacedSummary.setText(" Replaced words: " + state.getArticle().getReplacedWords());
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

    private void showError(ReaderState state) {
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void styleButton(JButton button) {
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(BUTTON_BACKGROUND_COLOR);
        button.setBorder(BUTTON_BORDER_STYLE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
