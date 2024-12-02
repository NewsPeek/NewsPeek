package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

/**
 * CensorshipService implementation which splits the text into punctuation and words,
 * censors the words, and reassembles the text.
 */
public class ScanningCensorshipService implements CensorshipService {
    // finds words by splitting on punctuation and spaces
    private static final String WORD_REGEX = "[ -.,()\\[\\]{};:\"']+";
    // finds punctuation by splitting on everything *except* punctuation and spaces
    private static final String PUNCTUATION_REGEX = "[^ -.,()\\[\\]{};:\"']+";

    @Override
    public Article censor(Article article, CensorshipRuleSet ruleset) {
        Article result = article.copy();

        int censoredWordsCount = 0;
        int replacedWordsCount = 0;
        // loop through the words in the article.getText() and apply censorship rules
        StringBuilder censoredText = new StringBuilder();

        String[] articleWords = result.getText().split(WORD_REGEX);
        String[] articlePunctuation = result.getText().split(PUNCTUATION_REGEX);

        // Input validation
        if (invalidInput(articleWords, articlePunctuation, result)) {
            return result;
        }

        int wordAdjustment = 0;

        if (articleWords[0].isEmpty()) {
            wordAdjustment = 1;
        }
        for (int i = 0; i < articleWords.length - wordAdjustment; i++) {
            String word = articleWords[i + wordAdjustment];
            censoredText.append(articlePunctuation[i]);
            Tuple3<String, Integer, Integer> censorshipResult = getCensored(word, ruleset);
            censoredText.append(censorshipResult.getFirst());
            censoredWordsCount += censorshipResult.getSecond();
            replacedWordsCount += censorshipResult.getThird();
        }

        result.setCensoredWords(censoredWordsCount);
        result.setReplacedWords(replacedWordsCount);
        endAdjustment(articleWords, articlePunctuation, censoredText, wordAdjustment);

        result.setText(censoredText.toString());
        return result;
    }

    /**
     * Censor the word if needed and return it, keeping track of censorship statistics.
     * @param word word to censor
     * @param ruleset the ruleset to apply
     * @return a Tuple3 containing the censored word, the number of words censored, and the number of words replaced.
     */
    private Tuple3<String, Integer, Integer> getCensored(String word, CensorshipRuleSet ruleset) {
        String searchWord;
        if (!ruleset.isCaseSensitive()) {
            searchWord = word.toLowerCase();
        } else {
            searchWord = word;
        }
        if (ruleset.getProhibitions().contains(searchWord)) {
            // Censor word
            return new Tuple3<>(censorWord(word), 1, 0);
        } else if (ruleset.getReplacements().containsKey(searchWord)) {
            // Replace word
            return new Tuple3<>(ruleset.getReplacements().get(searchWord), 0, 1);
        } else {
            // Do nothing
            return new Tuple3<>(word, 0, 0);
        }
    }

    /**
     * Detects if words and punctuation are in edge cases.
     * If it is in one of these edge cases, it will edit result to match
     * @param words array of words to test the length of
     * @param punctuation array of punctuation strings to test the length of
     * @param result article to set the text of
     * @return whether the input was processed without error
     */
    private boolean invalidInput(String[] words, String[] punctuation, Article result) {
        StringBuilder resultText = new StringBuilder();
        if (words.length == 0) {
            resultText.append(punctuation[0]);
            result.setText(resultText.toString());
            return true;
        }
        if (punctuation.length == 0) {
            resultText.append(words[0]);
            result.setText(resultText.toString());
            return true;
        }
        return words[0].isEmpty() && punctuation[0].isEmpty();
    }

    private void endAdjustment(String[] articleWords, String[] articlePunctuation,
                               StringBuilder censoredText, int wordAdjustment) {
        if (wordAdjustment == 1 | articleWords.length < articlePunctuation.length) {
            censoredText.append(articlePunctuation[articlePunctuation.length - 1]);
        }
    }

    private String censorWord(String word) {
        return new String(new char[word.length()]).replace("\0", "x");
    }
}
