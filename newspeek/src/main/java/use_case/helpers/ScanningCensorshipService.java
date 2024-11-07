package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

public class ScanningCensorshipService implements CensorshipService {
    @Override
    public Article censor(Article article, CensorshipRuleSet ruleset) {
        // loop through the words in the article.getText() and apply censorship rules
        Article result = article.copy();
        StringBuilder censoredText = new StringBuilder();

        // TODO: apply censorship rules. For now, we just duplicate the text verbatim.
        censoredText.append(article.getText());

        result.setText(censoredText.toString());
        return result;
    }
}