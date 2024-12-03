package use_case.load_url;

/**
 * Interface to execute the LoadURL Use Case.
 */
public interface LoadURLInputBoundary {
    /**
     * Execute the LoadURL Use Case.
     * @param loadURLInputData the input data for the use case.
     */
    void execute(LoadURLInputData loadURLInputData);
}
