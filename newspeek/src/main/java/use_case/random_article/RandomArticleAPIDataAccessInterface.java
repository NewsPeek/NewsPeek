package use_case.random_article;

import java.io.IOException;

import entity.article.Article;

/**
 * DAO for the Random Article Use Case.
 */
public interface RandomArticleAPIDataAccessInterface {
    /**
     * Returns a random article from the given country.
     * @param country the country in which to search.
     * @return the article, with all fields populated as well as possible from the page.
     * @throws IOException if something goes wrong in choosing, fetching or parsing the article.
     */
    Article getRandomArticle(String country) throws java.io.IOException;

}
