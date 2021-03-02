package src.swe.smft.memory;

import java.util.*;

public class DataCentre {

    //TODO fa tutto schifo, rifaccio uml

    private ArrayList<ArrayList<Boolean>> results;
    private ArrayList<ArrayList<Float>> timeFrames;

    public DataCentre(){
        results = new ArrayList<ArrayList<Boolean>>();
        timeFrames = new ArrayList<ArrayList<Float>>();
    }

    public void appendData(ArrayList<Boolean> result, ArrayList<Float> times) {
        results.add(result);
        timeFrames.add(times);
    }

    private void sanitizeData() {
        ArrayList<Float> commonTimes = new ArrayList<Float>();
        for(ArrayList<Float> time : timeFrames) {
            for(Float f : time)
                if(!commonTimes.contains(f))
                    commonTimes.add(f);
        }
    }

}
