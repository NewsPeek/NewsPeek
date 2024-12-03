package data_access.censorship_rule_set;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;
import use_case.choose_rule_set.ChooseRuleSetDataAccessInterface;

/**
 * Data access for censorship rule set stored in files.
 */
public class FileCensorshipRuleSetDataAccessObject implements ChooseRuleSetDataAccessInterface {

    @Override
    public CensorshipRuleSet getCensorshipRuleSet(File file) throws IOException {
        // Initialize default values
        Set<String> prohibitedWords = new HashSet<>();
        Map<String, String> replacedWords = new HashMap<>();

        // Use BufferedReader to read the file
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException ex) {
            System.err.println("An error occurred while reading the file: " + ex.getMessage());
            throw ex;
        }

        // Parse the JSON content
        JsonObject jsonObject = JsonParser.parseString(jsonContent.toString()).getAsJsonObject();

        // Extract prohibitedWords
        if (jsonObject.has("prohibitedWords")) {
            Type setType = new TypeToken<Set<String>>() { }.getType();
            prohibitedWords = new Gson().fromJson(jsonObject.get("prohibitedWords"), setType);
        }

        // Extract censoredWords
        if (jsonObject.has("censoredWords")) {
            Type listType = new TypeToken<List<Map<String, String>>>() { }.getType();
            List<Map<String, String>> censoredWordList = new Gson().fromJson(jsonObject.get("censoredWords"), listType);

            for (Map<String, String> wordPair : censoredWordList) {
                String original = wordPair.get("original");
                String censored = wordPair.get("censored");
                if (original != null && censored != null) {
                    replacedWords.put(original, "**"+censored+"**");
                }
            }
        }

        // Optional: Extract additional fields if they exist (e.g., "caseSensitive", "ruleSetName")
        // Default value
        Boolean caseSensitive = false;
        if (jsonObject.has("caseSensitive")) {
            caseSensitive = jsonObject.get("caseSensitive").getAsBoolean();
        }

        // Default value
        String ruleSetName = "Default Rules";
        if (jsonObject.has("ruleSetName")) {
            ruleSetName = jsonObject.get("ruleSetName").getAsString();
        }
        System.out.println(caseSensitive);
        System.out.println(prohibitedWords);
        System.out.println(replacedWords);
        // Return the constructed censorship rule set
        return new CommonCensorshipRuleSet(prohibitedWords, replacedWords, caseSensitive, ruleSetName);
    }
}
