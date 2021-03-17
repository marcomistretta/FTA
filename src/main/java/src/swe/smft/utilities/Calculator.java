/* new */
package src.swe.smft.utilities;

import org.apache.commons.math3.exception.NumberIsTooSmallException;

abstract public class Calculator {

    public static int binomialCoefficient(int n, int k) {
        if (n < 0)
            System.err.println("errore");
        if (k < 0)
            System.err.println("errore");
        if (k > n)
            return 0;
        if (k == 0 || k == n)
            return 1;

        // recursive
        return binomialCoefficient(n - 1, k - 1)
                + binomialCoefficient(n - 1, k);
    }

}
