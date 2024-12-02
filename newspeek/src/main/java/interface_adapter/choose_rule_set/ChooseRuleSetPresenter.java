package interface_adapter.choose_rule_set;

import interface_adapter.ReaderViewModel;
import use_case.choose_rule_set.ChooseRuleSetOutputBoundary;
import use_case.choose_rule_set.ChooseRuleSetOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class ChooseRuleSetPresenter implements ChooseRuleSetOutputBoundary {
    private final ReaderViewModel readerViewModel;

    public ChooseRuleSetPresenter(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    @Override
    public void prepareSuccessView(ChooseRuleSetOutputData outputData) {
        this.readerViewModel.setCensorshipRuleSet(outputData.getCensorshipRuleSet());
        this.readerViewModel.firePropertyChanged("ruleset");
    }

    @Override
    public void prepareFailView(String error) {
        this.readerViewModel.setError("Failed to load rule set: " + error);
        this.readerViewModel.firePropertyChanged("error");
    }
}
