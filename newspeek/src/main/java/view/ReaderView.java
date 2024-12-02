package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.*;

import entity.article.Article;
import interface_adapter.ReaderState;
import interface_adapter.ReaderViewModel;
import interface_adapter.choose_rule_set.ChooseRuleSetController;
import interface_adapter.random_article.RandomArticleController;
import interface_adapter.save_article.SaveArticleController;
import use_case.helpers.CensorshipService;
import use_case.load_article.LoadArticleDataAccessInterface;

/**
 * The View for when the user is reading a censored article.
 */
public class ReaderView extends JPanel implements PropertyChangeListener {
    private static final int DEFAULT_TEXTAREA_ROWS = 10;
    private static final int DEFAULT_TEXTAREA_COLUMNS = 40;

    private static final String VIEW_NAME = "reader";

    private final ReaderViewModel viewModel;

    // Use cases
    private RandomArticleController randomArticleController;
    private ChooseRuleSetController chooseRuleSetController;
    private SaveArticleController saveArticleController;
    private LoadArticleDataAccessInterface loadArticleDataAccess;

    // Swing objects
    private final JLabel articleTitle;
    private final JTextArea articleTextArea;
    private final JFileChooser fileChooser;
    private final JComboBox<String> loadArticleDropdown;

    private final CensorshipService censorshipService;

    public ReaderView(ReaderViewModel viewModel, CensorshipService censorshipService,
                      LoadArticleDataAccessInterface loadArticleDataAccess) {
        this.fileChooser = new JFileChooser();
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        this.censorshipService = censorshipService;
        this.loadArticleDataAccess = loadArticleDataAccess;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JPanel buttons = new JPanel();
        JButton randomArticleButton = new JButton("Random Article");
        JButton saveArticleButton = new JButton("Save Article");
        JButton loadRuleSetButton = new JButton("Open censorship data File");
        JButton loadArticleButton = new JButton("Load Article");
        buttons.add(randomArticleButton);
        buttons.add(saveArticleButton);
        buttons.add(loadRuleSetButton);
        buttons.add(loadArticleButton);

        this.articleTitle = new JLabel("No article loaded");
        articleTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        articleTextArea = new JTextArea(DEFAULT_TEXTAREA_ROWS, DEFAULT_TEXTAREA_COLUMNS);
        articleTextArea.setEditable(false);
        JScrollPane articleScrollPane = new JScrollPane(articleTextArea);

        // Create and initialize the dropdown (hidden by default)
        loadArticleDropdown = new JComboBox<>();
        loadArticleDropdown.setVisible(false); // Initially hidden
        loadArticleDropdown.addItem("Select an article...");
        loadArticleDropdown.addActionListener(evt -> {
            String selectedArticleId = (String) loadArticleDropdown.getSelectedItem();
            if (selectedArticleId != null && !selectedArticleId.equals("Select an article...")) {
                loadSelectedArticle(selectedArticleId);
                loadArticleDropdown.setVisible(false); // Hide the dropdown after selection
            }
        });

        this.add(buttons);
        this.add(loadArticleDropdown); // Add dropdown (hidden initially)
        this.add(articleTitle);
        this.add(articleScrollPane);

        randomArticleButton.addActionListener(evt -> {
            String country = "us";
            randomArticleController.execute(country);
        });

        saveArticleButton.addActionListener(evt -> {
            saveArticleController.execute(this.viewModel.getState().getArticle());
        });

        loadRuleSetButton.addActionListener(evt -> chooseRuleSet());

        loadArticleButton.addActionListener(evt -> toggleLoadArticleDropdown());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("PropertyChangeEvent received: " + evt.getPropertyName());
        if (evt.getPropertyName().equals("article")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            updateArticleText(state);
        } else if (evt.getPropertyName().equals("ruleset")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, "Rule set loaded: " + state.getCensorshipRuleSet().getRuleSetName());
            updateArticleText(state);
        } else if (evt.getPropertyName().equals("error")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            showError(state);
        }
    }

    private void toggleLoadArticleDropdown() {
        // Toggle visibility of the dropdown
        boolean isVisible = loadArticleDropdown.isVisible();
        if (!isVisible) {
            populateLoadArticleDropdown();
        }
        loadArticleDropdown.setVisible(!isVisible);
    }

    private void populateLoadArticleDropdown() {
        loadArticleDropdown.removeAllItems();
        loadArticleDropdown.addItem("Select an article...");
        try {
            Map<String, String> savedArticles = loadArticleDataAccess.listSavedArticles();
            for (Map.Entry<String, String> entry : savedArticles.entrySet()) {
                loadArticleDropdown.addItem(entry.getKey());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading saved articles: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSelectedArticle(String articleId) {
        try {
            Article article = loadArticleDataAccess.loadArticle(articleId);
            ReaderState state = new ReaderState();
            state.setArticle(article); // Set the loaded article
            viewModel.setState(state); // Update the ViewModel
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading article: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setRandomArticleController(RandomArticleController controller) {
        this.randomArticleController = controller;
    }

    public void setChooseRuleSetController(ChooseRuleSetController controller) {
        this.chooseRuleSetController = controller;
    }

    public void setSaveArticleController(SaveArticleController controller) {
        this.saveArticleController = controller;
    }

    private void updateArticleText(ReaderState state) {
        if (state.getArticle() != null) {
            Article censoredArticle = this.censorshipService.censor(state.getArticle(), state.getCensorshipRuleSet());
            articleTextArea.setText(censoredArticle.getText());
            articleTitle.setText(censoredArticle.getTitle());
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
}
