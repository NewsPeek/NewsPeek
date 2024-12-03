package use_case.load_article;

import entity.article.Article;

/**
 * Data Access Interface for loading censored Articles.
 */
public interface LoadArticleDataAccessInterface {
    /**
     * Loads the article.
     * @param id the article ID to load.
     * @return the loaded article.
     * @throws java.io.IOException if a filesystem/API error occurs.
     */
    Article loadArticle(String id) throws java.io.IOException;
}
