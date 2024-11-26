package use_case.save_article;

import entity.article.Article;

/**
 * Input data for the Save Article use case. Contains the article to save.
 */
public class SaveArticleInputData {
    private final Article article;

    public SaveArticleInputData(Article article) {
        this.article = article;
    }

    /**
     * Returns the article to save.
     * @return the article to save.
     */
    public Article getArticle() {
        return article;
    }
}
