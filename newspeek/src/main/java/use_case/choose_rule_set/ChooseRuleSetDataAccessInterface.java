package use_case.choose_rule_set;

import entity.censorship_rule_set.CensorshipRuleSet;

public interface ChooseRuleSetDataAccessInterface {
    /**
     * Returns a specified censorship rule set.
     * @param name The name of the rule set.
     * @return a censorship rule set.
     * @throws java.io.IOException if an error occurs in the process of getting the rule set (e.g. rule set not found).
     */
    CensorshipRuleSet getCensorshipRuleSet(String name) throws java.io.IOException;
}
