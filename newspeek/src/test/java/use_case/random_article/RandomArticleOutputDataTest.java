package use_case.random_article;

import entity.article.Article;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RandomArticleOutputDataTest {
    @Test
    void articleTest() {
        RandomArticleOutputData outputData = new RandomArticleOutputData(Article.mockArticle());
        assertEquals(Article.mockArticle(), outputData.getArticle());
    }
}
