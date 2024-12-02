package entity.article;

import java.time.LocalDateTime;

/**
 * Article entity with a title, body text, author, agency, source, and posting time.
 * The fundamental entity of NewsPeek.
 */
public class Article {
    private final String title;
    private String text;
    private final String author;
    private final String agency;
    private final String source;
    private final java.time.LocalDateTime postedAt;
    private int censoredWords;
    private int replacedWords;

    public Article(String title, String text, String source, String author, String agency,
                   java.time.LocalDateTime postedAt, int censoredWords, int replacedWords) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.agency = agency;
        this.source = source;
        this.postedAt = postedAt;
        this.censoredWords = censoredWords;
        this.replacedWords = replacedWords;
    }

    /**
     * Returns an identical copy of this article.
     * @return an identical copy of this article
     */
    public Article copy() {
        return new Article(this.title, this.text, this.source, this.author, this.agency, this.postedAt, this.censoredWords, this.replacedWords);
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

    public int getCensoredWords() {return this.censoredWords;}

    public int getReplacedWords() {return this.replacedWords;}

    public void setCensoredWords(int censoredWord) {this.censoredWords=censoredWord;}

    public void setReplacedWords(int replacedWord) {this.replacedWords=replacedWord;}
}

