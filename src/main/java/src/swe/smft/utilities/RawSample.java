package src.swe.smft.utilities;

import java.util.ArrayList;

public class RawSample {
    private Float time;
    private Boolean topStatus;
    private ArrayList<Boolean> leavesStatus;

    public RawSample(Float t, Boolean top, ArrayList<Boolean> l) {
        time = t;
        topStatus = top;
        leavesStatus = l;
    }

    public Float getTime() {
        return time;
    }

    public Boolean getTopStatus() {
        return topStatus;
    }

    public ArrayList<Boolean> getLeavesStatus() {
        return leavesStatus;
    }
}
