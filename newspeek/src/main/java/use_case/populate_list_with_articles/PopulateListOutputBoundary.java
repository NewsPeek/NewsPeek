package use_case.populate_list_with_articles;

/**
 * The Output Data Interface for the Populate List Use Case.
 */
public interface PopulateListOutputBoundary {
    /**
     * Display the success view for the Populate List Use Case.
     * @param outputData the desired output data for the Use Case.
     */
    void prepareSuccessView(PopulateListOutputData outputData);

    /**
     * Display an error returned by the Interactor in a pop-up.
     * @param error a descriptive error to show the user in a pop-up.
     */
    void prepareFailView(String error);

}
