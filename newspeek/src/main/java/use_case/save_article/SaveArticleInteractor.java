package use_case.save_article;

import java.io.IOException;

import entity.article.Article;

/**
 * Interactor for the Save Article use case. Uses the provided Data Access Interface to save the given article.
 */
public class SaveArticleInteractor implements SaveArticleInputBoundary {
    private final SaveArticleDataAccessInterface dataAccessInterface;
    private final SaveArticleOutputBoundary presenter;

    public SaveArticleInteractor(SaveArticleDataAccessInterface dataAccessInterface,
                                   SaveArticleOutputBoundary presenter) {
        this.dataAccessInterface = dataAccessInterface;
        this.presenter = presenter;
    }

    @Override
    public void execute(SaveArticleInputData inputData) {
        Article article = inputData.getArticle();
        if (article == null) {
            presenter.prepareFailView("Please load an article before saving.");
        } else {
            try {
                dataAccessInterface.saveArticle(article);
                SaveArticleOutputData outputData = new SaveArticleOutputData();
                presenter.prepareSuccessView(outputData);
            } catch (IOException exception) {
                presenter.prepareFailView(exception.getMessage());
            }
        }
    }
}
