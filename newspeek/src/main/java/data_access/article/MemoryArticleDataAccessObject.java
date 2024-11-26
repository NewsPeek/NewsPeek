package data_access.article;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import entity.article.Article;
import use_case.random_article.RandomArticleAPIDataAccessInterface;

/**
 * DAO to mock getting articles from the web.
 */
public class MemoryArticleDataAccessObject implements RandomArticleAPIDataAccessInterface {
    /**
     * Fake getting a random article from the given country.
     * @param country if this is "FAIL", intentionally raise an exception to simulate a failed API call.
     * @return a mock article.
     * @throws IOException if the country is specified as "FAIL"
     */
    @Override
    public Article getRandomArticle(String country) throws IOException {
        if ("FAIL".equals(country)) {
            throw new IOException("getRandomArticle intentionally failed.");
        } else {
            return Article.mockArticle();
        }
    }

    /**
     * Fake getting an article from the given URL. If the URL is malformed, print an error and exit.
     * @param url the URL to get the article from. Must be well-formed, but not necessarily real.
     * @return a mock article.
     */
    public Article getArticleFromUrl(String url) {
        try {
            new URL(url);
            return Article.mockArticle();
        } catch (MalformedURLException exception) {
            System.err.println("MemoryArticleDataAccessObject: Unrecoverable error: malformed URL.");
            exception.printStackTrace();
            System.exit(1);
        }
        /* unreachable */
        return null;
    }
}
