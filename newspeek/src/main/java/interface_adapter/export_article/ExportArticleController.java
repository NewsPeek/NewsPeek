package interface_adapter.export_article;

import entity.article.Article;
import use_case.export_article.ExportArticleInputBoundary;
import use_case.export_article.ExportArticleInputData;

/**
 * Controller for the Export Article use case.
 */
public class ExportArticleController {
    private final ExportArticleInputBoundary interactor;

    public ExportArticleController(ExportArticleInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Export Article use case.
     * @param article the article to save.
     */
    public void execute(Article article) {
        final ExportArticleInputData inputData = new ExportArticleInputData(article);
        this.interactor.execute(inputData);
    }
}
