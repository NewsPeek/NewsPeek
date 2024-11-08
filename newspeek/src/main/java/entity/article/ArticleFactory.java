package entity.article;

/**
 * Factory for creating articles.
 */
public interface ArticleFactory {
    /**
     * Creates a new Article.
     * @param title the title of the article
     * @param text the full text of the article
     * @param source the source of the article, such as a URL or file path
     * @param author the name of the author of the article
     * @param agency the name of the agency that published the article
     * @param postedAt the time at which the article was posted
     * @return the new article
     */
    Article create(String title, String text, String source, String author, String agency, java.time.LocalDateTime postedAt);
}
