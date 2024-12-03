package use_case.populate_list_with_articles;

import java.util.Map;

/**
 * The Data Access Interface for the Populate List Use Case.
 */
public interface PopulateListDataAccessInterface {
    /**
     * List the articles saved by the user.
     * @return A map of articles saved by the user.
     * @throws java.io.IOException when there is a problem with getting a file.
     */
    Map<String, String> listSavedArticles() throws java.io.IOException;
}
