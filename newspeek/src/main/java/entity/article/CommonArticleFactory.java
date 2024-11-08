package entity.article;

/**
 * Factory for creating articles.
 */
public class CommonArticleFactory implements ArticleFactory {
    @Override
    public Article create(String title, String text, String source, String author, String agency, java.time.LocalDateTime postedAt) {
        return new CommonArticle(title, text, source, author, agency, postedAt);
    }
}
