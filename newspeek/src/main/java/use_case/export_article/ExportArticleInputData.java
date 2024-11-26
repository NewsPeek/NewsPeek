package use_case.export_article;

import entity.article.Article;

/**
 * Input data for the Export Article use case. Contains the article to save.
 */
public class ExportArticleInputData {
    private final Article article;

    public ExportArticleInputData(Article article) {
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
