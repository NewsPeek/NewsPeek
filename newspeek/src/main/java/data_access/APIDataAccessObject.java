package data_access;

import entity.article.Article;
import entity.article.ArticleFactory;
import use_case.helpers.Scraper;
import use_case.random_article.RandomArticleAPIDataAccessInterface;

import java.io.*;

/**
 * DAO for to read articles from the web.
 */
public class APIDataAccessObject implements RandomArticleAPIDataAccessInterface {
    private final ArticleFactory articleFactory;
    private final Scraper scraper;

    public APIDataAccessObject(ArticleFactory articleFactory, Scraper scraper) {
        this.articleFactory = articleFactory;
        this.scraper = scraper;
    }

    @Override
    public Article getRandomArticle(String country) {
        // TODO add actual API call
        String url = "https://example.com";
        final Article article = this.scraper.scrapeArticle(url);
        return article;
    }
}
