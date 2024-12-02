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
import use_case.helpers.CensorshipService;

/**
 * The View for when the user is reading a censored article.
 */
public class ReaderView extends JPanel implements PropertyChangeListener {
    private static final int DEFAULT_TEXTAREA_ROWS = 20;
    private static final int DEFAULT_TEXTAREA_COLUMNS = 40;

    private static final String VIEW_NAME = "reader";

    // Use cases
    private RandomArticleController randomArticleController;
    private ChooseRuleSetController chooseRuleSetController;

    // Swing objects
    private final JLabel title;
    private final JLabel articleTitle;
    private final JLabel censoredSummary;
    private final JLabel replacedSummary;
    private final JTextArea articleTextArea;
    private final JFileChooser fileChooser;

    private final CensorshipService censorshipService;

    public ReaderView(ReaderViewModel readerViewModel, CensorshipService censorshipService) {
        this.fileChooser = new JFileChooser();
        readerViewModel.addPropertyChangeListener(this);

        this.censorshipService = censorshipService;

        this.setLayout(new BorderLayout(10, 10));
        this.setBackground(new Color(245, 245, 245));
        this.setPreferredSize(new Dimension(800, 800));

        // Title Section
        this.title = new JLabel("Newspeek");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        title.setForeground(new Color(70, 130, 180));

        this.censoredSummary = new JLabel(" Censored words:");
        this.replacedSummary = new JLabel(" Replaced words:");
        censoredSummary.setFont(new Font("SansSerif", Font.BOLD, 16));
        censoredSummary.setForeground(new Color(70, 130, 180));
        replacedSummary.setFont(new Font("SansSerif", Font.BOLD, 16));
        replacedSummary.setForeground(new Color(70, 130, 180));

        // Buttons Panel
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
        buttonsPanel.setBackground(new Color(230, 230, 250));
        buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton randomArticleButton = new JButton("Random Article");
        JButton loadRuleSetButton = new JButton("Open Censorship Data File");
        styleButton(randomArticleButton);
        styleButton(loadRuleSetButton);

        buttonsPanel.add(randomArticleButton);

        buttonsPanel.add(loadRuleSetButton);
        buttonsPanel.add(censoredSummary);
        buttonsPanel.add(replacedSummary);

        // Article Title
        this.articleTitle = new JLabel("No article loaded");
        articleTitle.setHorizontalAlignment(SwingConstants.CENTER);
        articleTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        articleTitle.setBorder(new EmptyBorder(10, 0, 10, 0));

        // Article Text Area
        articleTextArea = new JTextArea(DEFAULT_TEXTAREA_ROWS, DEFAULT_TEXTAREA_COLUMNS);
        articleTextArea.setEditable(false);
        articleTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        articleTextArea.setBackground(new Color(255, 250, 240));
        articleTextArea.setBorder(new LineBorder(Color.GRAY, 2));
        articleTextArea.setLineWrap(true);
        articleTextArea.setWrapStyleWord(true);

        JScrollPane articleScrollPane = new JScrollPane(articleTextArea);
        articleScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Main Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(new Color(245, 245, 245));
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

        loadRuleSetButton.addActionListener(evt -> chooseRuleSet());
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

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setRandomArticleController(RandomArticleController randomArticleController) {
        this.randomArticleController = randomArticleController;
    }

    public void setChooseRuleSetController(ChooseRuleSetController chooseRuleSetController) {
        this.chooseRuleSetController = chooseRuleSetController;
    }

    private void updateArticleText(ReaderState state) {
        if (state.getArticle() != null) {
            Article censoredArticle = this.censorshipService.censor(state.getArticle(), state.getCensorshipRuleSet());
            articleTextArea.setText(censoredArticle.getText());
            articleTitle.setText(censoredArticle.getTitle());

        }
    }

    private void updateSummaryLabel(ReaderState state) {
        if (state.getArticle() != null) {
            censoredSummary.setText(" Censored words:"+ String.valueOf(state.getArticle().getCensoredWords()));
            replacedSummary.setText(" Replaced words"+String.valueOf(state.getArticle().getReplacedWords()));
        }
    }


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
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(60, 130, 180));
        button.setBorder(new LineBorder(new Color(230, 230, 250), 7, false));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
