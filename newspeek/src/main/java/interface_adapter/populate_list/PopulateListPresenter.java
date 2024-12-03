package interface_adapter.populate_list;

import interface_adapter.ReaderState;
import interface_adapter.ReaderViewModel;
import use_case.populate_list_with_articles.PopulateListOutputBoundary;
import use_case.populate_list_with_articles.PopulateListOutputData;

public class PopulateListPresenter implements PopulateListOutputBoundary {


    private ReaderViewModel readerViewModel;
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

    }
}
