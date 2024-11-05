package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    void fiveTest() {
        assertEquals(Main.returnFive(), 5);
    }
}
