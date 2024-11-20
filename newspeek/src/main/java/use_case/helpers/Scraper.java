package use_case.helpers;

import entity.article.Article;

import java.io.IOException;

public interface Scraper {
    Article scrapeArticle(String url) throws IOException;
}