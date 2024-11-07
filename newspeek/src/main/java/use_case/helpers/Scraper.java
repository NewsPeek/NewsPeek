package use_case.helpers;

import entity.Article;

public abstract class Scraper {
    String url;

    Scraper(String url) {
        this.url = url;
    }

    abstract Article scrapeArticle();
}
