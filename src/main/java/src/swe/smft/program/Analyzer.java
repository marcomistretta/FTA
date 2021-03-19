package src.swe.smft.program;

import src.swe.smft.harryplotter.HarryPlotter;
import src.swe.smft.memory.DataCentre;
import src.swe.smft.utilities.Pair;
import src.swe.smft.utilities.Statistic;
import src.swe.smft.utilities.Timer;

import java.util.ArrayList;

public class Analyzer {
    private final Simulator s;
    private final DataCentre dc;

    public Analyzer(Simulator s, DataCentre dc) {
        this.s = s;
        this.dc = dc;
    }

    // chiama defineCI senza richiedere il plot di Sample Mean e di fault
    public void defineCI(int N, float alpha, float quantum) {
        defineCI(N, alpha, quantum, false, false);
    }

    public void defineCI(int N, float alpha, float quantum, boolean meanPlot, boolean fault) {
        dc.clear();
        double start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Timer.estimatedTime(N, start, i, "Simulazioni defineCI");
            dc.appendData(s.simulation(false));
        }
        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults =
                dc.quantizedData(quantum, s.getMaxTime());

        double[] sampleMean = Statistic.sampleMean(quantizedResults);
        double[][] CI = Statistic.confidenceInterval(quantizedResults, alpha, sampleMean);

        int l = CI[0].length;

        double[] times = new double[l];
        for (int i = 0; i < l; i++) {
            times[i] = i * quantum;
        }
        HarryPlotter.getInstance().plotReliability(times, CI, sampleMean, meanPlot, fault);
    }

    public void verifyErgodic(int N, float quantum, float eps, boolean ergodic) {
        dc.clear();

        double start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Timer.estimatedTime(N, start, i, "Simulazioni ergodic");
            dc.appendData(s.simulation(false));
        }

        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults =
                dc.quantizedData(quantum, s.getMaxTime());

        double[] differences;
        double[] sampleMean;
        double[] sampleVariance;
        double[] times;
        double[] epsF;

        if (ergodic) {
            differences = Statistic.allDifference(quantizedResults);
            epsF = new double[differences.length];
            for (int i = 0; i < differences.length; i++)
                epsF[i] = eps;
            times = new double[differences.length];
            for (int i = 0; i < differences.length; i++)
                times[i] = i * quantum;
            HarryPlotter.getInstance().plotErgodic(times, epsF, differences);
        } else {
            sampleMean = Statistic.sampleMean(quantizedResults);
            sampleVariance = Statistic.sampleVariance(quantizedResults, sampleMean);
            times = new double[sampleVariance.length];
            for (int i = 0; i < sampleMean.length; i++)
                times[i] = i * quantum;
            HarryPlotter.getInstance().plotErgodic2(times, sampleMean, sampleVariance);
        }
    }
}
