package use_case.random_article;

/**
 * The output boundary for the Random Article Use Case, used to update the view on success or failure.
 */
public interface RandomArticleOutputBoundary {
    /**
     * Prepares the success view for the Random Article Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(RandomArticleOutputData outputData);

    /**
     * Prepares the failure view for the Random Article Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
