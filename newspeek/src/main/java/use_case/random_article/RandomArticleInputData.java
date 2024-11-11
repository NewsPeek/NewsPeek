package use_case.random_article;

import entity.censorship_rule_set.CensorshipRuleSet;

import javax.swing.*;

/**
 * The Input Data for the Logout Use Case.
 */
public class RandomArticleInputData {
    private final String country;
    private final CensorshipRuleSet censorshipRuleSet;

    public RandomArticleInputData(String country, CensorshipRuleSet censorshipRuleSet) {
        this.country = country;
        this.censorshipRuleSet = censorshipRuleSet;
    }

    public String getCountry() {
        return country;
    }

    public CensorshipRuleSet getCensorshipRuleSet() {
        return censorshipRuleSet;
    }
}
