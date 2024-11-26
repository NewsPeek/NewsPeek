package use_case.save_article;

import entity.article.Article;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveArticleInputDataTest {
    @Test
    void articleTest() {
        SaveArticleInputData inputData = new SaveArticleInputData(Article.mockArticle());
        assertEquals(Article.mockArticle(), inputData.getArticle());
    }
}
