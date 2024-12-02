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

    /**
     * Constructor for uncensored article. Sets the censorship statistics to 0.
     * @param title title of the article
     * @param text full plaintext of the article
     * @param source URL or filesystem source of the article
     * @param author author of the article
     * @param agency publishing agency of the article
     * @param postedAt posting date and time of the article
     */
    public Article(String title, String text, String source, String author, String agency,
                   java.time.LocalDateTime postedAt) {
        this.title = title;
        this.text = text;
        this.author = author;
        this.agency = agency;
        this.source = source;
        this.postedAt = postedAt;
        this.censoredWords = 0;
        this.replacedWords = 0;
    }

    /**
     * Constructor for censored article. Specifies the censorship statistics.
     * @param title title of the article
     * @param text full plaintext of the article
     * @param source URL or filesystem source of the article
     * @param author author of the article
     * @param agency publishing agency of the article
     * @param postedAt posting date and time of the article
     * @param censoredWords the number of words censored in the censorship process
     * @param replacedWords the number of words replaced in the censorship process
     */
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

    /**
     * Returns the number of words censored (blocked out) in the censorship process.
     * @return the number of words censored
     */
    public int getCensoredWords() {
        return this.censoredWords;
    }

    /**
     * Returns the number of words replaced in the censorship process.
     * @return the number of words replaced
     */
    public int getReplacedWords() {
        return this.replacedWords;
    }

    /**
     * Sets the number of words censored (blocked out) in the censorship process.
     * @param censoredWords the number of words censored
     */
    public void setCensoredWords(int censoredWords) {
        this.censoredWords = censoredWords;
    }

    /**
     * Sets the number of words replaced in the censorship process.
     * @param replacedWords the number of words replaced
     */
    public void setReplacedWords(int replacedWords) {
        this.replacedWords = replacedWords;
    }
}

