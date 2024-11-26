package interface_adapter.export_article;

import interface_adapter.ReaderViewModel;
import use_case.export_article.ExportArticleOutputBoundary;
import use_case.export_article.ExportArticleOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class ExportArticlePresenter implements ExportArticleOutputBoundary {
    private final ReaderViewModel readerViewModel;

    public ExportArticlePresenter(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    @Override
    public void prepareSuccessView(ExportArticleOutputData outputData) {
        // TODO: decide if user needs confirmation
    }

    @Override
    public void prepareFailView(String error) {
        this.readerViewModel.setError("Failed to save article: " + error);
        this.readerViewModel.firePropertyChanged("error");
    }
}
