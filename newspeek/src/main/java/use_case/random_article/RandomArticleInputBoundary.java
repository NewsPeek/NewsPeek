package use_case.random_article;

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface RandomArticleInputBoundary {

    /**
     * Executes the Logout use case.
     * @param RandomArticleInputData the input data
     */
    void execute(RandomArticleInputData RandomArticleInputData);
}
