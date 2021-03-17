package test.swe.smft.utilities.Statistic;

import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.swe.smft.utilities.Calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {
    @Test
    @DisplayName("Test binomiale")
    void binomialTest() {
        //k > n
        assertEquals(Calculator.binomialCoefficient(2, 4), 0);
        //casi base
        assertEquals(Calculator.binomialCoefficient(0, 0), 1);
        assertEquals(Calculator.binomialCoefficient(1, 0), 1);
        assertEquals(Calculator.binomialCoefficient(1, 1), 1);
        //numeri negativi
        try {
            assertEquals(Calculator.binomialCoefficient(-1, 0), 33);
        } catch (NumberIsTooSmallException e) {
            assertEquals(0, 0);
        }
        try {
            assertEquals(Calculator.binomialCoefficient(0, -1), 33);
        } catch (NumberIsTooSmallException e) {
            assertEquals(0, 0);
        }
        //recursive case
        assertEquals(
                Calculator.binomialCoefficient(2, 1),
                Calculator.binomialCoefficient(1, 1) + Calculator.binomialCoefficient(1, 0)
        );
    }
}
