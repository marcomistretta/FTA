package src.swe.smft.program;

import src.swe.smft.plot.HarryPlotter;
import src.swe.smft.memory.DataCenter;
import src.swe.smft.utilities.QuantizedSample;
import src.swe.smft.utilities.Statistic;
import src.swe.smft.utilities.Timer;

import java.util.ArrayList;

public class Analyzer {
    private final Simulator sim;
    private final DataCenter dc;

    public Analyzer(Simulator s, DataCenter dc) {
        this.sim = s;
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
            Timer.estimatedTime(N, start, i, "Simulazioni per calcolo Intervalli di Confidenza");
            sim.getTreeManager().reset();
            dc.appendData(sim.simulation());
        }
        ArrayList<ArrayList<QuantizedSample>> quantizedResults =
                dc.quantizedData(quantum, sim.getMaxTime());
        double[] sampleMean = Statistic.sampleMean(quantizedResults);
        double[][] CI = Statistic.confidenceInterval(quantizedResults, alpha, sampleMean);
        int l = CI[0].length;

        double[] times = new double[l];
        for (int i = 0; i < l; i++) {
            times[i] = i * quantum;
        }
        HarryPlotter.getInstance().plotReliability(times, CI, sampleMean, meanPlot, fault);
    }

    public void verifyErgodic(int N, float quantum, double meanPrecision, double varPrecision) {
        dc.clear();

        double start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Timer.estimatedTime(N, start, i, "Simulazioni per verifica Ergodicità");
            sim.getTreeManager().randomReset();
            dc.appendData(sim.simulation());
        }

        ArrayList<ArrayList<QuantizedSample>> quantizedResults =
                dc.quantizedData(quantum, sim.getMaxTime());
        double[] sampleMean;
        double[] sampleStandardDeviation;
        double[] times;

        sampleMean = Statistic.sampleMean(quantizedResults);
        sampleStandardDeviation = Statistic.sampleStandardDeviation(quantizedResults, sampleMean);
        times = new double[sampleStandardDeviation.length];
        for (int i = 0; i < sampleMean.length; i++)
            times[i] = i * quantum;
        HarryPlotter.getInstance().plotErgodic(times, sampleMean, sampleStandardDeviation);
        findConvergency(times, sampleMean, sampleStandardDeviation, meanPrecision, varPrecision);

        verifyErgodicAlternative(N, quantum);

    }

    public void verifyErgodicAlternative(int N, float quantum) {

        ArrayList<ArrayList<QuantizedSample>>[] quantizedResults = new ArrayList[10];
        ArrayList<Boolean> status = new ArrayList<>();

        double start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            dc.clear();
            Timer.estimatedTime(10, start, i, "Simulazioni per verifica Ergodicità 2");
            sim.getTreeManager().randomReset();
            status = sim.getTreeManager().getBasicEventsStatus();
            for (int count = 0; count < N/5; count++) {
                sim.getTreeManager().setBasicEventsStatus(status);
                dc.appendData(sim.simulation());
            }
            quantizedResults[i] = dc.quantizedData(quantum, sim.getMaxTime());
        }

        double[][] sampleMeans = new double[10][];
        for (int i = 0; i < 10; i++)
            sampleMeans[i] = Statistic.sampleMean(quantizedResults[i]);

        double[] times = new double[sampleMeans[0].length];
        for (int i = 0; i < sampleMeans[0].length; i++)
            times[i] = i * quantum;

        HarryPlotter.getInstance().plotErgodic2(times, sampleMeans);
    }

    private void findConvergency(double[] times, double[] sampleMean, double[] sampleStandardDeviation,
                                 double meanPrecision, double varPrecision) {
        double min = 1.1f;
        double max = -0.1f;
        double start = times[times.length - 1];
        int count = times.length / 10;
        for (int i = times.length - 1; i >= 0; i--) {
            // tra gli ultimi valori varianza troppo alta
            if (sampleStandardDeviation[i] > varPrecision && count > 0) {
                System.err.println("sistema probabilmente non ergodico, deviazione standard campionaria  maggiore di "
                        + varPrecision);
                return; // colpa della varianza
            }
            if (sampleMean[i] > max) max = sampleMean[i];
            if (sampleMean[i] < min) min = sampleMean[i];
            // tra gli ultimi valori media non costante
            if ((max - min) > meanPrecision && count > 0) {
                System.err.println(
                        "sistema probabilmente non ergodico, media campionaria non sufficientemente costante");
                return; // colpa del valore medio che non è costante
            }
            // è stato ergodico, ma poi varianza troopo alta
            if (sampleStandardDeviation[i] > varPrecision && count <= 0) {
                System.err.println(
                        "Verifica ergodicità arrestata per valore di deviazione standar campionaria maggiore di "
                                + varPrecision);
                break;
            }
            // è stato ergodico ma poi media non costante
            if ((max - min) > meanPrecision && count <= 0) {
                System.err.println(
                        "Verifica ergodicità arrestata per valore di media campionaria non sufficientemente costante");
                break;
            }
            // continua ad essere ergodico
            else {
                // salvo il tempo dal quale sta continuando a valere la convergenza
                start = times[i];
                count--;
            }
        }
        System.err.println("Il sistema mostra la sua natura ergodica a partire dall'istante: " + start);
    }
}
