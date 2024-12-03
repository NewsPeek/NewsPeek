package use_case.load_article;

import entity.article.Article;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadArticleOutputDataTest {
    @Test
    void successTest() {
        LoadArticleOutputData outputData = new LoadArticleOutputData(Article.mockArticle());
        assertEquals(Article.mockArticle(), outputData.getArticle());
    }
}
