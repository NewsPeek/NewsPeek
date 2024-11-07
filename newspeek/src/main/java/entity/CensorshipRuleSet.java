package entity;

import java.util.Map;
import java.util.Set;

public interface CensorshipRuleSet {
    /**
     * Returns the set of words prohibited by the ruleset.
     * @return the set of prohibited words.
     */
    Set<String> getProhibitions();

    /**
     * Returns a map from 'harmful' words to their 'safe' replacements.
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
