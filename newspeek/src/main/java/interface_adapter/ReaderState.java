package interface_adapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;

/**
 * The State information representing the reader view.
 */
public class ReaderState {
    private Article article;
    private Article censoredArticle;
    private CensorshipRuleSet censorshipRuleSet;
    private String error;
    private String alert;
    private Map<String, String> savedArticleList;

    public ReaderState() {
        // Set up a default censorshipRuleSet
        Set<String> defaultProhibitedWords = new HashSet<>();
        Map<String, String> defaultReplacedWords = new HashMap<>();
        boolean defaultCaseSensitive = false;
        String defaultRuleSetName = "Default Rule Set";
        defaultProhibitedWords.add("the");
        defaultReplacedWords.put("Trump", "peepee");
        this.censorshipRuleSet = new CommonCensorshipRuleSet(
                defaultProhibitedWords, defaultReplacedWords, defaultCaseSensitive, defaultRuleSetName);
    }

    public Map<String, String> getArticleList(){
        return savedArticleList;
    }

    public void setArticleList(Map<String, String> articleList){
        this.savedArticleList = articleList;
    }
    /**
     * Returns the uncensored article currently being displayed.
     * @return the uncensored article currently being displayed.
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Set the uncensored article to be displayed.
     * @param article the uncensored article to be displayed.
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * Returns the censored article currently being displayed.
     * @return the censored article currently being displayed.
     */
    public Article getCensoredArticle() {
        return censoredArticle;
    }

    /**
     * Set the censored article to be displayed.
     * @param article the censored article to be displayed.
     */
    public void setCensoredArticle(Article article) {
        this.censoredArticle = article;
    }

    /**
     * Returns the censorship ruleset currently in effect.
     * @return the censorship ruleset currently in effect.
     */
    public CensorshipRuleSet getCensorshipRuleSet() {
        return censorshipRuleSet;
    }

    /**
     * Sets the censorship ruleset to apply to the current article.
     * @param censorshipRuleSet the censorship ruleset to apply.
     */
    public void setCensorshipRuleSet(CensorshipRuleSet censorshipRuleSet) {
        this.censorshipRuleSet = censorshipRuleSet;
    }

    /**
     * Returns the most recent error thrown by a Use Case.
     * This error should have been displayed the moment it was thrown.
     * @return the most recent error thrown by a Use Case.
     */
    public String getError() {
        return error;
    }

    /**
     * Sets an error to display upon processing this State.
     * @param error an informative error message to show the user.
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Returns the most alert written by a Use Case.
     * This alert should have been displayed the moment it was written.
     * @return the most alert written by a Use Case.
     */
    public String getAlert() {
        return alert;
    }

    /**
     * Sets an alert to display upon processing this State.
     * @param alert an informative alert message to show the user.
     */
    public void setAlert(String alert) {
        this.alert = alert;
    }

    public Map<String, String> getSavedArticleList() {
        return savedArticleList;
    }

    public void setSavedArticleList(Map<String, String> savedArticleList) {
        this.savedArticleList = savedArticleList;
    }

    public void firePropertyChanged(String article) {
    }
}
