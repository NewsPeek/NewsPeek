package interface_adapter;

/**
 * The View Model for the Reader View.
 */
public class ReaderViewModel extends ViewModel<ReaderState> {

    public ReaderViewModel() {
        super("reader");
        setState(new ReaderState());
    }

}
