package data_access.censorship_rule_set;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import entity.censorship_rule_set.CensorshipRuleSet;
import use_case.choose_rule_set.ChooseRuleSetDataAccessInterface;
import entity.censorship_rule_set.CommonCensorshipRuleSet;

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
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/*public class FileCensorshipRuleSetDataAccessObject implements ChooseRuleSetDataAccessInterface {

    @Override
    public CensorshipRuleSet getCensorshipRuleSet(File file) throws IOException {
        Set<String> prohibitedWords = new HashSet<>();
        Map<String, String> replacedWords = new HashMap<>();
        Boolean caseSensitive = false;
        String ruleSetName = "Default Rules";
        // prohibitedWords.add("");
        // replacedWords.put("","*");

        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
            String jsonString = jsonContent.toString();

            // Extract prohibitedWords
            String prohibitedWordsKey = "\"prohibitedWords\":";
            int prohibitedWordsStart = jsonString.indexOf(prohibitedWordsKey) + prohibitedWordsKey.length();
            int prohibitedWordsEnd = jsonString.indexOf("]", prohibitedWordsStart) + 1;
            String prohibitedWordsArray = jsonString.substring(prohibitedWordsStart, prohibitedWordsEnd);
            prohibitedWordsArray = prohibitedWordsArray.replace("[", "").replace("]", "").replace("\"", "").trim();

            if (!prohibitedWordsArray.isEmpty()) {
                for (String word : prohibitedWordsArray.split(",")) {
                    prohibitedWords.add(word);
                }
            }

            // Extract censoredWords
            String censoredWordsKey = "\"censoredWords\":";
            int censoredWordsStart = jsonString.indexOf(censoredWordsKey) + censoredWordsKey.length();
            int censoredWordsEnd = jsonString.indexOf("]", censoredWordsStart) + 1;
            String censoredWordsArray = jsonString.substring(censoredWordsStart, censoredWordsEnd);

            // Split individual censoredWord objects
            String[] censoredWordObjects = censoredWordsArray.substring(1, censoredWordsArray.length() - 1).split("},\\{");
            for (String wordObject : censoredWordObjects) {
                String originalKey = "\"original\":\"";
                String censoredKey = "\"censored\":\"";

                int originalStart = wordObject.indexOf(originalKey) + originalKey.length();
                int originalEnd = wordObject.indexOf("\"", originalStart);
                String original = wordObject.substring(originalStart, originalEnd);

                int censoredStart = wordObject.indexOf(censoredKey) + censoredKey.length();
                int censoredEnd = wordObject.indexOf("\"", censoredStart);
                String censored = wordObject.substring(censoredStart, censoredEnd);

                replacedWords.put(original, censored);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(caseSensitive);
        System.out.println(prohibitedWords);
        System.out.println(replacedWords);
        return new CommonCensorshipRuleSet(prohibitedWords, replacedWords, caseSensitive, ruleSetName);
    }
}*/

public class FileCensorshipRuleSetDataAccessObject implements ChooseRuleSetDataAccessInterface {

    @Override
    public CensorshipRuleSet getCensorshipRuleSet(File file) throws IOException {
        // Initialize default values
        Set<String> prohibitedWords = new HashSet<>();
        Map<String, String> replacedWords = new HashMap<>();
        Boolean caseSensitive = false; // Default value
        String ruleSetName = "Default Rules"; // Default value

        // Use BufferedReader to read the file
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
            throw e;
        }

        // Parse the JSON content
        JsonObject jsonObject = JsonParser.parseString(jsonContent.toString()).getAsJsonObject();

        // Extract prohibitedWords
        if (jsonObject.has("prohibitedWords")) {
            Type setType = new TypeToken<Set<String>>() {}.getType();
            prohibitedWords = new Gson().fromJson(jsonObject.get("prohibitedWords"), setType);
        }

        // Extract censoredWords
        if (jsonObject.has("censoredWords")) {
            Type listType = new TypeToken<List<Map<String, String>>>() {}.getType();
            List<Map<String, String>> censoredWordList = new Gson().fromJson(jsonObject.get("censoredWords"), listType);

            for (Map<String, String> wordPair : censoredWordList) {
                String original = wordPair.get("original");
                String censored = wordPair.get("censored");
                if (original != null && censored != null) {
                    replacedWords.put(original, censored);
                }
            }
        }

        // Optional: Extract additional fields if they exist (e.g., "caseSensitive", "ruleSetName")
        if (jsonObject.has("caseSensitive")) {
            caseSensitive = jsonObject.get("caseSensitive").getAsBoolean();
        }

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



