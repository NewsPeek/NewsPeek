package use_case.populate_list_with_articles;


public interface PopulateListOutputBoundary {


    void prepareSuccessView(PopulateListOutputData outputData);

    /**
     * Display an error returned by the Interactor in a pop-up.
     * @param error a descriptive error to show the user in a pop-up.
     */
    void prepareFailView(String error);

}
