package src.swe.smft.memory;

import src.swe.smft.utilities.QuantizedSample;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.RawSample;

import java.util.ArrayList;

public class DataCentre {
    // simulazioni >> campionamenti(tempo, topEvent, basicEvents)
    private ArrayList<ArrayList<RawSample>> simulationResults;

    public DataCentre() {
        simulationResults = new ArrayList<ArrayList<RawSample>>();
    }

    public void appendData(ArrayList<RawSample> entry) {
        simulationResults.add(entry);
    }

    // calcolare quantizedData è oneroso: la prima volta che lo calcola lo conserva (cancellare i dati lo forza a ricalcolare)
    public ArrayList<ArrayList<QuantizedSample>> quantizedData(float quantum, float maxTime) {

        // (Simulazioni[simulazione])[quanto]
        ArrayList<ArrayList<QuantizedSample>> quantizedResults = new ArrayList<>();
        // it's ok, maxTime := k é quantum
        int N = simulationResults.size();
        int numberOfSamples = (int) (maxTime / quantum) + 1;
        double start = System.currentTimeMillis();
        //for (ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation : simulationResults) {
        for(int i = 0; i < N; i++) {
            Timer.estimatedTime(N, start, i, "Quantizzazione dei risultati");


            // every sim
            quantizedResults.add(new ArrayList<>());
            for (float step = 0f; step <= maxTime; step += quantum) {
                // quanto attuale
                for (RawSample data : simulationResults.get(i)) {
                    //every sample
                    //if step > time ==> nextTime
                    if (data.getTime() >= step) {
                        quantizedResults.get(quantizedResults.size() - 1).add(new QuantizedSample(data.getTopStatus(), data.getLeavesStatus()));
                        break;
                    }
                }
            }
            // correzione necessaria se non esistono più campioni dopo un certo quanto, riempie i quanti rimasti
            // con l'ultimo istante temporale
            int l = numberOfSamples - quantizedResults.get(quantizedResults.size() - 1).size();
            while (l > 0) {
                quantizedResults.get(quantizedResults.size() - 1).add(new QuantizedSample(simulationResults.get(i).get(simulationResults.get(i).size() - 1).getTopStatus(),
                        simulationResults.get(i).get(simulationResults.get(i).size() - 1).getLeavesStatus()));
                l--;
            }
        }
        return quantizedResults;
    }


    public void clear() {
        if (!simulationResults.isEmpty())
            simulationResults = new ArrayList<>();
    }
}

