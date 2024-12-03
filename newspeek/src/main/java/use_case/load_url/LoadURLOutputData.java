package use_case.load_url;

import entity.article.Article;

/**
 *  This loads the data from the URL to be displayed.
 */
public class LoadURLOutputData {
    private final Article article;
    private final boolean useCaseFailed;

    public LoadURLOutputData(Article article, boolean useCaseFailed) {
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
