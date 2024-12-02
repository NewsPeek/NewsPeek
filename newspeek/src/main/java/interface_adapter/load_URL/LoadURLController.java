package interface_adapter.load_URL;

import use_case.load_url.LoadURLInputBoundary;
import use_case.load_url.LoadURLInputData;

public class LoadURLController {
    private LoadURLInputBoundary loadURLInteractor;
    public LoadURLController(LoadURLInputBoundary loadURLInteractor) {
        this.loadURLInteractor = loadURLInteractor;
    }
}
