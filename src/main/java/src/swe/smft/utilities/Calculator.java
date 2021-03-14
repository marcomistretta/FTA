/* new */
package src.swe.smft.utilities;

import org.apache.commons.math3.exception.NumberIsTooSmallException;

abstract public class Calculator {

    public static int binomialCoeff(int n, int k) throws NumberIsTooSmallException {
        if (n < 0) throw new NumberIsTooSmallException(n, 0, false);
        if (k < 0) throw new NumberIsTooSmallException(k, 0, false);
        // base Cases
        if (k > n)
            return 0;
        if (k == 0 || k == n)
            return 1;

        // recur
        return binomialCoeff(n - 1, k - 1)
                + binomialCoeff(n - 1, k);
    }

}
