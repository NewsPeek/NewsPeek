package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CensorshipServiceTest {
    private static Stream<Arguments> provideAllTestCases() {
        return Stream.of(
                Arguments.of(new ScanningCensorshipService(), true),
                Arguments.of(new ScanningCensorshipService(), false),
                Arguments.of(new CharByCharCensorshipService(), true),
                Arguments.of(new CharByCharCensorshipService(), false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorNothingTest(CensorshipService censorshipService, Boolean caseSensitive) {
        final String title = "Sample Article";
        final String text = "lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.";
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        final Article mockArticle = new Article(title, text, source, author, agency, postedAt);

        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final String ruleSetName = "Mock Ruleset";

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);
        Article censoredArticle = censorshipService.censor(mockArticle, mockCensorshipRuleSet);

        // Article text should be the same, since there's no censorship applied
        assertEquals(mockArticle.getText(), censoredArticle.getText());

        // No censorship was applied
        assertEquals(0, censoredArticle.getCensoredWords());
        assertEquals(0, censoredArticle.getReplacedWords());

        // Nothing else should have changed
        assertNothingElseChanged(mockArticle, censoredArticle);
    }

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorProhibitedTest(CensorshipService censorshipService, Boolean caseSensitive) {
        final String title = "Sample Article";
        final String text = "lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.";
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        final Article mockArticle = new Article(title, text, source, author, agency, postedAt);

        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final String ruleSetName = "Mock Ruleset";

        prohibitedWords.add("lorem");
        prohibitedWords.add("ipsum");
        prohibitedWords.add("amet");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);
        Article censoredArticle = censorshipService.censor(mockArticle, mockCensorshipRuleSet);

        // Article text should be censored
        if (caseSensitive) {
            assertEquals("xxxxx xxxxx dolor sit xxxx. Lorem Ipsum DOLOR sIT aMeT.", censoredArticle.getText());
            assertEquals(3, censoredArticle.getCensoredWords());
            assertEquals(0, censoredArticle.getReplacedWords());

        } else {
            assertEquals("xxxxx xxxxx dolor sit xxxx. xxxxx xxxxx DOLOR sIT xxxx.", censoredArticle.getText());
            assertEquals(6, censoredArticle.getCensoredWords());
            assertEquals(0, censoredArticle.getReplacedWords());
        }

        assertNothingElseChanged(mockArticle, censoredArticle);
    }

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorProhibitedSpaceAroundTest(CensorshipService censorshipService, Boolean caseSensitive) {
        final String title = "Sample Article";
        final String text = " lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT. "; // note spaces around text
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        final Article mockArticle = new Article(title, text, source, author, agency, postedAt);

        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final String ruleSetName = "Mock Ruleset";

        prohibitedWords.add("lorem");
        prohibitedWords.add("ipsum");
        prohibitedWords.add("amet");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);

        Article censoredArticle = censorshipService.censor(mockArticle, mockCensorshipRuleSet);

        // Article text should be censored
        if (caseSensitive) {
            assertEquals(" xxxxx xxxxx dolor sit xxxx. Lorem Ipsum DOLOR sIT aMeT. ", censoredArticle.getText());
            assertEquals(3, censoredArticle.getCensoredWords());
            assertEquals(0, censoredArticle.getReplacedWords());
        } else {
            assertEquals(" xxxxx xxxxx dolor sit xxxx. xxxxx xxxxx DOLOR sIT xxxx. ", censoredArticle.getText());
            assertEquals(6, censoredArticle.getCensoredWords());
            assertEquals(0, censoredArticle.getReplacedWords());
        }

        assertNothingElseChanged(mockArticle, censoredArticle);
    }

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorReplaceTest(CensorshipService censorshipService, Boolean caseSensitive) {
        final String title = "Sample Article";
        final String text = "lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT. ";
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        final Article mockArticle = new Article(title, text, source, author, agency, postedAt);

        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final String ruleSetName = "Mock Ruleset";

        replacedWords.put("lorem", "sed");
        replacedWords.put("dolor", "do");
        replacedWords.put("amet", "eiusmod");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);

        Article censoredArticle = censorshipService.censor(mockArticle, mockCensorshipRuleSet);

        // Article text should be replaced
        if (caseSensitive) {
            assertEquals("sed ipsum do sit eiusmod. Lorem Ipsum DOLOR sIT aMeT. ", censoredArticle.getText());
            assertEquals(0, censoredArticle.getCensoredWords());
            assertEquals(3, censoredArticle.getReplacedWords());
        } else {
            assertEquals("sed ipsum do sit eiusmod. sed Ipsum do sIT eiusmod. ", censoredArticle.getText());
            assertEquals(0, censoredArticle.getCensoredWords());
            assertEquals(6, censoredArticle.getReplacedWords());
        }

        assertNothingElseChanged(mockArticle, censoredArticle);
    }

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorProhibitedPunctuationAroundTest(CensorshipService censorshipService, Boolean caseSensitive) {
        final String title = "Sample Article";
        final String text = ",lorem ipsum dolor sit amet. Lorem Ipsum DOLOR sIT aMeT.!"; // note spaces around text
        final String source = "https://example.com";
        final String author = "John Cena";
        final String agency = "XYZ Corporation";
        final LocalDateTime postedAt = LocalDateTime.now();
        final Article mockArticle = new Article(title, text, source, author, agency, postedAt);

        final Set<String> prohibitedWords = new HashSet<>();
        final Map<String, String> replacedWords = new HashMap<>();
        final String ruleSetName = "Mock Ruleset";

        prohibitedWords.add("lorem");
        prohibitedWords.add("ipsum");
        prohibitedWords.add("amet");

        CensorshipRuleSet mockCensorshipRuleSet = new CommonCensorshipRuleSet(
                prohibitedWords, replacedWords, caseSensitive, ruleSetName);

        Article censoredArticle = censorshipService.censor(mockArticle, mockCensorshipRuleSet);

        // Article text should be censored
        if (caseSensitive) {
            assertEquals(",xxxxx xxxxx dolor sit xxxx. Lorem Ipsum DOLOR sIT aMeT.!", censoredArticle.getText());
            assertEquals(3, censoredArticle.getCensoredWords());
            assertEquals(0, censoredArticle.getReplacedWords());
        } else {
            assertEquals(",xxxxx xxxxx dolor sit xxxx. xxxxx xxxxx DOLOR sIT xxxx.!", censoredArticle.getText());
            assertEquals(6, censoredArticle.getCensoredWords());
            assertEquals(0, censoredArticle.getReplacedWords());
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