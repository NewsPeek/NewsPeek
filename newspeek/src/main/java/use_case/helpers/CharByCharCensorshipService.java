package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

/**
 * CensorshipService implementation which traverses the text one character at a time, building words and censoring them.
 */
public class CharByCharCensorshipService implements CensorshipService {

    private static final String PUNCTUATION = " -.,()[]{};:\"'";

    @Override
    public Article censor(Article article, CensorshipRuleSet ruleset) {
        StringBuilder censoredText = new StringBuilder();
        StringBuilder word = new StringBuilder();
        int censoredWordsCount = 0;
        int replacedWordsCount = 0;
        for (char character : article.getText().toCharArray()) {
            if (PUNCTUATION.indexOf(character) != -1) {
                // character is punctuation
                Tuple3<String, Integer, Integer> censorshipResult = censorWord(word.toString(), ruleset);
                censoredText.append(censorshipResult.getFirst());
                censoredWordsCount += censorshipResult.getSecond();
                replacedWordsCount += censorshipResult.getThird();

                // still add the punctuation character
                censoredText.append(character);

                // reset word to empty to begin building a new one
                word = new StringBuilder();
            } else {
                word.append(character);
            }
        }

        // add the word-in-progress at the end so we don't lose any characters
        Tuple3<String, Integer, Integer> censorshipResult = censorWord(word.toString(), ruleset);
        censoredText.append(censorshipResult.getFirst());
        censoredWordsCount += censorshipResult.getSecond();
        replacedWordsCount += censorshipResult.getThird();

        Article result = article.copy();
        result.setCensoredWords(censoredWordsCount);
        result.setReplacedWords(replacedWordsCount);

        result.setText(censoredText.toString());
        return result;
    }

    /**
     * Censor the word if needed and return it, keeping track of censorship statistics.
     * @param word word to censor
     * @param ruleset the ruleset to apply
     * @return a Tuple3 containing the censored word, the number of words censored, and the number of words replaced.
     */
    private Tuple3<String, Integer, Integer> censorWord(String word, CensorshipRuleSet ruleset) {
        String searchWord;
        if (!ruleset.isCaseSensitive()) {
            searchWord = word.toLowerCase();
        } else {
            searchWord = word;
        }
        if (ruleset.getProhibitions().contains(searchWord)) {
            // Censor word
            return new Tuple3<>(prohibit(word), 1, 0);
        } else if (ruleset.getReplacements().containsKey(searchWord)) {
            // Replace word
            return new Tuple3<>(ruleset.getReplacements().get(searchWord), 0, 1);
        } else {
            // Do nothing
            return new Tuple3<>(word, 0, 0);
        }
    }

    private String prohibit(String word) {
        return new String(new char[word.length()]).replace("\0", "x");
    }
}
