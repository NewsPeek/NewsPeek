package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

public class CharByCharCensorshipService implements CensorshipService {

    private static final String PUNCTUATION = " -.,()[]{};:\"'";

    @Override
    public Article censor(Article article, CensorshipRuleSet ruleset) {
        Article result = article.copy();
        StringBuilder censoredText = new StringBuilder();
        StringBuilder word = new StringBuilder();

        for (char character : article.getText().toCharArray()){
            if (PUNCTUATION.indexOf(character) != -1) { //is punctuation
                censoredText.append(censorWord(word.toString(), ruleset));
                censoredText.append(character);
                word = new StringBuilder(); // reset word to empty
            } else {
                word.append(character);
            }
        }

        // add the word-in-progress at the end so we don't lose any characters
        censoredText.append(censorWord(word.toString(), ruleset));

        result.setText(censoredText.toString());
        return result;
    }

    private String censorWord (String word, CensorshipRuleSet ruleset) {
        if (ruleset.getProhibitions().contains(word)) {
            return prohibit(word);
        } else {
            return ruleset.getReplacements().getOrDefault(word, word);
        }
    }

    private String prohibit (String word) {
        return new String(new char[word.length()]).replace("\0", "x");
    }
}