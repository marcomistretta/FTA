package src.swe.smft.utilities;

import java.util.ArrayList;

public class QuantizedSample {
    private boolean topStatus;
    private ArrayList<Boolean> leavesStatus;

    public QuantizedSample(boolean t, ArrayList<Boolean> l) {
        topStatus = t;
        leavesStatus = l;
    }

    public boolean getTopStatus() {
        return topStatus;
    }

    public ArrayList<Boolean> getLeavesStatus() {
        return leavesStatus;
    }

}
