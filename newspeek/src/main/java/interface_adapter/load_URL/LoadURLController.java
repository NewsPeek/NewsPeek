package interface_adapter.load_URL;

import use_case.load_url.LoadURLInputBoundary;
import use_case.load_url.LoadURLInputData;

/**
 * Controller for the LoadURL Use Case.
 */
public class LoadURLController {
    private final LoadURLInputBoundary loadURLInteractor;
    public LoadURLController(LoadURLInputBoundary loadURLInteractor) {
        this.loadURLInteractor = loadURLInteractor;
    }

    /**
     * Executes the LoadURL Use Case.
     * @param url the URL that we get data from.
     */
    public void execute(String url) {
        final LoadURLInputData inputData = new LoadURLInputData(url);
        loadURLInteractor.execute(inputData);
    }
}
