package use_case.random_article;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;
import use_case.helpers.CensorshipService;
import use_case.helpers.ScanningCensorshipService;

import javax.swing.*;

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

            // Censor article
            final CensorshipService censorshipService = new ScanningCensorshipService();
            final CensorshipRuleSet censorshipRuleSet = randomArticleInputData.getCensorshipRuleSet();
            final Article censoredArticle = censorshipService.censor(article, censorshipRuleSet);

            // Populate output data
            final RandomArticleOutputData randomArticleOutputData = new RandomArticleOutputData(censoredArticle, false);

            // Prepare success view
            presenter.prepareSuccessView(randomArticleOutputData);
        } catch (Exception e) {
            presenter.prepareFailView(e.getMessage());
        }
    }
}
