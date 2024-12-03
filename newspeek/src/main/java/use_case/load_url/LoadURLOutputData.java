package use_case.load_url;

import entity.article.Article;

/**
 *  This loads the data from the URL to be displayed.
 */
public class LoadURLOutputData {
    private final Article article;

    public LoadURLOutputData(Article article) {
        this.article = article;
    }

    /**
     * Returns the article found by this use case.
     * @return the article found by this use case.
     */
    public Article getArticle() {
        return this.article;
    }
}
