package use_case.helpers;

import entity.article.Article;
import entity.article.ArticleFactory;
import jreadability.Readability;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

public class JReadabilityScraper implements Scraper {
    private static final int TIMEOUT_MS = 10_000;
    private final ArticleFactory articleFactory;

    public JReadabilityScraper(ArticleFactory articleFactory) {
        this.articleFactory = articleFactory;
    }


    @Override
    public Article scrapeArticle(String url) throws IOException {
        URL urlObject;
        try {
            urlObject = new URL(url);
            Readability readability = new Readability(urlObject, TIMEOUT_MS);

            readability.init();

            final String text = readability.outerHtml();

            final String title = "Unknown title";
            final String author = "Unknown author";
            final String agency = "Unknown agency";
            final LocalDateTime postedAt = LocalDateTime.now();

            return this.articleFactory.create(title, text, url, author, agency, postedAt);
        } catch (MalformedURLException e) {
            System.err.println("JReadabilityScraper: Unrecoverable error: malformed URL.");
            e.printStackTrace();
            System.exit(1);
        }
        /* unreachable */
        return null;
    }
}
