package use_case.load_url;

import entity.article.Article;

import java.io.IOException;

public class LoadURLInteractor implements LoadURLInputBoundary{

    private final LoadURLDataAccessInterface loadURLDataAccessInterface;

    private final LoadURLOutputBoundary loadURLPresenter;

    public LoadURLInteractor(LoadURLDataAccessInterface loadURLDataAccessInterface,
                             LoadURLOutputBoundary loadURLPresenter){
        this.loadURLDataAccessInterface = loadURLDataAccessInterface;
        this.loadURLPresenter = loadURLPresenter;
    }
    /**
     * @param loadURLInputData The data that is passed in.
     */
    @Override
    public void execute(LoadURLInputData loadURLInputData) {
        try {
            // Get Article from URLDataAccess object
            final Article URLdata = loadURLDataAccessInterface.getArticleFromUrl(loadURLInputData.getURL());

            // Load Output data into loadURLOutputData
            final LoadURLOutputData loadURLOutputData = new LoadURLOutputData(URLdata, false);
            loadURLPresenter.prepareSuccessView(loadURLOutputData);
        }
        catch(IOException e) {
            loadURLPresenter.prepareFailView("Loading the URL did not work.");
        }
    }
}
