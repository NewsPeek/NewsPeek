package use_case.random_article;

import data_access.article.MemoryArticleDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomArticleInteractorTest {
    private RandomArticleAPIDataAccessInterface randomArticleDAO;

    @BeforeEach
    void createDAO() {
        this.randomArticleDAO = new MemoryArticleDataAccessObject();
    }

    /**
     * Test that when the DAO succeeds, RandomArticleInteractor will call
     * RandomArticlePresenter.prepareSuccessView() with the correct article.
     */
    @Test
    void successTest() {
        RandomArticleInputData inputData = new RandomArticleInputData("us");

        // Track whether presenter was called. A broken Interactor could just not call the presenter,
        // and then it would pass the test as no assertions fail. This prevents that.
        // Unfortunately, IntelliJ forces us to use a 1-element array instead of a single boolean.
        // This is evil but there's probably a reason; something about being final.
        final boolean[] presenterCalled = {false};

        RandomArticleOutputBoundary successPresenter = new RandomArticleOutputBoundary() {
            @Override
            public void prepareSuccessView(RandomArticleOutputData outputData) {
                presenterCalled[0] = true;
                // From MemoryArticleDataAccessObject.makeMockArticle()
                assertEquals("Sample Article", outputData.getArticle().getTitle());
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                fail("Use case should not fail with country 'us'.");
            }
        };

        RandomArticleInteractor interactor = new RandomArticleInteractor(randomArticleDAO, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]); // make sure the Interactor actually called the presenter
    }

    /**
     * Test that when the DAO throws an exception, RandomArticleInteractor will call
     * RandomArticlePresenter.prepareFailView() with the exception's message.
     */
    @Test
    void failureTest() {
        RandomArticleInputData inputData = new RandomArticleInputData("FAIL");

        // See comment in successTest() for explanation
        final boolean[] presenterCalled = {false};

        RandomArticleOutputBoundary successPresenter = new RandomArticleOutputBoundary() {
            @Override
            public void prepareSuccessView(RandomArticleOutputData outputData) {
                presenterCalled[0] = true;
                fail("Use case should not succeed with country 'FAIL'.");
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                assertEquals("getRandomArticle intentionally failed.", error);
            }
        };

        RandomArticleInteractor interactor = new RandomArticleInteractor(randomArticleDAO, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]); // make sure the Interactor actually called the presenter
    }
}
