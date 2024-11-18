package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addApiDataAccessObject(".env")
                .addReaderView()
                .addRandomArticleUseCase()
                .build();

        application.pack();
        application.setVisible(true);
    }

    public static int returnFive() {
        return 5;
    }
}
