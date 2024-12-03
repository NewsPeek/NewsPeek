package use_case.populate_list_with_articles;

import entity.article.Article;

import java.util.Map;

public class PopulateListOutputData {

    private final Map<String, String> articleList;

    public PopulateListOutputData(Map<String, String> articleList) {
        this.articleList = articleList;
    }

    /**
     * Returns the loaded article.
     * @return the loaded article list.
     */
    public Map<String, String> getArticleList() {
        return articleList;
    }
}
