package use_case.save_article;

import entity.article.Article;

/**
 * Data Access Interface for saving censored Articles.
 */
public interface SaveArticleDataAccessInterface {
    /**
     * Saves the article.
     * @param article the article to save.
     * @throws java.io.IOException if a filesystem/API error occurs.
     */
    void saveArticle(Article article) throws java.io.IOException;
}
