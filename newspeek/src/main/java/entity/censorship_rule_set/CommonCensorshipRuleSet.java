package entity.censorship_rule_set;

import java.util.*;

public class CommonCensorshipRuleSet implements CensorshipRuleSet {
    private final Set<String> prohibitedWords;
    private final Map<String, String> replacedWords;
    private final Boolean caseSensitive;
    private final String ruleSetName;

    public CommonCensorshipRuleSet(Set<String> prohibitedWords, Map<String, String> replacedWords, Boolean caseSensitive, String ruleSetName) {
        this.caseSensitive = caseSensitive;
        this.ruleSetName = ruleSetName;

        if (caseSensitive) {
            // Simply use the given set and map
            this.prohibitedWords = prohibitedWords;
            this.replacedWords = replacedWords;
        } else {
            // Create new set and map for lowercasing
            this.prohibitedWords = new HashSet<>();
            this.replacedWords = new HashMap<>();

            // Lowercase all prohibited words
            for (String word : prohibitedWords) {
                this.prohibitedWords.add(word.toLowerCase());
            }

            // Lowercase all replacement target words (but not the words they're replaced with)
            for (String word : replacedWords.keySet()) {
                this.replacedWords.put(word.toLowerCase(), replacedWords.get(word));
            }
        }
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
