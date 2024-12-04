package use_case.choose_rule_set;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChooseRuleSetInputDataTest {
    @Test
    void fileTest() {
        File mockFile = new File("MockFile.txt");
        ChooseRuleSetInputData inputData = new ChooseRuleSetInputData(mockFile);
        assertEquals(mockFile, inputData.getFile());
    }
}
