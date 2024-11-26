package data_access.article;

import java.io.IOException;

import entity.article.Article;
import use_case.save_article.SaveArticleDataAccessInterface;

/**
 * DAO to store articles in files.
 */
public class FileArticleDataAccessObject implements SaveArticleDataAccessInterface {
    @Override
    public void saveArticle(Article article) throws IOException {
        // TODO: implement
    }
}
