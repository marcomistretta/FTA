package src.swe.smft.program;

import src.swe.smft.event.TreeManager;
import src.swe.smft.plot.HarryPlotter;
import src.swe.smft.memory.DataCentre;
import src.swe.smft.utilities.QuantizedSample;
import src.swe.smft.utilities.Statistic;
import src.swe.smft.utilities.Timer;

import java.util.ArrayList;

public class Analyzer {
    private final Simulator s;
    private final DataCentre dc;
    // TODO ho aggiunto il riferimento a tree manager
    private final TreeManager treeManager;

    public Analyzer(Simulator s, DataCentre dc, TreeManager tm) {
        this.s = s;
        this.dc = dc;
        this.treeManager = tm;
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
            treeManager.reset();
            dc.appendData(s.simulation());
        }
        ArrayList<ArrayList<QuantizedSample>> quantizedResults =
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

    public void verifyErgodic(int N, float quantum, double meanPrecision, double varPrecision) {
        dc.clear();

        double start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Timer.estimatedTime(N, start, i, "Simulazioni per verifica Ergodicità");
            treeManager.randomReset();
            dc.appendData(s.simulation());
        }

        ArrayList<ArrayList<QuantizedSample>> quantizedResults =
                dc.quantizedData(quantum, s.getMaxTime());

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

        /*
        // questo è il coso vecchio
        ArrayList<ArrayList<QuantizedSample>> temp = new ArrayList<>();
        // assumo N multiplo di 10 (oltre che multiplo del quanto)
        double[][] sampleMeans = new double[10][];

        for (int count = 0; count < 10; count++) {
            for (int i = quantizedResults.size() / 10 * count; i < quantizedResults.size() / 10 * (count + 1); i++) {
                temp.add(quantizedResults.get(i));
            }
            sampleMeans[count] = Statistic.sampleMean(temp);
            temp.clear();
        }
        HarryPlotter.getInstance().plotErgodic2(times, sampleMeans);
        */
        // TODO move?
        verifyErgodic2(N, quantum);

    }

    // TODO added
    public void verifyErgodic2(int N, float quantum) {
        dc.clear();

        ArrayList<ArrayList<QuantizedSample>>[] quantizedResults = new ArrayList[10];

        double start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            Timer.estimatedTime(N, start, i, "Simulazioni per verifica Ergodicità 2!");
            treeManager.randomReset();
            for (int count = 0; count < N / 10; count++) {
                treeManager.randomReset();
                dc.appendData(s.simulation());
            }
            quantizedResults[i] = dc.quantizedData(quantum, s.getMaxTime());
            dc.clear();
        }

        double[][] sampleMeans = new double[10][];
        for (int i = 0; i < 10; i++)
            sampleMeans[i] = Statistic.sampleMean(quantizedResults[i]);

        double[] times = new double[sampleMeans[0].length];
        for (int i = 0; i < sampleMeans.length; i++)
            times[i] = i * quantum;

        HarryPlotter.getInstance().plotErgodic2(times, sampleMeans);
    }

    private void findConvergency(double[] times, double[] sampleMean, double[] sampleStandardDeviation, double meanPrecision, double varPrecision) {
        double min = 1.1f;
        double max = -0.1f;
        double start = times[times.length - 1];
        int count = times.length / 10;
        for (int i = times.length - 1; i >= 0; i--) {
            // tra gli ultimi valori varianza troppo alta
            if (sampleStandardDeviation[i] > varPrecision && count > 0) {
                // System.out.println("SV: "+sampleStandardDeviation[i]);
                // System.out.println("Count: "+count);
                System.err.println("sistema probabilmente non ergodico, deviazione standar campionaria  maggiore di " + varPrecision);
                return; // colpa della varianza
            }
            if (sampleMean[i] > max) {
                // System.out.println("SM: "+sampleMean[i]);
                // System.out.println("Max: "+max);
                max = sampleMean[i];
            }
            if (sampleMean[i] < min) {
                // System.out.println("SM: "+sampleMean[i]);
                // System.out.println("Min: "+min);
                min = sampleMean[i];
            }
            // tra gli ultimi valori media non costante
            if ((max - min) > meanPrecision && count > 0) {
                // System.out.println("Diff: "+(max-min));
                // System.out.println("Eps: "+meanPrecision);
                // System.out.println("Count: "+count);
                System.err.println("sistema probabilmente non ergodico, media campionaria non sufficientemente costante");
                return; // colpa del valore medio che non è costante
            }
            // è stato ergodico, ma poi varianza troopo alta
            if (sampleStandardDeviation[i] > varPrecision && count <= 0) {
                // System.out.println("SV: "+sampleStandardDeviation[i]);
                // System.out.println("Count: "+count);
                System.err.println("Verifica ergodicità arrestata per valore di deviazione standar campionaria maggiore di " + varPrecision);
                break;
            }
            // è stato ergodico ma poi media non costante
            if ((max - min) > meanPrecision && count <= 0) {
                // System.out.println("Diff: " + (max - min));
                // System.out.println("Eps: " + meanPrecision);
                // System.out.println("Count: " + count);
                System.err.println("Verifica ergodicità arrestata per valore di media campionaria non sufficientemente costante");

                break;
            }
            // continua ad essere ergodico
            else {
                // salvo il tempo dal quale sta continuando a valere la convergenza
                // System.out.println("STO CONTINUANDOOOOOOO");
                start = times[i];
                count--;
                // System.out.println("start: "+start);
                // System.out.println("count: "+ count);
            }

        }
        System.err.println("Sistema probabilmente ergodico a partire dall'istante: " + start);
        return;
    }
}
