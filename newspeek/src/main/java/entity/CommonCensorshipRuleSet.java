package entity;

import java.util.Map;
import java.util.Set;

public class CommonCensorshipRuleSet implements CensorshipRuleSet {
    private final Set<String> prohibitedWords;
    private final Map<String, String> replacedWords;
    private final Boolean caseSensitive;
    private final String ruleSetName;

    public CommonCensorshipRuleSet(Set<String> prohibitedWords, Map<String, String> replacedWords, Boolean caseSensitive, String ruleSetName) {
        this.prohibitedWords = prohibitedWords;
        this.replacedWords = replacedWords;
        this.caseSensitive = caseSensitive;
        this.ruleSetName = ruleSetName;
    }

    @Override
    public Set<String> getProhibitions() {
        return prohibitedWords;
    }

    @Override
    public Map<String, String> getReplacements() {
        return replacedWords;
    }

    @Override
    public Boolean isCaseSensitive() {
        return caseSensitive;
    }

    @Override
    public String getRuleSetName() {
        return ruleSetName;
    }
}
