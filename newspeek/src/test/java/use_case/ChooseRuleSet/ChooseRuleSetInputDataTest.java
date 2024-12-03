package use_case.ChooseRuleSet;

import org.junit.jupiter.api.Test;
import use_case.choose_rule_set.ChooseRuleSetInputData;
import use_case.load_article.LoadArticleInputData;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChooseRuleSetInputDataTest {
    @Test
    void inputTest() {
        ChooseRuleSetInputData inputData = new ChooseRuleSetInputData(new File("xyz"));
        assertEquals("xyz", inputData.getFile().getPath());
    }
}
