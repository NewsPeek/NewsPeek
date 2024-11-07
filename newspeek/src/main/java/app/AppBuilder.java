package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import interface_adapter.ViewManagerModel;

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


    public AppBuilder() {
        // TODO: implement
    }

    // TODO: add all the addFoo() methods
    /**
     * Adds *SOMETHING* to the application.
     * @return this builder
     */
    public AppBuilder addStuff() {
        // TODO: implement
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the SignupView to be displayed.
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("NewsPeek");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // TODO: set initial state
        // viewManagerModel.setState(somethingView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
