package use_case.load_article;

import entity.article.Article;

/**
 * Output data for the Choose Rule Set use case.
 * This object should only be created if the new rule set was successfully loaded.
 */
public class LoadArticleOutputData {
    private final Article article;

    public LoadArticleOutputData(Article article) {
        this.article = article;
    }

    /**
     * Returns the loaded article.
     * @return the loaded article.
     */
    public Article getArticle() {
        return article;
    }
}
