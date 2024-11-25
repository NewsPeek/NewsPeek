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

    public Article getArticle(){
        return getState().getArticle();
    }

    public void setArticle(Article article){
        ReaderState oldState = getState();
        getState().setArticle(article);
        firePropertyChange("article", oldState, getState());
    }

    public void setError(String error) {
        ReaderState oldState = getState();
        getState().setError(error);
        firePropertyChange("error", oldState, getState());
    }

    public void setCensorshipRuleSet(CensorshipRuleSet censorshipRuleSet){
        ReaderState oldState = getState();
        getState().setCensorshipRuleSet(censorshipRuleSet);
        firePropertyChange("ruleset", oldState, getState());
    }
}