package view;

import interface_adapter.ReaderState;
import interface_adapter.ReaderViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is reading a censored article.
 */
public class ReaderView extends JPanel implements PropertyChangeListener {

    private final String viewName = "reader";
    private final ReaderViewModel readerViewModel;


    public ReaderView(ReaderViewModel readerViewModel) {
        this.readerViewModel = readerViewModel;
        this.readerViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Reader Screen");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        final JPanel buttons = new JPanel();

        /* template for a button
        button = new JButton("Log Out");
        buttons.add(button);


        button.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                evt -> {
                    if (evt.getSource().equals(button)) {
                        final ReaderState readerState = readerViewModel.getState();

                        ____Controller.execute(...);
                    }
                }
        );
        */

        this.add(title);

        this.add(buttons);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final ReaderState state = (ReaderState) evt.getNewValue();
            // change the view based on the updated state
        }
    }

    public String getViewName() {
        return viewName;
    }

    /*
    public void set_______Controller(__UseCase__Controller __useCase__Controller) {
        this.__useCase__Controller = __useCase__Controller;
    }
    */
}
