package use_case.choose_rule_set;

import entity.censorship_rule_set.CensorshipRuleSet;

public class ChooseRuleSetOutputData {
    private final CensorshipRuleSet censorshipRuleSet;

    public ChooseRuleSetOutputData(CensorshipRuleSet censorshipRuleSet) {
        this.censorshipRuleSet = censorshipRuleSet;
    }

    public CensorshipRuleSet getCensorshipRuleSet() {
        return censorshipRuleSet;
    }
}
