package use_case.choose_rule_set;

import java.io.File;

import entity.censorship_rule_set.CensorshipRuleSet;

/**
 * Data Access Interface for loading a CensorshipRuleSet from some external source.
 */
public interface ChooseRuleSetDataAccessInterface {
    /**
     * Returns a specified censorship rule set.
     * @param file The file containing the rule set.
     * @return a censorship rule set.
     * @throws java.io.IOException if an error occurs in the process of getting the rule set (e.g. rule set not found).
     */
    CensorshipRuleSet getCensorshipRuleSet(File file) throws java.io.IOException;
}
