package src.swe.smft.memory;

import src.swe.smft.utilities.Pair;
import src.swe.smft.utilities.Triplet;

import java.util.*;

public class DataCentre {
        //simulazioni>>campionamenti(tempo, top, base)
private ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>> simulationResults;

public DataCentre() {
    simulationResults = new ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>>();
    simulationResults.add(new ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>());
}

public void appendData(ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> entry) {
    simulationResults.add(entry);
}

public void clean() {
    if(!simulationResults.isEmpty())
        simulationResults = new ArrayList<ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>>();
}

public ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedData(float quantum) {
    /**
     * (Simulazioni[simulazione])[quanto]
     */
    ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantized =
            new ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>>();
    for (ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation : simulationResults) {
        quantized.add(new ArrayList<Pair<Boolean, ArrayList<Boolean>>>());
        float step = 0;
        for (Triplet<Float, Boolean, ArrayList<Boolean>> data : simulation) {
            if (step < data.getElement1())
                continue;
            quantized.get(quantized.size() - 1).add(
                    new Pair<Boolean, ArrayList<Boolean>>(data.getElement2(), data.getElement3()));
            step += quantum;
        }
        //maxTime value
//        quantized.get(quantized.size() - 1).add(new Pair<Boolean, ArrayList<Boolean>>(
//                simulation.get(simulation.size() - 1).getElement2(),
//                simulation.get(simulation.size() - 1).getElement3()));
    }
    return quantized;
}
}
