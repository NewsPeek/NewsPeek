package entity.article;

import java.time.LocalDateTime;

public class CommonArticle implements Article {
    private final String title;
    private String text;
    private final String author;
    private final String agency;
    private final String source; // url or filesystem path from which the article is sourced
    private final java.time.LocalDateTime postedAt;


    public CommonArticle(String title, String text, String source, String author, String agency, java.time.LocalDateTime postedAt) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.agency = agency;
        this.source = source;
        this.postedAt = postedAt;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public Article copy() {
        return new CommonArticle(this.title, this.text, this.source, this.author, this.agency, this.postedAt);
    }

    public String getAuthor() {
        return author;
    }

    public String getAgency() {
        return agency;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }
}
