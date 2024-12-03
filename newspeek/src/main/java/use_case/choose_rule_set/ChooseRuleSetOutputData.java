package use_case.choose_rule_set;

import entity.censorship_rule_set.CensorshipRuleSet;

/**
 * Output data for the Choose Rule Set use case.
 * This object should only be created if the new rule set was successfully loaded.
 */
public class ChooseRuleSetOutputData {
    private final CensorshipRuleSet censorshipRuleSet;

    public ChooseRuleSetOutputData(CensorshipRuleSet censorshipRuleSet) {
        this.censorshipRuleSet = censorshipRuleSet;
    }

    /**
     * Returns the loaded CensorshipRuleSet.
     * @return the loaded CensorshipRuleSet.
     */
    public CensorshipRuleSet getCensorshipRuleSet() {
        return censorshipRuleSet;
    }
}
