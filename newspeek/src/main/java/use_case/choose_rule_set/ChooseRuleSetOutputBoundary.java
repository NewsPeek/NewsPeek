package use_case.choose_rule_set;

public interface ChooseRuleSetOutputBoundary {
    void prepareSuccessView(ChooseRuleSetOutputData outputData);

    void prepareFailView(String error);
}
