package src.swe.smft.utilities;

import org.apache.commons.math3.distribution.TDistribution;

import java.util.ArrayList;

public class Statistic {

    private ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults;
    private int N;
    private int l;
    private float alpha;
    private TDistribution TDist;

    private ArrayList<Float> sampleMeanList = new ArrayList<Float>();
    private ArrayList<Float> sampleVarianceList = new ArrayList<Float>();

    public Statistic(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults, float alpha) {
        this.quantizedResults = quantizedResults;
        this.N = quantizedResults.size();
        this.l = quantizedResults.get(0).size();
        sampleMeanList = sampleMean();
        sampleVarianceList = sampleVariance();
        // TODO check
        this.alpha = alpha;
        TDist = new TDistribution(N - 1);
    }

    public ArrayList<Float> sampleVariance() {
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i = 0; i < l; i++) {
            float sum = 0;
            for (int j = 0; j < N; j++)
                sum = (float) (sum + (Math.pow(((quantizedResults.get(j).get(i).getElement1() ? 1f : 0f) - sampleMeanList.get(j)), 2)));
            list.add(sum / (N - 1));
        }
        return list;
    }

    public ArrayList<Float> sampleMean() {
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i = 0; i < l; i++) {
            float sum = 0;
            for (int j = 0; j < N; j++)
                sum += (quantizedResults.get(j).get(i).getElement1() ? 1 : 0);
            list.add(sum / N);
        }
        return list;
    }

    public double[][] confidenceInterval() {
        double[] upperCI = new double[l];
        double[] lowerCI = new double[l];

        /*
        System.out.println("MEDIA ");
        for (float i : sampleMeanList)
            System.out.print(i + "  ");
        System.out.println();
        System.out.println("VARIANZA ");
        for (float j : sampleVarianceList)
            System.out.print(j + "  ");
        System.out.println();
         */

        double value = TDist.inverseCumulativeProbability(1 - (alpha / 2));
        //System.out.println("TSTUDENT: " + value);

        for(int i = 0; i<l; i++){
            double S = Math.sqrt(sampleVarianceList.get(i));
            double radN = Math.sqrt(N);
            // System.err.println("N: " + N);
            // System.err.println("S: " + S);
            // System.err.println("value: " + value);
            double coeff = (S/radN)*value;
            // System.err.println(coeff);
            double up = sampleMean().get(i) + coeff;
            double low = sampleMean().get(i) - coeff;
            lowerCI[i] = low;
            // System.err.println("low: " + low);
            upperCI[i] = up;
            // System.err.println("up: " + up);
        }

        double[][] ret = new double[2][];
        ret[0] = lowerCI;
        ret[1] = upperCI;

        return ret;


    }

}
