package interface_adapter.random_article;

import use_case.random_article.RandomArticleInputBoundary;
import use_case.random_article.RandomArticleInputData;

/**
 * Controller for the Change Password Use Case.
 */
public class RandomArticleController {
    private final RandomArticleInputBoundary randomArticleInteractor;

    public RandomArticleController(RandomArticleInputBoundary randomArticleInteractor) {
        this.randomArticleInteractor = randomArticleInteractor;
    }

    /**
     * Executes the Change Password Use Case.
     * @param country the country for which to fetch news articles
     */
    public void execute(String country) {
        final RandomArticleInputData inputData = new RandomArticleInputData(country);
        this.randomArticleInteractor.execute(inputData);
    }
}
