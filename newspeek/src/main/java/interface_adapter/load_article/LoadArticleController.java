package interface_adapter.load_article;

import use_case.load_article.LoadArticleInputBoundary;
import use_case.load_article.LoadArticleInputData;

/**
 * Controller for the Load Article use case.
 */
public class LoadArticleController {
    private final LoadArticleInputBoundary interactor;

    public LoadArticleController(LoadArticleInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Load Article use case.
     * @param id the article ID to load.
     */
    public void execute(String id) {
        final LoadArticleInputData inputData = new LoadArticleInputData(id);
        this.interactor.execute(inputData);
    }
}
