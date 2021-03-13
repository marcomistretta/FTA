package test.swe.smft.utilities.Statistic;


import org.junit.jupiter.api.*;
import src.swe.smft.utilities.Pair;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StatisticTest {

    private static ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults;

    @BeforeEach
    public void setUp() throws Exception {
        System.out.println("tutto pronto");
    }

    @AfterEach
    public void afterSetUp() throws Exception {
        // solo per provare
        System.out.println("tutto fatto");
    }


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
