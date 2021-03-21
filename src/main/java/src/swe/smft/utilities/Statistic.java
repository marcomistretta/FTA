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
            Timer.estimatedTime(l, start, i, "Media campionaria");
            double sum = 0;
            for (int j = 0; j < N; j++)
                sum += (quantizedResults.get(j).get(i).getElement1() ? 1 : 0);
            list[i] = sum / N;
        }
        return list;
    }

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
        for (int i = 0; i < l; i++) {
            double S = Math.sqrt(sampleVarianceList[i]);
            double coeff = (S / radN) * Tvalue;
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
            ret[1][i] = up;
        }

        return ret;
    }

}
