package src.swe.smft.program;

import src.swe.smft.memory.DataCentre;
import src.swe.smft.utilities.Pair;

import java.util.ArrayList;

public class Client {
    private DataCentre dataLogger;
    private Simulator simulator;

    public Client() {
        dataLogger = new DataCentre();
    }

    public void getConfidenceIndex(float quantum) {
        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> qd = dataLogger.quantizedData(quantum);
    }

    public void computeErgodicity() {

    }
}
