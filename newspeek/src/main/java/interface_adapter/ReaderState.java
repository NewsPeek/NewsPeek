package interface_adapter;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The State information representing the logged-in user.
 */
public class ReaderState {
    private Article article;
    private CensorshipRuleSet censorshipRuleSet;
    private String error;
    private Set<String> defaultProhibitedWords;
    private Map<String, String> defaultReplacedWords;
    private Boolean defaultCaseSensitive;
    private String defaultRuleSetName;


    public ReaderState() {

       defaultProhibitedWords = new HashSet<String>();
        defaultReplacedWords = new HashMap<String, String>();
        defaultCaseSensitive = false;
        defaultRuleSetName = "Default Rule Set";
        defaultProhibitedWords.add("the");
        defaultReplacedWords.put("Trump", "peepee");
        this.censorshipRuleSet=new CommonCensorshipRuleSet(defaultProhibitedWords, defaultReplacedWords, defaultCaseSensitive, defaultRuleSetName);
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public CensorshipRuleSet getCensorshipRuleSet() {
        return censorshipRuleSet;
    }

    public void setCensorshipRuleSet(CensorshipRuleSet censorshipRuleSet) {
        this.censorshipRuleSet = censorshipRuleSet;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
