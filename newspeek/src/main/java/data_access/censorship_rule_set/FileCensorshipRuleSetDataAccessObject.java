package data_access.censorship_rule_set;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;
import use_case.choose_rule_set.ChooseRuleSetDataAccessInterface;

/**
 * DAO to manage censorship rule sets stored in JSON files.
 */
public class FileCensorshipRuleSetDataAccessObject implements ChooseRuleSetDataAccessInterface {
    @Override
    public CensorshipRuleSet getCensorshipRuleSet(File file) throws IOException {
        Set<String> prohibitedWords = new HashSet<>();
        Map<String, String> replacedWords = new HashMap<>();
        Boolean caseSensitive = false;
        String ruleSetName = "Default Rules";
        prohibitedWords.add("");
        replacedWords.put("","*");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        System.out.println(caseSensitive);
        System.out.println(prohibitedWords);
        System.out.println(replacedWords);
        return new CommonCensorshipRuleSet(prohibitedWords, replacedWords, caseSensitive, ruleSetName);
    }
}
