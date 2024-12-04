package use_case.load_url;

import data_access.article.MemoryArticleDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void failInvalidURLTest() {
        LoadURLInputData inputData = new LoadURLInputData("Lorem Ipsum Dolor Amet");

        final boolean[] presenterCalled = {false};

        LoadURLOutputBoundary successPresenter = new LoadURLOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadURLOutputData outputData) {
                presenterCalled[0] = true;
                // From MemoryArticleDataAccessObject.makeMockArticle()
                fail("Use case should not succeed with website Lorem Ipsum Dolor Amet");
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                final String firstErrorWord = error.split(" ")[0];
                assertEquals("Invalid", firstErrorWord);
            }
        };

        LoadURLInteractor interactor = new LoadURLInteractor(LoadURLDAO, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]);
    }

    @Test
    void failURLNotLoadedTest() {
        LoadURLInputData inputData = new LoadURLInputData("FAIL");
        final boolean[] presenterCalled = {false};

        LoadURLOutputBoundary successPresenter = new LoadURLOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadURLOutputData outputData) {
                presenterCalled[0] = true;
                // From MemoryArticleDataAccessObject.makeMockArticle()
                fail("Use case should not succeed with input data FAIL");
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                assertEquals("Failed loading from URL: getArticleFromUrl intentionally failed.", error);
            }
        };

        LoadURLInteractor interactor = new LoadURLInteractor(LoadURLDAO, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]);
    }
}
