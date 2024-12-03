package use_case.random_article;

import entity.article.Article;

/**
 * Output Data for the Random Article Use Case, containing the article.
 */
public class RandomArticleOutputData {

    private final Article article;

    public RandomArticleOutputData(Article article) {
        this.article = article;
    }

    /**
     * Returns the article found by this use case.
     * @return the article found by this use case.
     */
    public Article getArticle() {
        return this.article;
    }
}
