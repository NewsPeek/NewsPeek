package use_case.load_url;

/**
 * The input data for the Load URL Use Case.
 */
public class LoadURLInputData {
    private final String url;

    /**
     * The constructor for the LoadURLInputData class.
     * @param url the URL to load from
     */
    public LoadURLInputData(String url) {
        this.url = url;
    }

    /**
     * Getter for the URL.
     * @return the URL as a String.
     */
    public String getURL() {
        return url;
    }

}
