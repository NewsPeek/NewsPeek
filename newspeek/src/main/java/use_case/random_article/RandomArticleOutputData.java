package use_case.random_article;

import entity.article.Article;

/**
 * Output Data for the Random Article Use Case, containing the article and the use case status.
 */
public class RandomArticleOutputData {

    private final Article article;
    private final boolean useCaseFailed;

    public RandomArticleOutputData(Article article, boolean useCaseFailed) {
        this.article = article;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Returns the article found by this use case. If isUseCaseFailed() is true, this return value is undefined.
     * @return the article found by this use case.
     */
    public Article getArticle() {
        return this.article;
    }

    /**
     * Returns whether the use case failed and an error should be displayed.
     * @return whether the use case failed.
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
