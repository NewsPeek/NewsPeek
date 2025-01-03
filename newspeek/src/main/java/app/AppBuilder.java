package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import data_access.article.APIArticleDataAccessObject;
import data_access.article.FileArticleDataAccessObject;
import data_access.censorship_rule_set.FileCensorshipRuleSetDataAccessObject;
import data_access.scraper.JReadabilityScraper;
import data_access.scraper.Scraper;
import interface_adapter.ReaderViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.choose_rule_set.ChooseRuleSetController;
import interface_adapter.choose_rule_set.ChooseRuleSetPresenter;
import interface_adapter.load_URL.LoadURLController;
import interface_adapter.load_URL.LoadURLPresenter;
import interface_adapter.load_article.LoadArticleController;
import interface_adapter.load_article.LoadArticlePresenter;
import interface_adapter.populate_list.PopulateListController;
import interface_adapter.populate_list.PopulateListPresenter;
import interface_adapter.random_article.RandomArticleController;
import interface_adapter.random_article.RandomArticlePresenter;
import interface_adapter.save_article.SaveArticleController;
import interface_adapter.save_article.SaveArticlePresenter;
import use_case.choose_rule_set.ChooseRuleSetInputBoundary;
import use_case.choose_rule_set.ChooseRuleSetInteractor;
import use_case.choose_rule_set.ChooseRuleSetOutputBoundary;
import use_case.helpers.CensorshipService;
import use_case.load_article.LoadArticleInputBoundary;
import use_case.load_article.LoadArticleInteractor;
import use_case.load_article.LoadArticleOutputBoundary;
import use_case.load_url.LoadURLInputBoundary;
import use_case.load_url.LoadURLInteractor;
import use_case.load_url.LoadURLOutputBoundary;
import use_case.populate_list_with_articles.PopulateListInputBoundary;
import use_case.populate_list_with_articles.PopulateListInteractor;
import use_case.populate_list_with_articles.PopulateListOutputBoundary;
import use_case.random_article.RandomArticleInputBoundary;
import use_case.random_article.RandomArticleInteractor;
import use_case.random_article.RandomArticleOutputBoundary;
import use_case.save_article.SaveArticleInputBoundary;
import use_case.save_article.SaveArticleInteractor;
import use_case.save_article.SaveArticleOutputBoundary;
import view.ReaderView;

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
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private ReaderView readerView;
    private ReaderViewModel readerViewModel;

    private APIArticleDataAccessObject apiArticleDataAccessObject;
    private FileCensorshipRuleSetDataAccessObject censorshipRuleSetDataAccessObject;
    private CensorshipService censorshipService;
    private FileArticleDataAccessObject fileArticleDataAccessObject;

    public AppBuilder() {
        CardLayout cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Reader View to the application.
     * @return this builder
     */
    public AppBuilder addReaderView() {
        readerViewModel = new ReaderViewModel();
        readerView = new ReaderView(readerViewModel, censorshipService);
        cardPanel.add(readerView, readerView.getViewName());
        return this;
    }

    /**
     * Adds an instance of the APIDataAccessObject to the application.
     * @return this builder
     */
    public AppBuilder addApiDataAccessObject() {
        Scraper scraper = new JReadabilityScraper();
        this.apiArticleDataAccessObject = new APIArticleDataAccessObject(scraper);
        return this;
    }

    /**
     * Adds an instance of the FileArticleDataAccessObject to the application.
     * @return this builder
     */
    public AppBuilder addFileArticleDataAccessObject() {
        this.fileArticleDataAccessObject = new FileArticleDataAccessObject();
        return this;
    }

    /**
     * Adds an instance of the FileCensorshipRuleSetDataAccessObject to the application.
     * @return this builder
     */
    public AppBuilder addCensorshipRuleSetDataAccessObject() {
        this.censorshipRuleSetDataAccessObject = new FileCensorshipRuleSetDataAccessObject();
        return this;
    }

    /**
     * Adds an implementation of CensorshipService to the application.
     * @param newCensorshipService the implementation of CensorshipService to use.
     * @return this builder
     */
    public AppBuilder addCensorshipService(CensorshipService newCensorshipService) {
        this.censorshipService = newCensorshipService;
        return this;
    }

    /**
     * Adds the Random Article Use Case to the application.
     * @return this builder
     */
    public AppBuilder addRandomArticleUseCase() {
        final RandomArticleOutputBoundary randomArticleOutputBoundary = new RandomArticlePresenter(readerViewModel);
        final RandomArticleInputBoundary randomArticleInteractor = new RandomArticleInteractor(
                apiArticleDataAccessObject, randomArticleOutputBoundary);

        final RandomArticleController controller = new RandomArticleController(randomArticleInteractor);
        readerView.setRandomArticleController(controller);
        return this;
    }

    /**
     * Adds the Save Article Use Case to the application.
     * @return this builder
     */
    public AppBuilder addSaveArticleUseCase() {
        final SaveArticleOutputBoundary presenter = new SaveArticlePresenter(readerViewModel);
        final SaveArticleInputBoundary interactor = new SaveArticleInteractor(
                fileArticleDataAccessObject, presenter);

        final SaveArticleController controller = new SaveArticleController(interactor);
        readerView.setSaveArticleController(controller);
        return this;
    }

    /**
     * Adds the Load URL Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoadURLUseCase() {
        final LoadURLOutputBoundary loadURLOutputBoundary = new LoadURLPresenter(readerViewModel);
        final LoadURLInputBoundary loadURLInteractor = new LoadURLInteractor(apiArticleDataAccessObject,
                loadURLOutputBoundary);

        final LoadURLController controller = new LoadURLController(loadURLInteractor);
        readerView.setLoadURLController(controller);
        return this;
    }

    /**
     * Add the Load Article Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLoadArticleUseCase() {
        final LoadArticleOutputBoundary presenter = new LoadArticlePresenter(readerViewModel);
        final LoadArticleInputBoundary interactor = new LoadArticleInteractor(
                fileArticleDataAccessObject, presenter);

        final LoadArticleController controller = new LoadArticleController(interactor);
        readerView.setLoadArticleController(controller);
        return this;
    }

    /**
     * Add the populate list use case to the application.
     * @return the builder
     */
    public AppBuilder addPopulateListUseCase() {
        final PopulateListOutputBoundary presenter = new PopulateListPresenter(readerViewModel);
        final PopulateListInputBoundary interactor = new PopulateListInteractor(
                fileArticleDataAccessObject, presenter);

        final PopulateListController controller = new PopulateListController(interactor);
        readerView.setPopulateListController(controller);
        return this;
    }

    /**
     * Adds the Choose Rule Set Use Case to the application.
     * @return this builder
     */
    public AppBuilder addChooseRuleSetUseCase() {
        final ChooseRuleSetOutputBoundary chooseRuleSetOutputBoundary = new ChooseRuleSetPresenter(readerViewModel);
        final ChooseRuleSetInputBoundary chooseRuleSetInteractor = new ChooseRuleSetInteractor(
                censorshipRuleSetDataAccessObject, chooseRuleSetOutputBoundary);

        final ChooseRuleSetController controller = new ChooseRuleSetController(chooseRuleSetInteractor);
        readerView.setChooseRuleSetController(controller);
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
