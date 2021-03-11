package src.swe.smft.memory;

import src.swe.smft.utilities.Pair;
import src.swe.smft.utilities.Triplet;

import java.util.*;

public class DataCentre {
    //simulazioni>>campionamenti(tempo, top, base)
    private ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>> simulationResults;

    public DataCentre() {
        simulationResults = new ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>>();
    }

    public void appendData(ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> entry) {
        simulationResults.add(entry);
    }

    public void clean() {
        if (!simulationResults.isEmpty())
            simulationResults = new ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>>();
    }

    public ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedData(float quantum, float maxTime) {

        /**
         * (Simulazioni[simulazione])[quanto]
         */
        //it's ok, maxTime := k é quantum
        int numberOfSamples = (int) (maxTime / quantum) + 1;

        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantized =
                new ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>>();
        for (ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation : simulationResults) {
            //every sim
            quantized.add(new ArrayList<Pair<Boolean, ArrayList<Boolean>>>());
            int count = 0;
            ArrayList<Float> debug = new ArrayList<Float>();
            for(float step = 0f; step <= maxTime; step += quantum) {
            //quanto attuale
                for (Triplet<Float, Boolean, ArrayList<Boolean>> data : simulation) {
                    //every sample
                    if (data.getElement1() > step) {
                        quantized.get(quantized.size() - 1).add(new Pair(data.getElement2(), data.getElement3()));
                        count -= -1;
                        debug.add(step);
                        break;
                    }
                }
            }
            //correzione necessaria se non esistono più campioni dopo un certo quanto, riempie i quanti rimasti
            //con l'ultimo istante temporale
            int l = numberOfSamples - quantized.get(quantized.size() - 1).size();
            while (l > 0) {
                quantized.get(quantized.size() - 1).add(new Pair(simulation.get(simulation.size() - 1).getElement2(),
                        simulation.get(simulation.size() - 1).getElement3()));
                l--;
            }
        }
        return quantized;
    }
}

