package interface_adapter;

import entity.article.Article;

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
        Article oldArticle = getState().getArticle();
        getState().setArticle(article);
        firePropertyChange("article", oldArticle,article);
    }
}
