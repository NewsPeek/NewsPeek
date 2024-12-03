package data_access.article;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import entity.article.Article;
import use_case.load_article.LoadArticleDataAccessInterface;
import use_case.random_article.RandomArticleAPIDataAccessInterface;
import use_case.save_article.SaveArticleDataAccessInterface;

/**
 * DAO to store articles in memory.
 */
public class MemoryArticleDataAccessObject
        implements RandomArticleAPIDataAccessInterface, SaveArticleDataAccessInterface, LoadArticleDataAccessInterface {
    private final Map<String, Article> articles = new HashMap<>();

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

    /**
     * Store the given article in a memory database.
     * @param article the article to save.
     * @throws IOException to simulate a filesystem error in testing, if the article text is "FAIL"
     */
    @Override
    public void saveArticle(Article article) throws IOException {
        if ("FAIL".equals(article.getText())) {
            throw new IOException("saveArticle intentionally failed.");
        }
        String id;
        do {
            // unlikely to ever run more than once, as this would require an uber-rare UUID collision
            id = UUID.randomUUID() + ".json";
        } while (this.articles.containsKey(id));
        this.articles.put(id, article);
    }

    /**
     * Loads the article from the given article ID.
     * @param id the unique article ID. This should be obtained from an earlier call to listSavedArticles().
     * @return the article with the given ID.
     * @throws IOException if the article can't be found.
     */
    @Override
    public Article loadArticle(String id) throws IOException {
        if (this.articles.containsKey(id)) {
            return this.articles.get(id);
        } else {
            throw new IOException(id + " not found in the database.");
        }
    }

    /**
     * Returns a mapping from article ID to article title.
     * An article ID is a unique string that represents an article in the database.
     * @return a mapping from article ID to article title.
     */
    public Map<String, String> listSavedArticles() {
        Map<String, String> titles = new HashMap<>();
        for (String id : this.articles.keySet()) {
            titles.put(id, this.articles.get(id).getTitle());
        }
        return titles;
    }
}
