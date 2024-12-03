package interface_adapter.populate_list;

import use_case.choose_rule_set.ChooseRuleSetInputBoundary;
import use_case.choose_rule_set.ChooseRuleSetInputData;
import use_case.populate_list_with_articles.PopulateListInputBoundary;

import java.io.File;

public class PopulateListController {

    private final PopulateListInputBoundary interactor;

    public PopulateListController(PopulateListInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes populate list Use Case.
     */
    public void execute() {
        this.interactor.execute();
    }
}
