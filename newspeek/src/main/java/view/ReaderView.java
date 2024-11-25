package view;

import interface_adapter.ReaderState;
import interface_adapter.ReaderViewModel;
import interface_adapter.choose_rule_set.ChooseRuleSetController;
import interface_adapter.random_article.RandomArticleController;
import use_case.helpers.CensorshipService;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;


/**
 * The View for when the user is reading a censored article.
 */
public class ReaderView extends JPanel implements PropertyChangeListener {
    private final String viewName = "reader";
    private final ReaderViewModel readerViewModel;
    private RandomArticleController randomArticleController;
    private ChooseRuleSetController chooseRuleSetController;
    private JTextArea articleTextArea;
    private JFileChooser fileChooser;
    private CensorshipService censorshipService;

    public ReaderView(ReaderViewModel readerViewModel) {
        this.fileChooser = new JFileChooser();
        this.readerViewModel = readerViewModel;
        this.readerViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel title = new JLabel("Reader Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel buttons = new JPanel();
        JButton randomArticleButton = new JButton("Random Article");
        JButton loadRuleSetButton = new JButton("Open censorship data File");
        buttons.add(randomArticleButton);
        buttons.add(loadRuleSetButton);
        articleTextArea = new JTextArea(10,40);
        articleTextArea.setEditable(false);
        JScrollPane articleScrollPane = new JScrollPane(articleTextArea);

        randomArticleButton.addActionListener(evt -> {
            String country = "us";
            randomArticleController.execute(country);
        });

        loadRuleSetButton.addActionListener(evt -> chooseRuleSet());

        this.add(title);

        this.add(buttons);
        this.add(articleScrollPane);
    }

    private void chooseRuleSet() {
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            chooseRuleSetController.execute(selectedFile);
        }
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

    public void updateArticleText(ReaderState state) {
        if (state.getArticle() != null) {
            String newText = this.censorshipService.censor(state.getArticle(), state.getCensorshipRuleSet()).getText();
            articleTextArea.setText(newText);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setRandomArticleController(RandomArticleController randomArticleController) {
        this.randomArticleController = randomArticleController;
    }

    public void setChooseRuleSetController(ChooseRuleSetController chooseRuleSetController) {
        this.chooseRuleSetController = chooseRuleSetController;
    }

    public void setCensorshipService(CensorshipService censorshipService) {
        this.censorshipService = censorshipService;
    }

    public void showError(ReaderState state) {
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
