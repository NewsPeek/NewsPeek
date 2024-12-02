package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

import java.util.Objects;

/**
 * CensorshipService implementation which traverses the text one character at a time, building words and censoring them.
 */
public class CharByCharCensorshipService implements CensorshipService {

    private static final String PUNCTUATION = " -.,()[]{};:\"'";

    @Override
    public Article censor(Article article, CensorshipRuleSet ruleset) {
        Article result = article.copy();
        StringBuilder censoredText = new StringBuilder();
        StringBuilder word = new StringBuilder();
        int censoredWordsCount = 0;
        int replacedWordsCount = 0;
        for (char character : article.getText().toCharArray()) {
            if (PUNCTUATION.indexOf(character) != -1) {
                // character is punctuation
                censoredText.append(censorWord(word.toString(), ruleset));
                censoredText.append(character);
                if (!Objects.equals(censorWord(word.toString(), ruleset), word.toString())) {
                    if (Objects.equals(censorWord(word.toString(), ruleset),
                            new String(new char[word.toString().length()]).replace("\0", "x"))) {
                        censoredWordsCount++;
                    } else {
                        replacedWordsCount++;
                    }
                }
                // reset word to empty to begin building a new one
                word = new StringBuilder();
            } else {
                word.append(character);
            }
        }
        article.setCensoredWords(censoredWordsCount);
        article.setReplacedWords(replacedWordsCount);

        // add the word-in-progress at the end so we don't lose any characters
        censoredText.append(censorWord(word.toString(), ruleset));

        result.setText(censoredText.toString());
        return result;
    }

    private String censorWord(String word, CensorshipRuleSet ruleset) {
        String searchWord;
        if (!ruleset.isCaseSensitive()) {
            searchWord = word.toLowerCase();
        } else {
            searchWord = word;
        }
        if (ruleset.getProhibitions().contains(searchWord)) {
            return prohibit(word);
        } else {
            return ruleset.getReplacements().getOrDefault(searchWord, word);
        }
    }

    private String prohibit(String word) {
        return new String(new char[word.length()]).replace("\0", "x");
    }
}
