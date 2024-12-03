package use_case.load_url;

/**
 * The Output Boundary Interface for the Load URL Use Case.
 */
public interface LoadURLOutputBoundary {
    /**
     * Prepares the success view for the Load URL Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(LoadURLOutputData outputData);

    /**
     * Prepares the failure view for the Load URL Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
