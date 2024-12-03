package use_case.load_article;

/**
 * Input data for the Load Article use case. Contains the article ID to load.
 */
public class LoadArticleInputData {
    private final String id;

    public LoadArticleInputData(String id) {
        this.id = id;
    }

    /**
     * Returns the article ID to load.
     * @return the article ID to load.
     */
    public String getId() {
        return id;
    }
}
