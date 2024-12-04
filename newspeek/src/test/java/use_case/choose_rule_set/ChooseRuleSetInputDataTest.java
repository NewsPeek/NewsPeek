package use_case.choose_rule_set;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChooseRuleSetInputDataTest {
    @Test
    void inputTest() {
        ChooseRuleSetInputData inputData = new ChooseRuleSetInputData(new File("xyz"));
        assertEquals("xyz", inputData.getFile().getPath());
    }
}
