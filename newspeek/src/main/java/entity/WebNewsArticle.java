package entity;

public class WebNewsArticle implements Article {
    private final String title;
    private final String text;
    private final String author;
    private final String agency;
    private final String url;
    private final java.time.LocalDateTime postedAt;


    public WebNewsArticle(String title, String text, String url, String author, String agency, java.time.LocalDateTime postedAt) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.agency = agency;
        this.url = url;
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
    public String getSource() {
        // TODO: return some synthesis of author, agency, url, postedAt
        return "";
    }
}
