package entity.article;

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
     * Replaces the full text of the article.
     * @param text the new full text of the article.
     */
    void setText(String text);

    /**
     * Returns the source of the article, such as a URL or file path.
     * @return the source of the article.
     */
    String getSource();

    /**
     * Returns a copy of the article.
     * @return a copy of the article.
     */
    Article copy();
}
