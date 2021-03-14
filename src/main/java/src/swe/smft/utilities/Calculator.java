/* new */
package src.swe.smft.utilities;

abstract public class Calculator {

    //loga b = log10 b / log10 a
    static double log2(double a) {
        if(a == 0) a = 0.5f;
        return (Math.log(a) / Math.log(2));
    }

    // TODO check
    static int binomialCoeff(int n, int k)
    {
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
