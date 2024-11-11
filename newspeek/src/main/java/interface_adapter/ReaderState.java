package interface_adapter;

import entity.article.Article;

/**
 * The State information representing the logged-in user.
 */
public class ReaderState {
    private Article article;

//    public ReaderState(ReaderState copy) {
//        // TODO: copy all instance variables from the other ReaderState
//    }

    // Because of the previous copy constructor, the default constructor must be explicit.
//    public ReaderState(Article article) {
//        this.article = article;
//    } Not added constructor to follow convention from Login state in Lab 5

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}
