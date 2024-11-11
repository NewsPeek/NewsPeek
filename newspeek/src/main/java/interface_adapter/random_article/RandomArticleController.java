package interface_adapter.random_article;

import entity.censorship_rule_set.CensorshipRuleSet;
import use_case.random_article.RandomArticleInputBoundary;
import use_case.random_article.RandomArticleInputData;

import javax.swing.*;

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
     * @param censorshipRuleSet the censorship ruleset used to return the censored article
     */
    public void execute(String country, CensorshipRuleSet censorshipRuleSet) {
        final RandomArticleInputData inputData = new RandomArticleInputData(country, censorshipRuleSet);
        this.randomArticleInteractor.execute(inputData);
    }
}
