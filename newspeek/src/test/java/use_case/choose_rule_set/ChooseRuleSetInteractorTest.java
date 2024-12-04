package use_case.choose_rule_set;

import data_access.censorship_rule_set.MemoryCensorshipRuleSetDataAccessObject;

import static org.junit.jupiter.api.Assertions.*;

import entity.censorship_rule_set.CensorshipRuleSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;


public class ChooseRuleSetInteractorTest {

    private MemoryCensorshipRuleSetDataAccessObject dataAccessInterface;
    private CensorshipRuleSet mockRuleSet;

    @BeforeEach
    void createDAO() {
        this.dataAccessInterface = new MemoryCensorshipRuleSetDataAccessObject();
        this.mockRuleSet = MemoryCensorshipRuleSetDataAccessObject.mockCensorshipRuleSet();
    }

    @Test
    void successTest() {
        File mockFile = new File("src/test/java/use_case/choose_rule_set/MockFile.txt");
        ChooseRuleSetInputData inputData = new ChooseRuleSetInputData(mockFile);

        // Track whether presenter was called. A broken Interactor could just not call the presenter,
        // and then it would pass the test as no assertions fail. This prevents that.
        // Unfortunately, IntelliJ forces us to use a 1-element array instead of a single boolean.
        // This is evil but there's probably a reason; something about being final.
        final boolean[] presenterCalled = {false};

        ChooseRuleSetOutputBoundary successPresenter = new ChooseRuleSetOutputBoundary() {
            @Override
            public void prepareSuccessView(ChooseRuleSetOutputData outputData) {

                // Check if the outputData is the same as what we got from
                // our mock data access interfaces
                presenterCalled[0] = true;
                assertTrue(compare(outputData.getCensorshipRuleSet(), mockRuleSet));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case should not fail on this mock file.");
            }
        };

        ChooseRuleSetInteractor interactor = new ChooseRuleSetInteractor(dataAccessInterface, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]);
    }

    /**
     * Tests whether the
     *
     */
    @Test
    void failTest() {
        File mockFile = new File("src/test/java/use_case/choose_rule_set/FailingMockFile.txt");
        ChooseRuleSetInputData inputData = new ChooseRuleSetInputData(mockFile);

        // Track whether presenter was called. A broken Interactor could just not call the presenter,
        // and then it would pass the test as no assertions fail. This prevents that.
        // Unfortunately, IntelliJ forces us to use a 1-element array instead of a single boolean.
        // This is evil but there's probably a reason; something about being final.
        final boolean[] presenterCalled = {false};

        ChooseRuleSetOutputBoundary successPresenter = new ChooseRuleSetOutputBoundary() {
            @Override
            public void prepareSuccessView(ChooseRuleSetOutputData outputData) {
                fail("Use case should not succeed on this mock file.");
            }

            @Override
            public void prepareFailView(String error) {
                presenterCalled[0] = true;
                assertEquals("getCensorshipRuleSet intentionally failed.", error);
            }
        };

        ChooseRuleSetInteractor interactor = new ChooseRuleSetInteractor(dataAccessInterface, successPresenter);
        interactor.execute(inputData);
        assertTrue(presenterCalled[0]);
    }


    /**
     * assertEquals does not work with censorshipRuleSet because the replacements and prohibitions
     * are stored internally as hashmaps, meaning they will not be equal. So, I wrote this version
     * which compares them.
     * @param firstSet The first set we are comparing
     * @param secondSet The second set we are comparing
     * @return Whether the sets are the same.
     */
    private boolean compare(CensorshipRuleSet firstSet, CensorshipRuleSet secondSet) {
        if(!firstSet.isCaseSensitive().equals(secondSet.isCaseSensitive())) {
            return false;
        }
        if(!firstSet.getReplacements().equals(secondSet.getReplacements())) {
            return false;
        }
        if(!firstSet.getRuleSetName().equals(secondSet.getRuleSetName())) {
            return false;
        }
        return firstSet.getProhibitions().equals(secondSet.getProhibitions());
    }
}
