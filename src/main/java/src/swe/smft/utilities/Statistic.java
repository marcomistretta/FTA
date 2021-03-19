package src.swe.smft.utilities;

import org.apache.commons.math3.distribution.TDistribution;

import java.util.ArrayList;

public class Statistic {

    public static double[] sampleVariance(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults, double[] sampleMeanList) {
        int N = quantizedResults.size();
        int l = quantizedResults.get(0).size();
        double[] list = new double[l];
        double start = System.currentTimeMillis();
        // per ogni istante devo calcolare la varianza campionaria basandomi sul campione delle N simulazion;
        for (int i = 0; i < l; i++) {
            double sum = 0;
            Timer.estimatedTime(l, start, i, "Varianza campionaria");
            for (int j = 0; j < N; j++)
                // quantizedResults.get(j).get(i).getElement1() ::
                // della j-esima simulazione (su N), il i-esimo istante (su l)
                sum = sum + (Math.pow(((quantizedResults.get(j).get(i).getElement1() ? 1f : 0f) - sampleMeanList[i]), 2));
            list[i] = sum / (N - 1);
        }

        return list;
    }

        public static double[] sampleMean(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults) {
            int N = quantizedResults.size();
            int l = quantizedResults.get(0).size();
            double[] list = new double[l];
            double start = System.currentTimeMillis();
            for (int i = 0; i < l; i++) {
                Timer.estimatedTime(l, start, i, "Sample Mean");
                double sum = 0;
                for(int j = 0; j < N; j++)
                    sum += (quantizedResults.get(j).get(i).getElement1() ? 1 : 0);
                list[i] = sum / N;
            }
            return list;
    }
     // fixme ho invertito le chiamate nel main, così sample mean lo calcola una volta sola
    public static double[][] confidenceInterval(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults, double alpha, double[] sampleMean) {
        int N = quantizedResults.size();
        int l = quantizedResults.get(0).size();
        TDistribution TDist = new TDistribution(N - 1);
        double[][] ret = new double[2][l];
        // ret[0] = lowerCI;
        // ret[1] = upperCI;

        double Tvalue = TDist.inverseCumulativeProbability(1 - (alpha / 2));
        //System.out.println("TSTUDENT: " + value);

        double[] sampleVarianceList = sampleVariance(quantizedResults, sampleMean);

        double radN = Math.sqrt(N);
        float start = System.currentTimeMillis();
        for (int i = 0; i < l; i++) {
            //fixme, è talmente veloce che i coefficienti esplodono, io lo leverei da qua
            //Timer.estimatedTime(l, start, i, "Confidence Interval");
            double S = Math.sqrt(sampleVarianceList[i]);
            // System.err.println("N: " + N);
            // System.err.println("S: " + S);
            // System.err.println("value: " + value);
            double coeff = (S / radN) * Tvalue;
            // System.err.println(coeff);
            double up = sampleMean[i] + coeff;
            double low = sampleMean[i] - coeff;
            if (up > 1)
                up = 1;
            if (up < 0)
                up = 0;
            if (low > 1)
                low = 1;
            if (low < 0)
                low = 0;
            ret[0][i] = low;
            // System.err.println("low: " + low);
            ret[1][i] = up;
            // System.err.println("up: " + up);
        }

        return ret;
    }

    public static double[] allDifference(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults) {
        int l = quantizedResults.get(0).size();
        int N = quantizedResults.size();
        double[] result = new double[l];
        double start = System.currentTimeMillis();
        // per ogni istante
        for (int k = 0; k < l; k++) {
            Timer.estimatedTime(l, start, k, "Calcolo differenze");
            double sumDiff = 0;
            // i-esimo di N valori
            for (int i = 0; i < N - 1; i++) {
                // j :: i+1-esimo di N valori
                for (int j = i + 1; j < N; j++) {
                    sumDiff = sumDiff + Math.abs((quantizedResults.get(i).get(k).getElement1() ? 1 : 0)
                            - (quantizedResults.get(j).get(k).getElement1() ? 1 : 0));
                }
            }
            // TODO momentaneamente mutato il binomiale
            // result[k] = sumDiff / Calculator.binomialCoefficient(N, 2);
            result[k] = sumDiff / N;
        }
        return result;
    }
}
