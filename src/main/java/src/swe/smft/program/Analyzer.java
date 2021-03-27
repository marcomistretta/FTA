package src.swe.smft.program;

import src.swe.smft.plot.HarryPlotter;
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
            Timer.estimatedTime(N, start, i, "Simulazioni per calcolo Intervalli di Confidenza");
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

    public void verifyErgodic(int N, float quantum, double meanPrecision, double varPrecision) {
        dc.clear();

        double start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Timer.estimatedTime(N, start, i, "Simulazioni per verifica Ergodicità");
            dc.appendData(s.simulation(true));
        }

        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults =
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


        // TODO dimmi che ne pensi
        if(true) {
            ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> temp = new ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>>();
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
        }
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
