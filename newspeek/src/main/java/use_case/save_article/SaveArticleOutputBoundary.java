package use_case.save_article;

/**
 * Output boundary for the Save Article use case.
 */
public interface SaveArticleOutputBoundary {
    /**
     * Do nothing. TODO: decide if it should do nothing.
     * @param outputData an object containing the data to be returned to the ReaderView.
     */
    void prepareSuccessView(SaveArticleOutputData outputData);

    /**
     * Display an error returned by the Interactor in a pop-up.
     * @param error a descriptive error to show the user in a pop-up.
     */
    void prepareFailView(String error);
}
