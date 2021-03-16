package test.swe.smft.utilities.Statistic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.swe.smft.utilities.Timer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class TimerTest {

    Timer timer;

    @Test
    @DisplayName("NextTimw")
    public void testNextTime() {
        timer = new Timer(0);
        assertNotEquals(0, timer.getMaxTime());
        timer = new Timer(100000);
        assertEquals(-1, timer.nextTime(0));

    }
}
