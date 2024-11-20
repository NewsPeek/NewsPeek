package data_access;

import entity.article.Article;
import entity.article.CommonArticle;
import use_case.random_article.RandomArticleAPIDataAccessInterface;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

/**
 * DAO to mock getting articles from the web
 */
public class MemoryArticleDataAccessObject implements RandomArticleAPIDataAccessInterface {
    @Override
    public Article getRandomArticle(String country) {
        return makeMockArticle();
    }

    public Article getArticleFromURL(String url) {
        try {
            new URL(url);
            return makeMockArticle();
        } catch (MalformedURLException e) {
            System.err.println("MemoryArticleDataAccessObject: Unrecoverable error: malformed URL.");
            e.printStackTrace();
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
        return new CommonArticle(title, text, source, author, agency, postedAt);
    }
}
