package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

/**
 * Service which applies censorship rules to an article.
 */
public interface CensorshipService {
    /**
     * Censors the given article's text with the given ruleset.
     * @param article the article to censor the text of
     * @param ruleset the ruleset to apply
     * @return a new Article with the text censored and all other attributes unchanged
     */
    Article censor(Article article, CensorshipRuleSet ruleset);
}
