package use_case.load_url;

import java.io.IOException;
import java.net.MalformedURLException;

import entity.article.Article;

/**
 * The Interactor for the Load URL use case.
 */
public class LoadURLInteractor implements LoadURLInputBoundary {
    private final LoadURLDataAccessInterface loadURLDataAccessInterface;

    private final LoadURLOutputBoundary loadURLPresenter;

    public LoadURLInteractor(LoadURLDataAccessInterface loadURLDataAccessInterface,
                             LoadURLOutputBoundary loadURLPresenter) {
        this.loadURLDataAccessInterface = loadURLDataAccessInterface;
        this.loadURLPresenter = loadURLPresenter;
    }

    /**
     * Execute the Use Case.
     * @param loadURLInputData The data that is passed in.
     */
    @Override
    public void execute(LoadURLInputData loadURLInputData) {
        try {
            // Get Article from URLDataAccess object
            final Article urlData = loadURLDataAccessInterface.getArticleFromUrl(loadURLInputData.getURL());

            // Load Output data into loadURLOutputData
            final LoadURLOutputData loadURLOutputData = new LoadURLOutputData(urlData);
            loadURLPresenter.prepareSuccessView(loadURLOutputData);
        } catch (IOException ex) {
            if (ex instanceof MalformedURLException) {
                loadURLPresenter.prepareFailView("Invalid URL: " + ex.getMessage());
            } else {
                loadURLPresenter.prepareFailView("Failed loading from URL: " + ex.getMessage());
            }
        }
    }
}
