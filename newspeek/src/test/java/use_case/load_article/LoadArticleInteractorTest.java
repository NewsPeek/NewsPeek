package use_case.load_article;

import data_access.article.MemoryArticleDataAccessObject;
import entity.article.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.save_article.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LoadArticleInteractorTest {
    private MemoryArticleDataAccessObject dataAccessInterface;
    private String mockArticleId;

    @BeforeEach
    void createDAO() throws IOException {
        this.dataAccessInterface = new MemoryArticleDataAccessObject();

        // Save an article to the
        dataAccessInterface.saveArticle(Article.mockArticle());
        for (final String id : dataAccessInterface.listSavedArticles().keySet()) {
            if (Article.mockArticle().getTitle().equals(dataAccessInterface.listSavedArticles().get(id))) {
                mockArticleId = id;
                break;
            }
        }
        if (mockArticleId == null) {
            fail("Failed to save mock article in DAO.");
        }
    }

    /**
     * Test that when the DAO succeeds, LoadArticleInteractor will call
     * RandomArticlePresenter.prepareSuccessView().
     */
    @Test
    void successTest() {
        LoadArticleInputData inputData = new LoadArticleInputData(mockArticleId);

        // Track whether presenter was called. A broken Interactor could just not call the presenter,
        // and then it would pass the test as no assertions fail. This prevents that.
        // Unfortunately, IntelliJ forces us to use a 1-element array instead of a single boolean.
        // This is evil but there's probably a reason; something about being final.
        final boolean[] presenterCalled = {false};

        LoadArticleOutputBoundary successPresenter = new LoadArticleOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadArticleOutputData outputData) {
                assertEquals(Article.mockArticle(), outputData.getArticle());
                presenterCalled[0] = true;
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                fail("Use case should not fail on a valid ID.");
            }
        };

        LoadArticleInteractor interactor = new LoadArticleInteractor(dataAccessInterface, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]); // make sure the Interactor actually called the presenter
    }

    /**
     * Test that when the ID is invalid, LoadArticleInteractor will call
     * RandomArticlePresenter.prepareFailView() with a descriptive message.
     */
    @Test
    void failureTest() {
        LoadArticleInputData inputData = new LoadArticleInputData("not_a_real_id");

        // See explanation above
        final boolean[] presenterCalled = {false};

        LoadArticleOutputBoundary successPresenter = new LoadArticleOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadArticleOutputData outputData) {
                presenterCalled[0] = true;
                fail("Use case should not succeed on an invalid ID.");
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                assertEquals("not_a_real_id not found in the database.", error);
            }
        };

        LoadArticleInteractor interactor = new LoadArticleInteractor(dataAccessInterface, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]); // make sure the Interactor actually called the presenter
    }

    /**
     * Test that when the ID is null, LoadArticleInteractor will call
     * RandomArticlePresenter.prepareFailView() with a descriptive message.
     */
    @Test
    void nullArticleTest() {
        LoadArticleInputData inputData = new LoadArticleInputData(null);

        // See explanation above
        final boolean[] presenterCalled = {false};

        LoadArticleOutputBoundary successPresenter = new LoadArticleOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadArticleOutputData outputData) {
                presenterCalled[0] = true;
                fail("Use case should not succeed on a null ID.");
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                assertEquals("Must choose an article to load.", error);
            }
        };

        LoadArticleInteractor interactor = new LoadArticleInteractor(dataAccessInterface, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]); // make sure the Interactor actually called the presenter
    }
}



