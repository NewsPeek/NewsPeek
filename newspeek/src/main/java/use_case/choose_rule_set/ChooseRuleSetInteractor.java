package use_case.choose_rule_set;

import entity.censorship_rule_set.CensorshipRuleSet;

import java.io.File;
import java.io.IOException;

public class ChooseRuleSetInteractor implements ChooseRuleSetInputBoundary {
    private final ChooseRuleSetDataAccessInterface chooseRuleSetDataAccessInterface;
    private final ChooseRuleSetOutputBoundary chooseRuleSetPresenter;

    public ChooseRuleSetInteractor(ChooseRuleSetDataAccessInterface chooseRuleSetDataAccessInterface, ChooseRuleSetOutputBoundary chooseRuleSetPresenter) {
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
        } catch (IOException e) {
            chooseRuleSetPresenter.prepareFailView(e.getMessage());
        }
    }
}
