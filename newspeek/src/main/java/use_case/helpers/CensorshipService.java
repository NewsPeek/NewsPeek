package use_case.helpers;

import entity.Article;
import entity.CensorshipRuleSet;

public interface CensorshipService {
    Article censor(Article article, CensorshipRuleSet ruleset);
}
