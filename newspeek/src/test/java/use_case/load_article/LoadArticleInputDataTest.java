package use_case.load_article;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadArticleInputDataTest {
    @Test
    void articleTest() {
        LoadArticleInputData inputData = new LoadArticleInputData("1234567890");
        assertEquals("1234567890", inputData.getId());
    }
}
