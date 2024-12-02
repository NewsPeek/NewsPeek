package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.*;

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
    private static final int DEFAULT_TEXTAREA_ROWS = 10;
    private static final int DEFAULT_TEXTAREA_COLUMNS = 40;

    private static final String VIEW_NAME = "reader";

    // Use cases
    private RandomArticleController randomArticleController;
    private ChooseRuleSetController chooseRuleSetController;

    // Swing objects
    private final JLabel articleTitle;
    private final JTextArea articleTextArea;
    private final JFileChooser fileChooser;

    private final CensorshipService censorshipService;

    public ReaderView(ReaderViewModel readerViewModel, CensorshipService censorshipService) {
        this.fileChooser = new JFileChooser();
        readerViewModel.addPropertyChangeListener(this);

        this.censorshipService = censorshipService;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        final JPanel buttons = new JPanel();
        JButton randomArticleButton = new JButton("Random Article");
        JButton loadArticleFromURL = new JButton("Load from URL");
        JButton loadRuleSetButton = new JButton("Open censorship data File");
        buttons.add(randomArticleButton);
        buttons.add(loadArticleFromURL);
        buttons.add(loadRuleSetButton);

        this.articleTitle = new JLabel("No article loaded");
        articleTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        articleTextArea = new JTextArea(DEFAULT_TEXTAREA_ROWS, DEFAULT_TEXTAREA_COLUMNS);
        articleTextArea.setEditable(false);
        JScrollPane articleScrollPane = new JScrollPane(articleTextArea);

        this.add(buttons);
        this.add(articleTitle);
        this.add(articleScrollPane);

        randomArticleButton.addActionListener(evt -> {
            String country = "us";
            randomArticleController.execute(country);
        });

        loadRuleSetButton.addActionListener(evt -> chooseRuleSet());

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
     * @param randomArticleController the controller to attach.
     */
    public void setRandomArticleController(RandomArticleController randomArticleController) {
        this.randomArticleController = randomArticleController;
    }

    /**
     * Attach the controller for the Choose Rule Set use case.
     * Must be executed before showing the view to the user to prevent a program crash.
     * @param chooseRuleSetController the controller to attach.
     */
    public void setChooseRuleSetController(ChooseRuleSetController chooseRuleSetController) {
        this.chooseRuleSetController = chooseRuleSetController;
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
}
