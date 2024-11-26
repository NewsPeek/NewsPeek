package data_access.article;

import java.io.IOException;

import entity.article.Article;
import use_case.export_article.ExportArticleDataAccessInterface;

/**
 * DAO to store articles in files.
 */
public class FileArticleDataAccessObject implements ExportArticleDataAccessInterface {
    @Override
    public void saveArticle(Article article) throws IOException {
        // TODO: implement
    }
}
