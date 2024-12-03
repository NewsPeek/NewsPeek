package use_case.load_article;

/**
 * Interface to execute the Load Article use case.
 */
public interface LoadArticleInputBoundary {
    /**
     * Execute the Load Article use case.
     * @param inputData the input data for the use case.
     */
    void execute(LoadArticleInputData inputData);
}
