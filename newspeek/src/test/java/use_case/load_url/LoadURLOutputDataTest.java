package use_case.load_url;

import entity.article.Article;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadURLOutputDataTest {
    @Test
    void successTest() {
        LoadURLOutputData outputData = new LoadURLOutputData(Article.mockArticle());
        assertEquals(Article.mockArticle(), outputData.getArticle());
    }

}
