package use_case.choose_rule_set;

/**
 * Output boundary for the Choose Rule Set use case.
 */
public interface ChooseRuleSetOutputBoundary {
    /**
     * Re-display the article with the given CensorshipRuleSet applied.
     * @param outputData an object containing the new CensorshipRuleSet.
     */
    void prepareSuccessView(ChooseRuleSetOutputData outputData);

    /**
     * Display an error returned by the Interactor in a pop-up. Do not change the article display.
     * @param error a descriptive error to show the user in a pop-up.
     */
    void prepareFailView(String error);
}
