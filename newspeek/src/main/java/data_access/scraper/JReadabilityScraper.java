package data_access.scraper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entity.article.Article;
import jreadability.Readability;

/**
 * Scraper implementation using the JReadability library.
 */
public class JReadabilityScraper implements Scraper {
    private static final int TIMEOUT_MS = 10_000;

    @Override
    public Article scrapeArticle(String url) throws IOException {
        URL urlObject;
        try {
            urlObject = new URL(url);
            Readability readability = new Readability(urlObject, TIMEOUT_MS);

            readability.init();

            final String cleanHTML = readability.outerHtml();

            if (cleanHTML.contains("Sorry, readability was unable to parse this page for content.")) {
                throw new IOException("JReadabilityScraper: couldn't scrape");
            }

            return scrapeFromCleanHTML(cleanHTML, url);
        } catch (MalformedURLException exception) {
            System.err.println("JReadabilityScraper: Unrecoverable error: malformed URL.");
            exception.printStackTrace();
            System.exit(1);
        }
        /* unreachable */
        return null;
    }

    private Article scrapeFromCleanHTML(String html, String url) {
        Document doc = Jsoup.parse(html);
        Elements paragraphs = doc.select("p");
        Elements titles = doc.select("h1");

        // Append the paragraphs into a single string
        StringBuilder textBuilder = new StringBuilder();
        for (Element paragraph : paragraphs) {
            textBuilder.append(paragraph.text()).append("\n");
        }

        final String title = titles.get(0).text();
        final String text = textBuilder.toString();
        final String author = "Unknown author";
        final String agency = "Unknown agency";
        final LocalDateTime postedAt = LocalDateTime.now();

        return new Article(title, text, url, author, agency, postedAt,0,0);
    }
}
