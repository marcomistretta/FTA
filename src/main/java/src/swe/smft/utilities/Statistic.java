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
                mult *= (Math.pow(((quantizedResults.get(j).get(i).getElement1() ? 1f : 0f) - sampleMeanList.get(j)), 2));
            list.add(mult / (N - 1));
        }
        return list;
    }

    public ArrayList<Float> sampleMean() {
        ArrayList<Float> list = new ArrayList<Float>();
        for (int i = 0; i < quantizedResults.get(0).size(); i++) {
            float sum = 0;
            for (int j = 0; j < N; j++)
                sum += (quantizedResults.get(j).get(i).getElement1() ? 1 : 0);
            list.add(sum / N);
        }
        return list;
    }

    public void confidenceInterval() {
        System.out.println("MEDIA ");
        for (float i : sampleMeanList)
            System.out.print(i + "  ");
        System.out.println("VARIANZA ");
        for (float j : sampleVarianceList)
            System.out.print(j + "  ");
    }
}
