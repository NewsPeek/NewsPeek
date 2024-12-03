package use_case.load_article;

import java.io.IOException;

import entity.article.Article;

/**
 * Interactor for the Load Article use case. Uses the provided Data Access Interface to load the chosen article.
 */
public class LoadArticleInteractor implements LoadArticleInputBoundary {
    private final LoadArticleDataAccessInterface dataAccessInterface;
    private final LoadArticleOutputBoundary presenter;

    public LoadArticleInteractor(LoadArticleDataAccessInterface dataAccessInterface,
                                 LoadArticleOutputBoundary presenter) {
        this.dataAccessInterface = dataAccessInterface;
        this.presenter = presenter;
    }

    @Override
    public void execute(LoadArticleInputData inputData) {
        String id = inputData.getId();
        if (id == null) {
            presenter.prepareFailView("Must choose an article to load.");
        } else {
            try {
                Article article = dataAccessInterface.loadArticle(id);
                LoadArticleOutputData outputData = new LoadArticleOutputData(article);
                presenter.prepareSuccessView(outputData);
            } catch (IOException exception) {
                presenter.prepareFailView(exception.getMessage());
            }
        }
    }
}
