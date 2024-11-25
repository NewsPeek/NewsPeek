package use_case.choose_rule_set;

import java.io.File;

public class ChooseRuleSetInputData {
    private final File file;

    public ChooseRuleSetInputData(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
