package view;

import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;
import interface_adapter.ReaderState;
import interface_adapter.ReaderViewModel;
import interface_adapter.random_article.RandomArticleController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.Set;

/**
 * The View for when the user is reading a censored article.
 */
public class ReaderView extends JPanel implements PropertyChangeListener {

    private final String viewName = "reader";
    private final ReaderViewModel readerViewModel;
    private RandomArticleController randomArticleController;
    private JTextArea articleTextArea;

    public ReaderView(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
        this.readerViewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        final JLabel title = new JLabel("Reader Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        final JPanel buttons = new JPanel();
        JButton randomArticleButton = new JButton("Random Article");
        buttons.add(randomArticleButton);
        articleTextArea = new JTextArea(10,40);
        articleTextArea.setEditable(false);
        JScrollPane articleScrollPane = new JScrollPane(articleTextArea);

        randomArticleButton.addActionListener(evt ->{
            String country = "Canada";
            CensorshipRuleSet censorshipRuleSet = new CommonCensorshipRuleSet( Set.of("violence"), Map.of(),
                    false, "Basic Rules");
            randomArticleController.execute(country,censorshipRuleSet);
        });


        this.add(title);

        this.add(buttons);
        this.add(articleScrollPane);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("PropertyChangeEvent received: " + evt.getPropertyName());
        if (evt.getPropertyName().equals("article")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            updateArticleText(state);
        }
    }

    public void updateArticleText(ReaderState state) {
        if (state.getArticle() != null) {
            articleTextArea.setText(state.getArticle().getText());
        }
    }

    public String getViewName() {
        return viewName;
    }


    public void setRandomArticleController(RandomArticleController randomArticleController  ) {
        this.randomArticleController = randomArticleController;
    }
}
