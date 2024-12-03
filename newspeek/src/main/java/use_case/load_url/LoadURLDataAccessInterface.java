package use_case.load_url;

import entity.article.Article;

import java.io.IOException;

public interface LoadURLDataAccessInterface {
    /**
     * Returns the article scraped from the given URL.
     * @param url the URL from which to load the article. Should point to a page formatted like a news article.
     * @return the article, with all fields populated as well as possible from the page.
     * @throws IOException if something goes wrong in fetching or parsing the article.
     */
    Article getArticleFromUrl(String url) throws IOException;
}
