package use_case.random_article;

/**
 * The Input Data for the Logout Use Case.
 */
public class RandomArticleInputData {
    private final String country;

    public RandomArticleInputData(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
