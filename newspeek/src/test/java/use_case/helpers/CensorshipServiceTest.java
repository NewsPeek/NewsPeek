package use_case.helpers;

import entity.article.Article;
import entity.article.CommonArticle;
import entity.censorship_rule_set.CensorshipRuleSet;
import entity.censorship_rule_set.CommonCensorshipRuleSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CensorshipServiceTest {
    private Article mockArticle;

    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {

        });
    }

    private static Stream<Arguments> provideAllTestCases() {
        return Stream.of(
                Arguments.of(new ScanningCensorshipService(), true),
                Arguments.of(new ScanningCensorshipService(), false)
        );
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

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorNothingTest(CensorshipService censorshipService, Boolean caseSensitive) {
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

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorProhibitedTest(CensorshipService censorshipService, Boolean caseSensitive) {
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

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorProhibitedSpaceAroundTest(CensorshipService censorshipService, Boolean caseSensitive) {
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

    @ParameterizedTest
    @MethodSource("provideAllTestCases")
    void censorProhibitedPunctuationAroundTest(CensorshipService censorshipService, Boolean caseSensitive) {
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