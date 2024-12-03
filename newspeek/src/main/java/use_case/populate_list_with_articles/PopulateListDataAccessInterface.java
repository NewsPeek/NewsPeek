package use_case.populate_list_with_articles;

import java.util.Map;

public interface PopulateListDataAccessInterface {

    Map<String, String> listSavedArticles() throws java.io.IOException;
}
