package use_case.random_article;

import java.io.IOException;

import entity.article.Article;

/**
 * The Random Article interactor.
 */
public class RandomArticleInteractor implements RandomArticleInputBoundary {
    private final RandomArticleAPIDataAccessInterface apiDataAccessInterface;
    private final RandomArticleOutputBoundary presenter;

    public RandomArticleInteractor(RandomArticleAPIDataAccessInterface randomArticleAPIDDataAccessInterface,
                                   RandomArticleOutputBoundary randomArticleOutputBoundary) {
        apiDataAccessInterface = randomArticleAPIDDataAccessInterface;
        presenter = randomArticleOutputBoundary;
    }

    @Override
    public void execute(RandomArticleInputData randomArticleInputData) {
        try {
            // Get article from Data Access Object
            final String country = randomArticleInputData.getCountry();
            final Article article = apiDataAccessInterface.getRandomArticle(country);

            // Populate output data
            final RandomArticleOutputData randomArticleOutputData = new RandomArticleOutputData(article, false);

            // Prepare success view
            presenter.prepareSuccessView(randomArticleOutputData);
        } catch (IOException exception) {
            presenter.prepareFailView(exception.getMessage());
        }
    }
}
