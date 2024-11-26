package use_case.random_article;

import entity.article.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import use_case.random_article.RandomArticleOutputData;

import java.time.LocalDateTime;

class RandomArticleOutputDataTest {
    Article mockArticle;

    @BeforeEach
    void createMockArticle() {
        final String title = "Sample Article";
        final String text = "lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.";
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        this.mockArticle = new Article(title, text, source, author, agency, postedAt);
    }

    @Test
    void successTest() {
        RandomArticleOutputData outputData = new RandomArticleOutputData(mockArticle, false);
        assertEquals(mockArticle, outputData.getArticle());
        assertFalse(outputData.isUseCaseFailed());
    }

    @Test
    void failureTest() {
        RandomArticleOutputData outputData = new RandomArticleOutputData(mockArticle, true);
        assertEquals(mockArticle, outputData.getArticle());
        assertTrue(outputData.isUseCaseFailed());
    }
}
