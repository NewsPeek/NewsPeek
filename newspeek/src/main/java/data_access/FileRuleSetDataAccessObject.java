package data_access;

import entity.censorship_rule_set.CensorshipRuleSet;
import use_case.choose_rule_set.ChooseRuleSetDataAccessInterface;

import java.io.IOException;

public class FileRuleSetDataAccessObject implements ChooseRuleSetDataAccessInterface {

    @Override
    public CensorshipRuleSet getCensorshipRuleSet(String name) throws IOException {
        return null;
    }
}
