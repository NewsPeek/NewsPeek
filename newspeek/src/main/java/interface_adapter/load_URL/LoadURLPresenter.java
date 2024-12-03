package interface_adapter.load_URL;

import interface_adapter.ReaderViewModel;

import use_case.load_url.LoadURLOutputBoundary;
import use_case.load_url.LoadURLOutputData;

public class LoadURLPresenter implements LoadURLOutputBoundary {

    private final ReaderViewModel readerViewModel;

    public LoadURLPresenter (ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    /**
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(LoadURLOutputData outputData) {
        this.readerViewModel.setArticle(outputData.getArticle());
        this.readerViewModel.firePropertyChanged("article");
    }

    /**
     * Prepares the failure view for the Login Use Case.
     * @param error the explanation of the failure
     */
    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
        System.err.println("Unhandled failure in RandomArticlePresenter. Please fix this.");
        System.err.println(error);
    }
}
