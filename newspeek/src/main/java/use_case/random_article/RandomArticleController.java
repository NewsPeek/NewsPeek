package use_case.random_article;
import entity.censorship_rule_set.CensorshipRuleSet;
import interface_adapter.ReaderViewModel;
import interface_adapter.ReaderState;
import entity.article.Article;
public class RandomArticleController {
    private RandomArticleInputBoundary randomArticleInputBoundary;

    public RandomArticleController(RandomArticleInputBoundary randomArticleInputBoundary) {
        this.randomArticleInputBoundary= randomArticleInputBoundary;
    }
    public void execute(String country, CensorshipRuleSet censorshipRuleSet) {
        RandomArticleInputData inputData = new RandomArticleInputData(country, censorshipRuleSet);
        randomArticleInputBoundary.execute(inputData);
    }

}
