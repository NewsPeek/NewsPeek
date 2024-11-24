package entity.censorship_rule_set;

import java.util.Map;
import java.util.Set;

/**
 * A named set of rules to prohibit and replace words in text.
 * Can be case-sensitive or -insensitive.
 */
public interface CensorshipRuleSet {
    /**
     * Returns the set of words prohibited by the ruleset.
     * If isCaseSensitive() returns false, this function will return only lowercase words.
     * @return the set of prohibited words.
     */
    Set<String> getProhibitions();

    /**
     * Returns a map from 'harmful' words to their 'safe' replacements.
     * If isCaseSensitive() returns false, the keys in this function's returned map will all be lowercase.
     * @return the map of word replacement rules.
     */
    Map<String, String> getReplacements();

    /**
     * Returns whether this ruleset is case-sensitive.
     * @return whether this ruleset is case-sensitive.
     */
    Boolean isCaseSensitive();

    /**
     * Returns the same of this ruleset.
     * @return the same of this ruleset.
     */
    String getRuleSetName();
}
