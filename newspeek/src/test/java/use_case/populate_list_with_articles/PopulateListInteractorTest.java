package use_case.populate_list_with_articles;

import data_access.article.MemoryArticleDataAccessObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PopulateListInteractorTest {
    @Test
    void successTest() {
        MemoryArticleDataAccessObject memoryArticleDAO = new MemoryArticleDataAccessObject();

        // Track whether presenter was called. A broken Interactor could just not call the presenter,
        // and then it would pass the test as no assertions fail. This prevents that.
        // Unfortunately, IntelliJ forces us to use a 1-element array instead of a single boolean.
        // This is evil but there's probably a reason; something about being final.
        final boolean[] presenterCalled = {false};

        PopulateListOutputBoundary successPresenter = new PopulateListOutputBoundary() {
            @Override
            public void prepareSuccessView(PopulateListOutputData outputData) {
                presenterCalled[0] = true;
                Map<String, String> articles = new HashMap<>();
                assertEquals(articles, outputData.getArticleList());
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                fail("Use case should not fail.");
            }
        };

        PopulateListInteractor interactor = new PopulateListInteractor(memoryArticleDAO, successPresenter);
        interactor.execute();
        assertTrue(presenterCalled[0]);
    }
}
