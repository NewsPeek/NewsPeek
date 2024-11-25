package use_case.random_article;

import entity.article.Article;

/**
 * DAO for the Logout Use Case.
 */
public interface RandomArticleAPIDataAccessInterface {
    /**
     * Returns the username of the curren user of the application.
     * @param country the country in which to search.
     * @return the username of the current user
     * @throws java.io.IOException if something goes wrong internally and the article could not be fetched.
     *      Trying again might help.
     */
    Article getRandomArticle(String country) throws java.io.IOException;
}
