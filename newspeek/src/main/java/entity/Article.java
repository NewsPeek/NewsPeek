package entity;

public interface Article {
    /**
     * Returns the title of the article.
     * @return the title of the article.
     */
    String getTitle();

    /**
     * Returns the full text of the article.
     * @return the full text of the article.
     */
    String getText();

    /**
     * The source of the article, such as a URL or file path.
     * @return the source of the article.
     */
    String getSource();
}
