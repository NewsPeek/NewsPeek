package use_case.random_article;

import java.io.IOException;

import entity.article.Article;

/**
 * DAO for the Logout Use Case.
 */
public interface RandomArticleAPIDataAccessInterface {
    /**
     * Returns a random article from the given country.
     * @param country the country in which to search.
     * @return the article, with all fields populated as well as possible from the page.
     * @throws IOException if something goes wrong in choosing, fetching or parsing the article.
     */
    Article getRandomArticle(String country) throws java.io.IOException;

    /**
     * Returns the article scraped from the given URL.
     * @param url the URL from which to load the article. Should point to a page formatted like a news article.
     * @return the article, with all fields populated as well as possible from the page.
     * @throws IOException if something goes wrong in fetching or parsing the article.
     */
    Article getArticleFromUrl(String url) throws IOException;
}
