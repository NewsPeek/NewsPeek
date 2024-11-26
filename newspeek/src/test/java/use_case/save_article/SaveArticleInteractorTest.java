package use_case.save_article;

import data_access.article.MemoryArticleDataAccessObject;
import entity.article.Article;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.random_article.*;

import static org.junit.jupiter.api.Assertions.*;

class SaveArticleInteractorTest {
    private SaveArticleDataAccessInterface dataAccessInterface;

    @BeforeEach
    void createDAO() {
        this.dataAccessInterface = new MemoryArticleDataAccessObject();
    }

    /**
     * Test that when the DAO succeeds, SaveArticleInteractor will call
     * RandomArticlePresenter.prepareSuccessView().
     */
    @Test
    void successTest() {
        SaveArticleInputData inputData = new SaveArticleInputData(Article.mockArticle());

        // Track whether presenter was called. A broken Interactor could just not call the presenter,
        // and then it would pass the test as no assertions fail. This prevents that.
        // Unfortunately, IntelliJ forces us to use a 1-element array instead of a single boolean.
        // This is evil but there's probably a reason; something about being final.
        final boolean[] presenterCalled = {false};

        SaveArticleOutputBoundary successPresenter = new SaveArticleOutputBoundary() {
            @Override
            public void prepareSuccessView(SaveArticleOutputData outputData) {
                presenterCalled[0] = true;
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                fail("Use case should not fail on a valid article.");
            }
        };

        SaveArticleInteractor interactor = new SaveArticleInteractor(dataAccessInterface, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]); // make sure the Interactor actually called the presenter
    }

    /**
     * Test that when the DAO throws an exception, SaveArticleInteractor will call
     * RandomArticlePresenter.prepareFailView() with the exception's message.
     */
    @Test
    void failureTest() {
        Article article = Article.mockArticle();
        article.setText("FAIL");
        SaveArticleInputData inputData = new SaveArticleInputData(article);

        // See explanation above
        final boolean[] presenterCalled = {false};

        SaveArticleOutputBoundary successPresenter = new SaveArticleOutputBoundary() {
            @Override
            public void prepareSuccessView(SaveArticleOutputData outputData) {
                presenterCalled[0] = true;
                fail("Use case should not succeed on a FAIL article.");
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                assertEquals("saveArticle intentionally failed.", error);
            }
        };

        SaveArticleInteractor interactor = new SaveArticleInteractor(dataAccessInterface, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]); // make sure the Interactor actually called the presenter
    }

    /**
     * Test that when the article is null, SaveArticleInteractor will call
     * RandomArticlePresenter.prepareFailView() with a descriptive message.
     */
    @Test
    void nullArticleTest() {
        SaveArticleInputData inputData = new SaveArticleInputData(null);

        // See explanation above
        final boolean[] presenterCalled = {false};

        SaveArticleOutputBoundary successPresenter = new SaveArticleOutputBoundary() {
            @Override
            public void prepareSuccessView(SaveArticleOutputData outputData) {
                presenterCalled[0] = true;
                fail("Use case should not succeed on a null article.");
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                assertEquals("Please load an article before saving.", error);
            }
        };

        SaveArticleInteractor interactor = new SaveArticleInteractor(dataAccessInterface, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]); // make sure the Interactor actually called the presenter
    }
}



