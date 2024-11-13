package use_case.helpers;

import entity.article.Article;
import entity.article.CommonArticle;
import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
class CensorshipServiceTest {
    private Article mockArticle;

    @Parameter
    public CensorshipService censorshipService;

    @Parameter(1)
    public boolean caseSensitive;

    @Parameters
    public static Object[] data() {
        return new Object[][] {
                {new ScanningCensorshipService(), false},
                {new ScanningCensorshipService(), true},
        };
    }

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
        final String ruleSetName = "Mock Ruleset";

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);
        Article censoredArticle = censorshipService.censor(this.mockArticle, mockCensorshipRuleSet);

        // Article text should be the same, since there's no censorship applied
        assertEquals(censoredArticle.getText(), censoredArticle.getText());

        // Nothing else should have changed
        assertNothingElseChanged(mockArticle, censoredArticle);
    }

    @Test
    void censorProhibitedTest() {
        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final String ruleSetName = "Mock Ruleset";

        prohibitedWords.add("lorem");
        prohibitedWords.add("ipsum");
        prohibitedWords.add("amet");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);
        Article censoredArticle = censorshipService.censor(this.mockArticle, mockCensorshipRuleSet);

        // Article text should be censored
        if (caseSensitive) {
            assertEquals("xxxxx xxxxx dolor sit xxxx. Lorem Ipsum DOLOR sIT aMeT.", censoredArticle.getText());
        } else {
            assertEquals("xxxxx xxxxx dolor sit xxxx. xxxxx xxxxx DOLOR sIT xxxx.", censoredArticle.getText());
        }

        assertNothingElseChanged(mockArticle, censoredArticle);
    }

    @Test
    void censorProhibitedSpaceAroundTest() {
        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final String ruleSetName = "Mock Ruleset";

        prohibitedWords.add("lorem");
        prohibitedWords.add("ipsum");
        prohibitedWords.add("amet");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);

        final String title = "Sample Article";
        final String text = " lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT. "; // note spaces around text
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        final Article mockArticle = new CommonArticle(title, text, source, author, agency, postedAt);

        Article censoredArticle = censorshipService.censor(this.mockArticle, mockCensorshipRuleSet);

        // Article text should be censored
        if (caseSensitive) {
            assertEquals(" xxxxx xxxxx dolor sit xxxx. Lorem Ipsum DOLOR sIT aMeT. ", censoredArticle.getText());
        } else {
            assertEquals(" xxxxx xxxxx dolor sit xxxx. xxxxx xxxxx DOLOR sIT xxxx. ", censoredArticle.getText());
        }

        assertNothingElseChanged(mockArticle, censoredArticle);
    }

    void censorProhibitedPunctuationAroundTest() {
        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final String ruleSetName = "Mock Ruleset";

        prohibitedWords.add("lorem");
        prohibitedWords.add("ipsum");
        prohibitedWords.add("amet");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);

        final String title = "Sample Article";
        final String text = ",lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.!"; // note spaces around text
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        final Article mockArticle = new CommonArticle(title, text, source, author, agency, postedAt);

        Article censoredArticle = censorshipService.censor(this.mockArticle, mockCensorshipRuleSet);

        // Article text should be censored
        if (caseSensitive) {
            assertEquals(",xxxxx xxxxx dolor sit xxxx. Lorem Ipsum DOLOR sIT aMeT.!", censoredArticle.getText());
        } else {
            assertEquals(",xxxxx xxxxx dolor sit xxxx. xxxxx xxxxx DOLOR sIT xxxx.!", censoredArticle.getText());
        }

        assertNothingElseChanged(mockArticle, censoredArticle);
    }

    private void assertNothingElseChanged(Article mockArticle, Article censoredArticle) {
        assertEquals(mockArticle.getTitle(), censoredArticle.getTitle());
        assertEquals(mockArticle.getSource(), censoredArticle.getSource());
        assertEquals(mockArticle.getAuthor(), censoredArticle.getAuthor());
        assertEquals(mockArticle.getAgency(), censoredArticle.getAgency());
        assertEquals(mockArticle.getPostedAt(), censoredArticle.getPostedAt());
    }
}