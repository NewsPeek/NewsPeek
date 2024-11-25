package use_case.choose_rule_set;

import java.io.File;
import java.io.IOException;

import entity.censorship_rule_set.CensorshipRuleSet;

/**
 * Interactor for the Choose Rule Set use case. Uses the provided Data Access Interface to load a new
 * CensorshipRuleSet.
 */
public class ChooseRuleSetInteractor implements ChooseRuleSetInputBoundary {
    private final ChooseRuleSetDataAccessInterface chooseRuleSetDataAccessInterface;
    private final ChooseRuleSetOutputBoundary chooseRuleSetPresenter;

    public ChooseRuleSetInteractor(ChooseRuleSetDataAccessInterface chooseRuleSetDataAccessInterface,
                                   ChooseRuleSetOutputBoundary chooseRuleSetPresenter) {
        this.chooseRuleSetDataAccessInterface = chooseRuleSetDataAccessInterface;
        this.chooseRuleSetPresenter = chooseRuleSetPresenter;
    }

    @Override
    public void execute(ChooseRuleSetInputData chooseRuleSetInputData) {
        File file = chooseRuleSetInputData.getFile();
        try {
            CensorshipRuleSet ruleSet = this.chooseRuleSetDataAccessInterface.getCensorshipRuleSet(file);
            ChooseRuleSetOutputData outputData = new ChooseRuleSetOutputData(ruleSet);
            chooseRuleSetPresenter.prepareSuccessView(outputData);
        } catch (IOException exception) {
            chooseRuleSetPresenter.prepareFailView(exception.getMessage());
        }
    }
}
