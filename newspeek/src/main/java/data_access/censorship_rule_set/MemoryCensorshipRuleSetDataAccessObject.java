package data_access.censorship_rule_set;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;
import use_case.choose_rule_set.ChooseRuleSetDataAccessInterface;

/**
 * Data Access Object that returns a fixed CensorshipRuleSet for testing purposes.
 */
public class MemoryCensorshipRuleSetDataAccessObject implements ChooseRuleSetDataAccessInterface {
    @Override
    public CensorshipRuleSet getCensorshipRuleSet(File file) {
        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        Boolean caseSensitive = false;
        String ruleSetName = "Test Rules";

        prohibitedWords.add("idea");

        replacedWords.put("subversion", "compliance");

        return new CommonCensorshipRuleSet(prohibitedWords, replacedWords, caseSensitive, ruleSetName);
    }
}
