package data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import entity.article.Article;
import use_case.helpers.Scraper;
import use_case.random_article.RandomArticleAPIDataAccessInterface;
import data_access.NewsAPIException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.net.URL;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * DAO for to read articles from the web.
 */
public class APIDataAccessObject implements RandomArticleAPIDataAccessInterface {
    private static final String API_ENDPOINT_RANDOM = "https://newsapi.org/v2/top-headlines";
    private final String apiKey;

    private final Scraper scraper;

    public APIDataAccessObject(Scraper scraper) {
        this.scraper = scraper;
        this.apiKey = this.loadApiKey();
    }

    private String loadApiKey() {
        Dotenv dotenv = Dotenv.load(); // Automatically loads the .env file
        String apiKey = dotenv.get("NEWS_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new RuntimeException("NEWS_API_KEY is missing in .env file");
        }

        return apiKey;
    }

    @Override
    public Article getRandomArticle(String country) throws java.io.IOException, NewsAPIException {
        String url = getRandomURL(country);
        return this.scraper.scrapeArticle(url);
    }

    public Article getArticleFromURL(String url) throws IOException {
        return this.scraper.scrapeArticle(url);
    }

    private String getRandomURL(String country) throws java.io.IOException, NewsAPIException {
        String url = API_ENDPOINT_RANDOM +
                "?country=" +
                country +
                "&apiKey=" +
                apiKey;

        // Make API request
        final JsonObject response = get(url);
        if (!Objects.equals(response.get("status").getAsString(), "ok")) {
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

        // Return a random one
        return urls.get(new Random().nextInt(urls.size()));
    }

    private JsonObject get(String urlString) throws java.io.IOException {
        try {
            URLConnection request = new URL(urlString).openConnection();
            request.connect();
            InputStreamReader inputStreamReader = new InputStreamReader((InputStream) request.getContent());

            return JsonParser.parseReader(inputStreamReader).getAsJsonObject();
        } catch (MalformedURLException e) {
            System.err.println("APIDataAccessObject: Unrecoverable error: malformed URL.");
            e.printStackTrace();
            System.exit(1);
        }
        /* unreachable */
        return null;
    }
}
