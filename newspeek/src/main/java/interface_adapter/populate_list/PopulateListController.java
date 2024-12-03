package interface_adapter.populate_list;

import use_case.populate_list_with_articles.PopulateListInputBoundary;

/**
 * The Controller for the Populate List Use Case.
 */
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
