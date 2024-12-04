package use_case.choose_rule_set;


import org.junit.jupiter.api.Test;
import entity.censorship_rule_set.CensorshipRuleSet;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChooseRuleSetOutputDataTest {
    @Test
    void ruleSetTest() {
        CensorshipRuleSet mockCensorshipRuleSet = new CensorshipRuleSet() {
            /**
             * @return The prohibition list
             */
            @Override
            public Set<String> getProhibitions() {
                return Set.of("Lorem", "Ipsum");
            }

            /**
             * @return The replacement list
             */
            @Override
            public Map<String, String> getReplacements() {
                return Map.of("Peace", "WAR", "America", "Violence");
            }

            /**
             * @return Whether this anonymous class is case sensitive
             */
            @Override
            public Boolean isCaseSensitive() {
                return false;
            }

            /**
             * @return The name of this mock censorship rule set.
             */
            @Override
            public String getRuleSetName() {
                return "Mock Censorship Rule Set";
            }
        };

        ChooseRuleSetOutputData outputData = new ChooseRuleSetOutputData(mockCensorshipRuleSet);
        assertEquals(mockCensorshipRuleSet, outputData.getCensorshipRuleSet());
    }
}
