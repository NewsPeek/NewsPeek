package interface_adapter.random_article;

import use_case.random_article.RandomArticleOutputBoundary;
import interface_adapter.ReaderViewModel;
import use_case.random_article.RandomArticleOutputData;

/**
 * The Presenter for the Change Password Use Case.
 */
public class RandomArticlePresenter implements RandomArticleOutputBoundary {

    private final ReaderViewModel readerViewModel;

    public RandomArticlePresenter(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    @Override
    public void prepareSuccessView(RandomArticleOutputData outputData) {
        // currently there isn't anything to change based on the output data,
        // since the output data only contains the username, which remains the same.
        // We still fire the property changed event, but just to let the view know that
        // it can alert the user that their password was changed successfully.
        this.readerViewModel.firePropertyChanged("article");

    }

    @Override
    public void prepareFailView(String error) {
        // note: this use case currently can't fail
    }
}
