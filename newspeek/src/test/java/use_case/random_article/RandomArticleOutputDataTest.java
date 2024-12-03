package use_case.random_article;

import entity.article.Article;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomArticleOutputDataTest {
    @Test
    void successTest() {
        RandomArticleOutputData outputData = new RandomArticleOutputData(Article.mockArticle(), false);
        assertEquals(Article.mockArticle(), outputData.getArticle());
        assertFalse(outputData.isUseCaseFailed());
    }

    @Test
    void failureTest() {
        RandomArticleOutputData outputData = new RandomArticleOutputData(Article.mockArticle(), true);
        assertEquals(Article.mockArticle(), outputData.getArticle());
        assertTrue(outputData.isUseCaseFailed());
    }
}
