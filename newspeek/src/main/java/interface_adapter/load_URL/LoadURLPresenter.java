package interface_adapter.load_URL;

import interface_adapter.ReaderViewModel;
import use_case.load_url.LoadURLOutputBoundary;
import use_case.load_url.LoadURLOutputData;

/**
 * The Presenter for the Load URL Use Case.
 */
public class LoadURLPresenter implements LoadURLOutputBoundary {

    private final ReaderViewModel readerViewModel;

    public LoadURLPresenter(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    /**
     * Prepares the success view for the Load URL Use Case.
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(LoadURLOutputData outputData) {
        this.readerViewModel.setArticle(outputData.getArticle());
        this.readerViewModel.firePropertyChanged("article");
    }

    /**
     * Prepares the failure view for the Load URL Use Case.
     * @param error the explanation of the failure
     */
    @Override
    public void prepareFailView(String error) {
        this.readerViewModel.setError(error);
        this.readerViewModel.firePropertyChanged("error");
    }
}
