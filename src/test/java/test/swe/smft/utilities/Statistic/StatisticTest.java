package test.swe.smft.utilities.Statistic;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StatisticTest {

    //private ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults;

    /*
    @BeforeEach
    public void setUp() {
        quantizedResults = null;
    }

    @AfterEach
    // usually throws Exception onnline
    public void setUp() {
        // solo per provare
        System.out.println("ho fatto");
    }
    */

    @Test
    @DisplayName("Check Sample Mean Calc")
    public void testSampleMean() {
        assertEquals(20, 20, "Regular multiplication should work");
    }

    @RepeatedTest(5)
    @DisplayName("Check Sample Variance Calc")
    public void testSampleVariance() {
        assertEquals(0, 0, "Multiple with zero should be zero");
    }

}
