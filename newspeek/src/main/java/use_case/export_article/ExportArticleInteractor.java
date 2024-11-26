package use_case.export_article;

import java.io.IOException;

import entity.article.Article;

/**
 * Interactor for the Export Article use case. Uses the provided Data Access Interface to save the given article.
 */
public class ExportArticleInteractor implements ExportArticleInputBoundary {
    private final ExportArticleDataAccessInterface dataAccessInterface;
    private final ExportArticleOutputBoundary presenter;

    public ExportArticleInteractor(ExportArticleDataAccessInterface dataAccessInterface,
                                   ExportArticleOutputBoundary presenter) {
        this.dataAccessInterface = dataAccessInterface;
        this.presenter = presenter;
    }

    @Override
    public void execute(ExportArticleInputData inputData) {
        Article article = inputData.getArticle();
        try {
            dataAccessInterface.saveArticle(article);
            ExportArticleOutputData outputData = new ExportArticleOutputData();
            presenter.prepareSuccessView(outputData);
        } catch (IOException exception) {
            presenter.prepareFailView(exception.getMessage());
        }
    }
}
