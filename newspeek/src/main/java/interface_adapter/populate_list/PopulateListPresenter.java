package interface_adapter.populate_list;

import interface_adapter.ReaderViewModel;
import use_case.populate_list_with_articles.PopulateListOutputBoundary;
import use_case.populate_list_with_articles.PopulateListOutputData;

/**
 * Class to Present the Populate list Use Case.
 */
public class PopulateListPresenter implements PopulateListOutputBoundary {
    private final ReaderViewModel readerViewModel;

    /**
     * Constructor for the Populate List Use Case Presenter.
     * @param readerViewModel the reader view model to populate.
     */
    public PopulateListPresenter(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    @Override
    public void prepareSuccessView(PopulateListOutputData outputData) {
        this.readerViewModel.setArticleList(outputData.getArticleList());
        this.readerViewModel.firePropertyChanged("article-list");
    }

    @Override
    public void prepareFailView(String error) {
        this.readerViewModel.setError("Failed to populate list: " + error);
        this.readerViewModel.firePropertyChanged("error");

    }
}
