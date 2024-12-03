package use_case.load_url;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class LoadURLInputDataTest {
    @Test
    void URLtest() {
        LoadURLInputData data = new LoadURLInputData("Test URL Input Data");
        assertEquals("Test URL Input Data", data.getURL());
    }
}
