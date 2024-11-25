package data_access.article;

/**
 * Exception thrown by NewsAPI calls in APIDataAccessObject.
 */
public class NewsAPIException extends RuntimeException {
    public NewsAPIException(String message) {
        super(message);
    }
}
