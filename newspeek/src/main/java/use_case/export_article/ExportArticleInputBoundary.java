package use_case.export_article;

/**
 * Interface to execute the Export Article use case.
 */
public interface ExportArticleInputBoundary {
    /**
     * Execute the Export Article use case.
     * @param inputData the input data for the use case.
     */
    void execute(ExportArticleInputData inputData);
}
