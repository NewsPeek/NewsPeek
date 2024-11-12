package use_case.helpers;

import entity.article.Article;
import entity.article.CommonArticle;
import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ScanningCensorshipServiceTest {
    private Article mockArticle;

    @BeforeEach
    void initializeMocks() {
        final String title = "Sample Article";
        final String text = "lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.";
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        this.mockArticle = new CommonArticle(title, text, source, author, agency, postedAt);
    }

    @Test
    void censorNothingTest() {
        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final Boolean caseSensitive = false;
        final String ruleSetName = "Mock Ruleset";

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);
        CensorshipService censorshipService = new ScanningCensorshipService();
        Article censoredArticle = censorshipService.censor(this.mockArticle, mockCensorshipRuleSet);

        // Article text should be the same, since there's no censorship applied
        assertEquals(censoredArticle.getText(), mockArticle.getText());

        // Nothing else should have changed
        assertEquals(censoredArticle.getTitle(), mockArticle.getTitle());
        assertEquals(censoredArticle.getSource(), mockArticle.getSource());
        assertEquals(censoredArticle.getAuthor(), mockArticle.getAuthor());
        assertEquals(censoredArticle.getAgency(), mockArticle.getAgency());
        assertEquals(censoredArticle.getPostedAt(), mockArticle.getPostedAt());
    }

    /*
    @Test
    void censorProhibitedCaseInsensitiveTest() {
        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final Boolean caseSensitive = false;
        final String ruleSetName = "Mock Ruleset";

        prohibitedWords.add("ipsum");
        prohibitedWords.add("lorem");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);
        CensorshipService censorshipService = new ScanningCensorshipService();
        Article censoredArticle = censorshipService.censor(this.mockArticle, mockCensorshipRuleSet);

        // Article text should be censored
        assertEquals(mockArticle.getText(), "xxxxx xxxxx dolor sit amet. xxxxx xxxxx DOLOR sIT aMeT.");

        // Nothing else should have changed
        assertEquals(censoredArticle.getTitle(), mockArticle.getTitle());
        assertEquals(censoredArticle.getSource(), mockArticle.getSource());
        assertEquals(censoredArticle.getAuthor(), mockArticle.getAuthor());
        assertEquals(censoredArticle.getAgency(), mockArticle.getAgency());
        assertEquals(censoredArticle.getPostedAt(), mockArticle.getPostedAt());
    }
    */

    @Test
    void censorProhibitedCaseSensitiveTest() {
        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final Boolean caseSensitive = true;
        final String ruleSetName = "Mock Ruleset";

        prohibitedWords.add("ipsum");
        prohibitedWords.add("lorem");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);
        CensorshipService censorshipService = new ScanningCensorshipService();
        Article censoredArticle = censorshipService.censor(this.mockArticle, mockCensorshipRuleSet);

        // Article text should be censored
        assertEquals("xxxxx xxxxx dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.", censoredArticle.getText());

        // Nothing else should have changed
        assertEquals(mockArticle.getTitle(), censoredArticle.getTitle());
        assertEquals(mockArticle.getSource(), censoredArticle.getSource());
        assertEquals(mockArticle.getAuthor(), censoredArticle.getAuthor());
        assertEquals(mockArticle.getAgency(), censoredArticle.getAgency());
        assertEquals(mockArticle.getPostedAt(), censoredArticle.getPostedAt());
    }
}