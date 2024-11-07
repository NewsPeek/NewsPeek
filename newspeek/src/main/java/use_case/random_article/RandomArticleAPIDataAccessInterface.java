package use_case.random_article;

import entity.article.Article;

/**
 * DAO for the Logout Use Case.
 */
public interface RandomArticleAPIDataAccessInterface {

    /**
     * Returns the username of the curren user of the application.
     * @return the username of the current user
     */
    Article getRandomArticle(String country);
}
