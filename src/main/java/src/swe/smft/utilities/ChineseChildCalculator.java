package src.swe.smft.utilities;

abstract public class ChineseChildCalculator {
    /**
     * quick math methods here ^_^
     */
    //loga b = log10 b / log10 a
    static double log2(double a) {
        if(a == 0) a = 0.5f;
        return (Math.log(a) / Math.log(2));
    }
}
