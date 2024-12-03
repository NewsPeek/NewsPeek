package use_case.populate_list_with_articles;

import entity.article.Article;
import use_case.load_article.LoadArticleDataAccessInterface;
import use_case.load_article.LoadArticleInputData;
import use_case.load_article.LoadArticleOutputBoundary;
import use_case.load_article.LoadArticleOutputData;

import java.io.IOException;
import java.util.Map;

public class PopulateListInteractor implements PopulateListInputBoundary {
    private final PopulateListDataAccessInterface dataAccessInterface;
    private final PopulateListOutputBoundary presenter;

    public PopulateListInteractor(PopulateListDataAccessInterface dataAccessInterface,
                                 PopulateListOutputBoundary presenter) {
        this.dataAccessInterface = dataAccessInterface;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
            try {
                Map<String, String> articleList = dataAccessInterface.listSavedArticles();
                PopulateListOutputData outputData = new PopulateListOutputData(articleList);
                presenter.prepareSuccessView(outputData);
            } catch (IOException exception) {
                presenter.prepareFailView(exception.getMessage());
            }
        }
    }


