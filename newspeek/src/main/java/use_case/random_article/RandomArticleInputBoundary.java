package use_case.random_article;

/**
 * Input Boundary to execute the Random Article use case.
 */
public interface RandomArticleInputBoundary {

    /**
     * Executes the Random Article use case.
     * @param RandomArticleInputData the input data
     */
    void execute(RandomArticleInputData RandomArticleInputData);
}
