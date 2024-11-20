package entity.article;

/**
 * Factory for creating articles.
 */
public class ArticleFactory {
    public Article create(String title, String text, String source, String author, String agency, java.time.LocalDateTime postedAt) {
        return new Article(title, text, source, author, agency, postedAt);
    }
}
