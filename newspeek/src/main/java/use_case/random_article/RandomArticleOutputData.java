package use_case.random_article;

import entity.article.Article;

/**
 * Output Data for the Logout Use Case.
 */
public class RandomArticleOutputData {

    private final Article article;
    private final boolean useCaseFailed;

    public RandomArticleOutputData(Article article, boolean useCaseFailed) {
        this.article = article;
        this.useCaseFailed = useCaseFailed;
    }

    public Article getArticle() {
        return this.article;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
