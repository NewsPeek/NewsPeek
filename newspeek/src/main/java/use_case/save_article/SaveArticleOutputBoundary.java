package use_case.save_article;

/**
 * Output boundary for the Save Article use case.
 */
public interface SaveArticleOutputBoundary {
    /**
     * Inform the user that the article was saved.
     */
    void prepareSuccessView();

    /**
     * Display an error returned by the Interactor in a pop-up.
     * @param error a descriptive error to show the user in a pop-up.
     */
    void prepareFailView(String error);
}
