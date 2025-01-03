package app;

import javax.swing.JFrame;

import use_case.helpers.ScanningCensorshipService;

/**
 * Main class for NewsPeek.
 */
public class Main {
    /**
     * Main entry point for NewsPeek app. Builds the app and makes it visible.
     * @param args command-line arguments. Not used.
     */
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addApiDataAccessObject()
                .addFileArticleDataAccessObject()
                .addCensorshipRuleSetDataAccessObject()
                .addCensorshipService(new ScanningCensorshipService())
                .addReaderView()
                .addRandomArticleUseCase()
                .addSaveArticleUseCase()
                .addChooseRuleSetUseCase()
                .addPopulateListUseCase()
                .addLoadArticleUseCase()
                .addLoadURLUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }
}
