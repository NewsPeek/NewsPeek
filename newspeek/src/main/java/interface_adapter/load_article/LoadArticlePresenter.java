package interface_adapter.load_article;

import interface_adapter.ReaderViewModel;
import use_case.load_article.LoadArticleOutputBoundary;
import use_case.load_article.LoadArticleOutputData;

/**
 * The Presenter for the Load Article Use Case.
 */
public class LoadArticlePresenter implements LoadArticleOutputBoundary {
    private final ReaderViewModel readerViewModel;

    public LoadArticlePresenter(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
    }

    @Override
    public void prepareSuccessView(LoadArticleOutputData outputData) {
        this.readerViewModel.setArticle(outputData.getArticle());
        this.readerViewModel.firePropertyChanged("article");
    }

    @Override
    public void prepareFailView(String error) {
        this.readerViewModel.setError("Failed to load article: " + error);
        this.readerViewModel.firePropertyChanged("error");
    }
}
