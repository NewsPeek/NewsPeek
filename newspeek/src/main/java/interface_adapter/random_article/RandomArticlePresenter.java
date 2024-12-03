package interface_adapter.random_article;

import interface_adapter.ReaderViewModel;
import use_case.random_article.RandomArticleOutputBoundary;
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
        this.readerViewModel.setArticle(outputData.getArticle());
        this.readerViewModel.firePropertyChanged("article");
    }

    @Override
    public void prepareFailView(String error) {
        this.readerViewModel.setError("Failed to get random article: " + error);
        this.readerViewModel.firePropertyChanged("error");
    }
}
