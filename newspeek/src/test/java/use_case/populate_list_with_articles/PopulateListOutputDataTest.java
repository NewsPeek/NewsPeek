package use_case.populate_list_with_articles;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

class PopulateListOutputDataTest {
    @Test
    void successTest() {
        Map<String, String> articles = new HashMap<>();
        articles.put("1", "a");
        articles.put("2", "b");
        articles.put("3", "c");
        PopulateListOutputData outputData = new PopulateListOutputData(articles);
        assertEquals(outputData.getArticleList(), articles);
    }
}
