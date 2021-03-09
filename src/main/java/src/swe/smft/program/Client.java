package src.swe.smft.program;

import src.swe.smft.memory.DataCentre;

public class Client {
    private DataCentre dataLogger;
    private Simulator simulator;

    public Client() {
        dataLogger = new DataCentre();
    }

}
