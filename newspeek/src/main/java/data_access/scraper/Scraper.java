package data_access.scraper;

import java.io.IOException;

import entity.article.Article;

/**
 * Class that fetches and processes the HTML from a URL, returning an Article.
 * The URL is assumed to point to a page formatted like a news article.
 */
public interface Scraper {
    /**
     * Return the article scraped from the given URL.
     * @param url the URL to fetch.
     * @return the scraped Article.
     * @throws IOException if fetching the URL fails due to a network issue.
     */
    Article scrapeArticle(String url) throws IOException;
}
