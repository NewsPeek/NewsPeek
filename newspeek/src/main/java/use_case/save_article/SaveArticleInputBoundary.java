package use_case.save_article;

/**
 * Interface to execute the Save Article use case.
 */
public interface SaveArticleInputBoundary {
    /**
     * Execute the Save Article use case.
     * @param inputData the input data for the use case.
     */
    void execute(SaveArticleInputData inputData);
}
