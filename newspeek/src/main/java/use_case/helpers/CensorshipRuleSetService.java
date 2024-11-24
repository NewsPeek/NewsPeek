package use_case.helpers;

import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CensorshipRuleSetDataAccessInterface;

import java.io.File;
import java.io.IOException;

public class CensorshipRuleSetService {
    private final CensorshipRuleSetDataAccessInterface dataAccess;

    public CensorshipRuleSetService(CensorshipRuleSetDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }

    public CensorshipRuleSet loadCensorshipRuleSet(File file) throws IOException {
        return dataAccess.loadFromFile(file);
    }
}
