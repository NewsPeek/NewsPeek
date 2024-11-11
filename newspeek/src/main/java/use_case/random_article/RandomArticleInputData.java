package use_case.random_article;

import entity.censorship_rule_set.CensorshipRuleSet;

import javax.swing.*;

/**
 * The Input Data for the Logout Use Case.
 */
public class RandomArticleInputData {
    private final String country;
    private final CensorshipRuleSet censorshipRuleSet;
    private final JTextArea textArea;

    public RandomArticleInputData(String country, CensorshipRuleSet censorshipRuleSet, JTextArea jTextArea) {
        this.country = country;
        this.censorshipRuleSet = censorshipRuleSet;
        this.textArea = jTextArea;
    }

    public String getCountry() {
        return country;
    }

    public CensorshipRuleSet getCensorshipRuleSet() {
        return censorshipRuleSet;
    }
    public JTextArea getTextArea() {return textArea;}
}
