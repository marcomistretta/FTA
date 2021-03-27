package src.swe.smft.utilities;

import java.util.ArrayList;

public class QuantizedSample {
    private Boolean topStatus;
    private ArrayList<Boolean> leavesStatus;

    public QuantizedSample(Boolean t, ArrayList<Boolean> l) {
        topStatus = t;
        leavesStatus = l;
    }

    public Boolean getTopStatus() {
        return topStatus;
    }

    public ArrayList<Boolean> getLeavesStatus() {
        return leavesStatus;
    }

}
