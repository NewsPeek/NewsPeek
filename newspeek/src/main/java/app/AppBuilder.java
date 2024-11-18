package app;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.APIDataAccessObject;
import entity.article.ArticleFactory;
import entity.article.CommonArticleFactory;
import interface_adapter.ReaderViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.random_article.RandomArticleController;
import interface_adapter.random_article.RandomArticlePresenter;
import use_case.helpers.JReadabilityScraper;
import use_case.helpers.Scraper;
import use_case.random_article.RandomArticleInputBoundary;
import use_case.random_article.RandomArticleInteractor;
import use_case.random_article.RandomArticleOutputBoundary;
import view.ReaderView;

import java.awt.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues for this lab; we encourage
//                  your team to think about ways to refactor the code to resolve these
//                  if your team decides to work with this as your starter code
//                  for your final project this term.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private ReaderView readerView;
    private ReaderViewModel readerViewModel;

    private final ArticleFactory articleFactory = new CommonArticleFactory();
    private APIDataAccessObject apiDataAccessObject;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Reader View to the application.
     * @return this builder
     */
    public AppBuilder addReaderView() {
        readerViewModel = new ReaderViewModel();
        readerView = new ReaderView(readerViewModel);
        cardPanel.add(readerView, readerView.getViewName());
        return this;
    }

    public AppBuilder addApiDataAccessObject(String dotenvPath) {
        Scraper scraper = new JReadabilityScraper(articleFactory);
        this.apiDataAccessObject = new APIDataAccessObject(scraper, dotenvPath);

        return this;
    }

    /**
     * Adds the Random Article Use Case to the application.
     * @return this builder
     */
    public AppBuilder addRandomArticleUseCase() {
        final RandomArticleOutputBoundary randomArticleOutputBoundary = new RandomArticlePresenter(readerViewModel);
        final RandomArticleInputBoundary randomArticleInteractor = new RandomArticleInteractor(
                apiDataAccessObject, randomArticleOutputBoundary);

        final RandomArticleController controller = new RandomArticleController(randomArticleInteractor);
        readerView.setRandomArticleController(controller);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("NewsPeek");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(readerView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
