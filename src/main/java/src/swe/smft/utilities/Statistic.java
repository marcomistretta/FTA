package src.swe.smft.utilities;
import org.apache.commons.math3.distribution.TDistribution;
import java.util.ArrayList;

public class Statistic {

    public static ArrayList<Float> sampleVariance(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults, ArrayList<Float> sampleMeanList, int l, int N) {
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i = 0; i < l; i++) {
            float sum = 0;
            for (int j = 0; j < N; j++)
                //todo leggi: ho invertito indici i e j nel get di sampleMean, controlla se va bene
                sum = (float) (sum + (Math.pow(((quantizedResults.get(j).get(i).getElement1() ? 1f : 0f) - sampleMeanList.get(i)), 2)));
            list.add(sum / (N - 1));
        }
        return list;
    }

    public static ArrayList<Float> sampleMean(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults, int l, int N) {
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i = 0; i < l; i++) {
            float sum = 0;
            for (int j = 0; j < N; j++)
                sum += (quantizedResults.get(j).get(i).getElement1() ? 1 : 0);
            list.add(sum / N);
        }
        return list;
    }

    public static double[][] confidenceInterval(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults, float alpha) {
        int N = quantizedResults.size();
        int l = quantizedResults.get(0).size();
        TDistribution TDist = new TDistribution(N - 1);
        double[][] ret = new double[2][l];
        // ret[0] = lowerCI;
        // ret[1] = upperCI;

        double Tvalue = TDist.inverseCumulativeProbability(1 - (alpha / 2));
        //System.out.println("TSTUDENT: " + value);

        ArrayList<Float> sampleMeanList = sampleMean(quantizedResults, l, N);
        ArrayList<Float> sampleVarianceList = sampleVariance(quantizedResults, sampleMeanList, l, N);

        double radN = Math.sqrt(N);

        for(int i = 0; i<l; i++){
            double S = Math.sqrt(sampleVarianceList.get(i));
            // System.err.println("N: " + N);
            // System.err.println("S: " + S);
            // System.err.println("value: " + value);
            double coeff = (S/radN)*Tvalue;
            // System.err.println(coeff);
            double up = sampleMeanList.get(i) + coeff;
            double low = sampleMeanList.get(i) - coeff;
            if(up>1)
                up = 1;
            if(up<0)
                up=0;
            if(low>1)
                low = 1;
            if(low<0)
                low=0;
            ret[0][i] = low;
            // System.err.println("low: " + low);
            ret[1][i] = up;
            // System.err.println("up: " + up);
        }

        return ret;
    }

}
