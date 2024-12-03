package use_case.random_article;

/**
 * The Input Data for the Random Article Use Case.
 */
public class RandomArticleInputData {
    private final String country;

    public RandomArticleInputData(String country) {
        this.country = country;
    }

    /**
     * Returns the country which the use case will find an article from.
     * @return the country which the use case will find an article from.
     */
    public String getCountry() {
        return country;
    }
}
