package use_case.helpers;

import entity.article.Article;
import entity.article.ArticleFactory;
import entity.article.CommonArticle;

import java.time.LocalDateTime;

public class JReadabilityScraper implements Scraper {
    private final ArticleFactory articleFactory;

    public JReadabilityScraper(ArticleFactory articleFactory) {
        this.articleFactory = articleFactory;
    }


    @Override
    public Article scrapeArticle(String url) {
        final String title = "Sample Article";
        final String text = "How much wood could a woodchuck chuck if a woodchuck could chuck wood?";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        return this.articleFactory.create(title, text, url, author, agency, postedAt);
    }
}
