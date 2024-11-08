package data_access.scraper;

import entity.article.Article;

public interface Scraper {
    Article scrapeArticle(String url);
}