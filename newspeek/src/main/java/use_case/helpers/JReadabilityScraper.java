package use_case.helpers;

import entity.Article;
import entity.CommonArticle;

import java.time.LocalDateTime;

public class JReadabilityScraper extends Scraper {

    public JReadabilityScraper(String url) {
        super(url);
    }

    @Override
    public Article scrapeArticle() {
        final String title = "Sample Article";
        final String text = "How much wood could a woodchuck chuck if a woodchuck could chuck wood?";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        return new CommonArticle(title, text, this.url, author, agency, postedAt);
    }
}
