package use_case.export_article;

/**
 * Output boundary for the Choose Rule Set use case.
 */
public interface ExportArticleOutputBoundary {
    /**
     * Do nothing. TODO: decide if it should do nothing.
     * @param outputData an object containing the data to be returned to the ReaderView.
     */
    void prepareSuccessView(ExportArticleOutputData outputData);

    /**
     * Display an error returned by the Interactor in a pop-up.
     * @param error a descriptive error to show the user in a pop-up.
     */
    void prepareFailView(String error);
}
