package src.swe.smft.utilities;

import java.util.ArrayList;
// array esterno le varie sperimentazioni
// array interno i vari istanti

// TODO check
public class Statistic {

    private ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults;

    private int N;

    private ArrayList<Float> sampleMeanList = new ArrayList<Float>();
    private ArrayList<Float> sampleVarianceList = new ArrayList<Float>();

    public Statistic(ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults) {
        this.quantizedResults = quantizedResults;
        this.N = quantizedResults.size();
        sampleMeanList = sampleMean();
        sampleVarianceList = sampleVariance();
    }

    public ArrayList<Float> sampleVariance() {
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i = 0; i < quantizedResults.get(0).size(); i++) {
            float mult = 1;
            for (int j = 0; j < N; j++)
                mult *= (Math.pow(((float) Integer.parseInt(String.valueOf(quantizedResults.get(j).get(i).getElement2())) - sampleMeanList.get(j)), 2));
            list.add(mult / (N - 1));
        }
        return list;
    }

    public ArrayList<Float> sampleMean() {
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i = 0; i < quantizedResults.get(0).size(); i++) {
            float sum = 0;
            for (int j = 0; j < N; j++)
                sum = sum + Integer.parseInt(String.valueOf(quantizedResults.get(j).get(i).getElement2()));
            list.add(sum / N);
        }
        return list;
    }

    public void confidenceInterval() {
    }
}
