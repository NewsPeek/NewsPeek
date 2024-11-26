package use_case.choose_rule_set;

import java.io.File;

/**
 * Input data for the Choose Rule Set use case. Contains a file from which to load the CensorshipRuleSet.
 */
public class ChooseRuleSetInputData {
    private final File file;

    public ChooseRuleSetInputData(File file) {
        this.file = file;
    }

    /**
     * Returns the file from which to load the CensorshipRuleSet.
     * @return the file from which to load the CensorshipRuleSet.
     */
    public File getFile() {
        return file;
    }
}
