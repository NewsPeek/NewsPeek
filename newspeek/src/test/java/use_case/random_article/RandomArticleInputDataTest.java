package use_case.random_article;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import use_case.random_article.RandomArticleInputData;

class RandomArticleInputDataTest {
    @Test
    void countryTest() {
        RandomArticleInputData inputData = new RandomArticleInputData("Xyzzystan");
        assertEquals("Xyzzystan", inputData.getCountry());
    }
}