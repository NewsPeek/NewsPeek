package interface_adapter.save_article;

import entity.article.Article;
import use_case.save_article.SaveArticleInputBoundary;
import use_case.save_article.SaveArticleInputData;

/**
 * Controller for the Save Article use case.
 */
public class SaveArticleController {
    private final SaveArticleInputBoundary interactor;

    public SaveArticleController(SaveArticleInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Save Article use case.
     * @param article the article to save.
     */
    public void execute(Article article) {
        final SaveArticleInputData inputData = new SaveArticleInputData(article);
        this.interactor.execute(inputData);
    }
}
