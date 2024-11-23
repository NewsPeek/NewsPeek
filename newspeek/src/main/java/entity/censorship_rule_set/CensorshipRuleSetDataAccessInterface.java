package entity.censorship_rule_set;

import java.io.File;
import java.io.IOException;

public interface CensorshipRuleSetDataAccessInterface {
    CensorshipRuleSet loadFromFile(File file) throws IOException;
}
