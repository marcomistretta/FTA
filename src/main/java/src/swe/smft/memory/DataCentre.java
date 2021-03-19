package src.swe.smft.memory;

import src.swe.smft.utilities.Pair;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.Triplet;

import java.util.ArrayList;

public class DataCentre {
    // simulazioni >> campionamenti(tempo, topEvent, basicEvents)
    private ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>> simulationResults;
    private ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults;

    public DataCentre() {
        simulationResults = new ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>>();
    }

    public void appendData(ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> entry) {
        simulationResults.add(entry);
    }

    // calcolare quantizedData è oneroso: la prima volta che lo calcola lo conserva (cancellare i dati lo forza a ricalcolare)
    public ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedData(float quantum, float maxTime) {

        // (Simulazioni[simulazione])[quanto]

        // it's ok, maxTime := k é quantum
        if(quantizedResults == null) {
            int N = simulationResults.size();
            int numberOfSamples = (int) (maxTime / quantum) + 1;
            quantizedResults = new ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>>();
            double start = System.currentTimeMillis();
            //for (ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation : simulationResults) {
            for(int i = 0; i < N; i++) {
                Timer.estimatedTime(N, start, i, "Quantizzazione dei risultati");


                // every sim
                quantizedResults.add(new ArrayList<>());
                for (float step = 0f; step <= maxTime; step += quantum) {
                    // quanto attuale
                    for (Triplet<Float, Boolean, ArrayList<Boolean>> data : simulationResults.get(i)) {
                        //every sample
                        //if step > time ==> nextTime
                        if (data.getElement1() >= step) {
                            quantizedResults.get(quantizedResults.size() - 1).add(new Pair<>(data.getElement2(), data.getElement3()));
                            break;
                        }
                    }
                }
                // correzione necessaria se non esistono più campioni dopo un certo quanto, riempie i quanti rimasti
                // con l'ultimo istante temporale
                int l = numberOfSamples - quantizedResults.get(quantizedResults.size() - 1).size();
                while (l > 0) {
                    quantizedResults.get(quantizedResults.size() - 1).add(new Pair<>(simulationResults.get(i).get(simulationResults.get(i).size() - 1).getElement2(),
                            simulationResults.get(i).get(simulationResults.get(i).size() - 1).getElement3()));
                    l--;
                }
            }
        }
        return quantizedResults;
    }


    public void clear() {
        if (!simulationResults.isEmpty()) {
            simulationResults = new ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>>();
            quantizedResults = null;
        }
    }
}

