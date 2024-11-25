package data_access.article;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.article.Article;
import io.github.cdimascio.dotenv.Dotenv;
import data_access.scraper.Scraper;
import use_case.random_article.RandomArticleAPIDataAccessInterface;

/**
 * DAO for to read articles from the web.
 */
public class APIArticleDataAccessObject implements RandomArticleAPIDataAccessInterface {
    private static final String API_ENDPOINT_RANDOM = "https://newsapi.org/v2/top-headlines";
    private final String apiKey;

    private final Scraper scraper;

    public APIArticleDataAccessObject(Scraper scraper) {
        this.scraper = scraper;
        this.apiKey = this.loadApiKey();
    }

    private String loadApiKey() {
        // Automatically loads the .env file
        Dotenv dotenv = Dotenv.load();
        String loadedApiKey = dotenv.get("NEWS_API_KEY");

        if (loadedApiKey == null || loadedApiKey.isEmpty()) {
            throw new RuntimeException("NEWS_API_KEY is missing in .env file");
        }

        return loadedApiKey;
    }

    @Override
    public Article getRandomArticle(String country) throws java.io.IOException, NewsAPIException {
        AbstractList<String> urls = getTopUrls(country);
        Collections.shuffle(urls);

        for (String url : urls) {
            try {
                return this.scraper.scrapeArticle(url);
            } catch (IOException ignored) {
                // continue through the articles
            }
        }
        throw new NewsAPIException("All returned articles couldn't be scraped.");
    }

    public Article getArticleFromUrl(String url) throws IOException {
        return this.scraper.scrapeArticle(url);
    }

    private AbstractList<String> getTopUrls(String country) throws java.io.IOException, NewsAPIException {
        String url = API_ENDPOINT_RANDOM + "?country=" + country + "&apiKey=" + this.apiKey;

        // Make API request
        final JsonObject response = get(url);
        if (!response.get("status").getAsString().equals("ok")) {
            throw new NewsAPIException("NewsAPI call failed: " + response.get("message").getAsString());
        }

        // Extract article URLS
        JsonArray articles = response.get("articles").getAsJsonArray();

        if (articles.isEmpty()) {
            throw new NewsAPIException("NewsAPI call failed: no articles found for country '" + country + "'.");
        }

        ArrayList<String> urls = new ArrayList<>(articles.size());

        for (JsonElement article : articles) {
            urls.add(article.getAsJsonObject().get("url").getAsString());
        }

        return urls;
    }

    private JsonObject get(String urlString) throws java.io.IOException {
        try {
            URLConnection request = new URL(urlString).openConnection();
            request.connect();
            InputStreamReader inputStreamReader = new InputStreamReader((InputStream) request.getContent());

            return JsonParser.parseReader(inputStreamReader).getAsJsonObject();
        } catch (MalformedURLException exception) {
            System.err.println("APIDataAccessObject: Unrecoverable error: malformed URL.");
            exception.printStackTrace();
            System.exit(1);
        }
        /* unreachable */
        return null;
    }
}
