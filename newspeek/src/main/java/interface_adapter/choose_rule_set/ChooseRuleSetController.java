package interface_adapter.choose_rule_set;

import java.io.File;

import use_case.choose_rule_set.ChooseRuleSetInputBoundary;
import use_case.choose_rule_set.ChooseRuleSetInputData;

/**
 * Controller for the Change Password Use Case.
 */
public class ChooseRuleSetController {
    private final ChooseRuleSetInputBoundary chooseRuleSetInteractor;

    public ChooseRuleSetController(ChooseRuleSetInputBoundary chooseRuleSetInteractor) {
        this.chooseRuleSetInteractor = chooseRuleSetInteractor;
    }

    /**
     * Executes the Choose Rule Set Use Case.
     * @param file the file from which to laod the ruleset
     */
    public void execute(File file) {
        final ChooseRuleSetInputData inputData = new ChooseRuleSetInputData(file);
        this.chooseRuleSetInteractor.execute(inputData);
    }
}
