package entity.article;

import java.time.LocalDateTime;

/**
 * Article entity with a title, body text, author, agency, source, and posting time.
 * The fundamental entity of NewsPeek.
 */
public class Article {
    private static Article mockArticle;

    private final String title;
    private String text;
    private final String author;
    private final String agency;
    private final String source;
    private final java.time.LocalDateTime postedAt;

    public Article(String title, String text, String source, String author, String agency,
                   java.time.LocalDateTime postedAt) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.agency = agency;
        this.source = source;
        this.postedAt = postedAt;
    }

    /**
     * Returns an identical copy of this article.
     * @return an identical copy of this article
     */
    public Article copy() {
        return new Article(this.title, this.text, this.source, this.author, this.agency, this.postedAt);
    }

    /**
     * Returns the title of this article.
     * @return the title of this article.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the body text of this article.
     * This will be plain text, not HTML.
     * @return the body text of this article.
     */
    public String getText() {
        return this.text;
    }

    /**
     * Sets the text of this article.
     * @param text the new text of this article
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the url or filesystem path from which the article is sourced.
     * @return the url or filesystem path from which the article is sourced
     */
    public String getSource() {
        return this.source;
    }

    /**
     * Returns the author of this article.
     * @return the author of this article.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Returns the publishing agency of this article.
     * @return the publishing agency of this article.
     */
    public String getAgency() {
        return agency;
    }

    /**
     * Returns the posting time and date of this article.
     * If no posting time was found when reading the article, return the current time.
     * @return the posting time and date of this article.
     */
    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    /**
     * Return a mock article for testing, with dummy data in all fields.
     * @return a mock article. This will be the same object every time.
     */
    public static Article mockArticle() {
        if (mockArticle == null) {
            mockArticle = new Article("Sample Article",
                    "lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.",
                    "https://example.com",
                    "John Cena",
                    "XYZ Corporation",
                    LocalDateTime.now());
        }
        return mockArticle;
    }
}
