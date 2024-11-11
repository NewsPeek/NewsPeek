package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

public class ScanningCensorshipService implements CensorshipService {
    @Override
    public Article censor(Article article, CensorshipRuleSet ruleset) {
        // loop through the words in the article.getText() and apply censorship rules
        Article result = article.copy();
        StringBuilder censoredText = new StringBuilder();

        String[] articleText = result.getText().split(" ");

        for(String word : articleText){
            if(ruleset.getProhibitions().contains(word)) {
                censoredText.append(censorWord(word));
            }
            else censoredText.append(ruleset.getReplacements().getOrDefault(word, word));
            censoredText.append(" ");
        }


        result.setText(censoredText.toString());
        return result;
    }

    private String censorWord(String word) {
        return new String(new char[word.length()]).replace("\0", "x");
    }
}