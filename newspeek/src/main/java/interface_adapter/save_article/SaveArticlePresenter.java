package interface_adapter.save_article;

import interface_adapter.ReaderViewModel;
import use_case.save_article.SaveArticleOutputBoundary;

/**
 * The Presenter for the Change Password Use Case.
 */
public class SaveArticlePresenter implements SaveArticleOutputBoundary {
    private final ReaderViewModel readerViewModel;

    public SaveArticlePresenter(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    @Override
    public void prepareSuccessView() {
        this.readerViewModel.setAlert("Saved article successfully.");
        this.readerViewModel.firePropertyChanged("alert");
    }

    @Override
    public void prepareFailView(String error) {
        this.readerViewModel.setError("Failed to save article: " + error);
        this.readerViewModel.firePropertyChanged("error");
    }
}
