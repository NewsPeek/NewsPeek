package use_case.load_url;

import data_access.article.MemoryArticleDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.random_article.RandomArticleInputData;
import use_case.random_article.RandomArticleInteractor;

import static org.junit.jupiter.api.Assertions.*;

public class LoadURLInteractorTest {

    private LoadURLDataAccessInterface LoadURLDAO;
    @BeforeEach
    void createDAO() {
        this.LoadURLDAO = new MemoryArticleDataAccessObject();
    }

    @Test
    void successTest() {
        LoadURLInputData inputData = new LoadURLInputData("https://www.example.com");

        final boolean[] presenterCalled = {false};

        LoadURLOutputBoundary successPresenter = new LoadURLOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadURLOutputData outputData) {
                presenterCalled[0] = true;
                // From MemoryArticleDataAccessObject.makeMockArticle()
                assertEquals("Sample Article", outputData.getArticle().getTitle());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case should not fail with website 'www.example.com'.");
            }
        };

        LoadURLInteractor interactor = new LoadURLInteractor(LoadURLDAO, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]);
    }

    @Test
    void failTest() {
        LoadURLInputData inputData = new LoadURLInputData("https://www.example.com");

        final boolean[] presenterCalled = {false};

        LoadURLOutputBoundary successPresenter = new LoadURLOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadURLOutputData outputData) {
                presenterCalled[0] = true;
                // From MemoryArticleDataAccessObject.makeMockArticle()
                assertEquals("Sample Article", outputData.getArticle().getTitle());
                assertFalse(outputData.isUseCaseFailed());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case should not fail with website 'www.example.com'.");
            }
        };

        LoadURLInteractor interactor = new LoadURLInteractor(LoadURLDAO, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]);
    }
}
