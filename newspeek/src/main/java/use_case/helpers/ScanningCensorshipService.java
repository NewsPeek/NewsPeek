package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

public class ScanningCensorshipService implements CensorshipService {

    private static final String WORD_REGEX = "[ -.,()\\[\\]{};:\"']+";
    private static final String PUNCTUATION_REGEX = "[^ -.,()\\[\\]{};:\"']+";
    @Override
    public Article censor(Article article, CensorshipRuleSet ruleset) {
        // loop through the words in the article.getText() and apply censorship rules
        Article result = article.copy();
        StringBuilder censoredText = new StringBuilder();

        String[] articleWords = result.getText().split(WORD_REGEX);
        String[] articlePunctuation = result.getText().split(PUNCTUATION_REGEX);


        for(int i = 0; i < articleWords.length; i++){
            String word = articleWords[i];

            censoredText.append(articlePunctuation[i]);

            if(ruleset.getProhibitions().contains(word)) {
                censoredText.append(censorWord(word));
            }
            else censoredText.append(ruleset.getReplacements().getOrDefault(word, word));
        }

        if(articlePunctuation.length > articleWords.length) {
            censoredText.append(articlePunctuation[articlePunctuation.length - 1]);
        }
        result.setText(censoredText.toString());
        return result;
    }

    private String censorWord(String word) {
        return new String(new char[word.length()]).replace("\0", "x");
    }
}