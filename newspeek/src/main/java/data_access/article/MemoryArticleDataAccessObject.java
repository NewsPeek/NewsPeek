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
    @Override
    public Article getRandomArticle(String country) throws IOException {
        if ("FAIL".equals(country)) {
            throw new IOException("getRandomArticle intentionally failed.");
        } else {
            return makeMockArticle();
        }
    }

    public Article getArticleFromURL(String url) {
        try {
            new URL(url);
            return makeMockArticle();
        } catch (MalformedURLException exception) {
            System.err.println("MemoryArticleDataAccessObject: Unrecoverable error: malformed URL.");
            exception.printStackTrace();
            System.exit(1);
        }
        /* unreachable */
        return null;
    }

    private static Article makeMockArticle() {
        final String title = "Sample Article";
        final String text = "lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.";
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        return new Article(title, text, source, author, agency, postedAt);
    }
}
