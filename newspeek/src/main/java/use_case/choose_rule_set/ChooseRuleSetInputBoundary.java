package use_case.choose_rule_set;

public interface ChooseRuleSetInputBoundary {
    /**
     * Execute the Choose Rule Set use case.
     * @param chooseRuleSetInputData the input data for the use case.
     */
    void execute(ChooseRuleSetInputData chooseRuleSetInputData);
}
