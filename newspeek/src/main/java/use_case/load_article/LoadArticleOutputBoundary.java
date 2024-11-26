package use_case.load_article;

/**
 * Output boundary for the Load Article use case.
 */
public interface LoadArticleOutputBoundary {
    /**
     * Display the loaded article on screen.
     * @param outputData an object containing the data to be returned to the ReaderView.
     */
    void prepareSuccessView(LoadArticleOutputData outputData);

    /**
     * Display an error returned by the Interactor in a pop-up.
     * @param error a descriptive error to show the user in a pop-up.
     */
    void prepareFailView(String error);
}
