package src.swe.smft.utilities;

import java.util.ArrayList;

public class UnquantizedSample {
    private boolean topStatus;
    private ArrayList<Boolean> leavesStatus;
    private float time;

    public UnquantizedSample(float t, boolean top, ArrayList<Boolean> l) {
        time = t;
        topStatus = top;
        leavesStatus = l;
    }

    public boolean getTopStatus() {
        return topStatus;
    }

    public ArrayList<Boolean> getLeavesStatus() {
        return leavesStatus;
    }

    public float getTime() {
        return time;
    }

}
