package use_case.helpers;

import entity.article.Article;
import entity.censorship_rule_set.CensorshipRuleSet;

public interface CensorshipService {
    Article censor(Article article, CensorshipRuleSet ruleset);
}
