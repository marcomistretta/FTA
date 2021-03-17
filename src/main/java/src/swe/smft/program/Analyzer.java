package src.swe.smft.program;

import src.swe.smft.harryplotter.HarryPlotter;
import src.swe.smft.memory.DataCentre;
import src.swe.smft.utilities.Pair;
import src.swe.smft.utilities.Statistic;

import java.util.ArrayList;

public class Analyzer {
    private final Simulator s;
    private final DataCentre dc;

    public Analyzer(Simulator s, DataCentre dc) {
        this.s = s;
        this.dc = dc;
    }

    // chiama defineCI senza richiedere il plot di Sample Mean
    public void defineCI(int N, float alpha, int quantum) {
        defineCI(N, alpha, quantum, false);
    }

    public void defineCI(int N, float alpha, int quantum, boolean meanPlot) {
        dc.clear();

        for (int i = 0; i < N; i++) {
            dc.appendData(s.simulation(false));
        }

        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults =
                dc.quantizedData(quantum, s.getMaxTime());

        double[][] CI = Statistic.confidenceInterval(quantizedResults, alpha);
        double[] sampleMean = Statistic.sampleMean(quantizedResults);

        int l = CI[0].length;
        double time = 0;
        double[] times = new double[l];
        for (int i = 0; i < l; i++) {
            times[i] = time;
            time = time + quantum;
        }
        HarryPlotter.getInstance().plotReliability(times, meanPlot, CI, sampleMean);
    }

    public void verifyErgodic(int N, int quantum, float eps) {
        dc.clear();

        for (int i = 0; i < N; i++) {
            dc.appendData(s.simulation(true));
        }

        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults =
                dc.quantizedData(quantum, s.getMaxTime());

        double[] differences = Statistic.allDifference(quantizedResults);
        double time = 0;
        double[] times = new double[differences.length];
        double[] epsF = new double[differences.length];
        for (int i = 0; i < differences.length; i++) {
            times[i] = time;
            time = time + quantum;
            epsF[i] = eps;
        }

        HarryPlotter.getInstance().plotErgodic(times, epsF, differences);

    }
}
