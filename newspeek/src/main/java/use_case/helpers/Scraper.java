package use_case.helpers;

import entity.article.Article;
import entity.article.ArticleFactory;

import java.awt.geom.Arc2D;
import java.io.IOException;

public interface Scraper {
    Article scrapeArticle(String url) throws IOException;
}