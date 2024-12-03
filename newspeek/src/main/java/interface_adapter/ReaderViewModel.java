package interface_adapter;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

/**
 * The View Model for the Reader View.
 */
public class ReaderViewModel extends ViewModel<ReaderState> {
    public ReaderViewModel() {
        super("reader");
        setState(new ReaderState());
    }

    /**
     * Set the censored article to be displayed.
     * @param article the censored article to be displayed.
     */
    public void setArticle(Article article) {
        ReaderState oldState = getState();
        getState().setArticle(article);
        firePropertyChange("article", oldState, getState());
    }

    /**
     * Sets the censorship ruleset to apply to the current article.
     * @param censorshipRuleSet the censorship ruleset to apply.
     */
    public void setCensorshipRuleSet(CensorshipRuleSet censorshipRuleSet) {
        ReaderState oldState = getState();
        getState().setCensorshipRuleSet(censorshipRuleSet);
        firePropertyChange("ruleset", oldState, getState());
    }

    /**
     * Sets an error to display upon processing this State.
     * @param error an informative error message to show the user.
     */
    public void setError(String error) {
        ReaderState oldState = getState();
        getState().setError(error);
        firePropertyChange("error", oldState, getState());
    }

    /**
     * Sets an alert to display upon processing this State.
     * @param alert an informative alert message to show the user.
     */
    public void setAlert(String alert) {
        ReaderState oldState = getState();
        getState().setAlert(alert);
        firePropertyChange("alert", oldState, getState());
    }
}
