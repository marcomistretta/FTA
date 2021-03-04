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

//public class DataCentre {
//
//    private ArrayList<ArrayList<Boolean>> topStatus;
//    private ArrayList<ArrayList<ArrayList<Boolean>>> leavesStatus;
//    private ArrayList<ArrayList<Float>> timeFrames;
//
//    private ArrayList<HashMap<Float, Pair<Boolean, ArrayList<Boolean>>>> simulations;
//    private ArrayList<Float> plotTimes;
//
//    public DataCentre(){
//        topStatus = new ArrayList<ArrayList<Boolean>>();
//        leavesStatus = new ArrayList<ArrayList<ArrayList<Boolean>>>();
//        timeFrames = new ArrayList<ArrayList<Float>>();
//
//        plotTimes = new ArrayList<Float>();
//        simulations = new ArrayList<HashMap<Float, Pair<Boolean, ArrayList<Boolean>>>>();
//    }
//
//    public void appendData(ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulationResult) {
//        ArrayList<Float> time = new ArrayList<Float>();
//        ArrayList<ArrayList<Boolean>> leaves = new ArrayList<ArrayList<Boolean>>();
//        ArrayList<Boolean> root = new ArrayList<Boolean>();
//
//        for(Triplet<Float, Boolean, ArrayList<Boolean>> entry: simulationResult) {
//            time.add(entry.getValue0());
//            root.add(entry.getValue1());
//            leaves.add(entry.getValue2());
//        }
//        topStatus.add(root);
//        leavesStatus.add(leaves);
//        timeFrames.add(time);
//
//    }
//
//    public void appendData(HashMap<Float, Pair<Boolean, ArrayList<Boolean>>> simulation) {
//        simulations.add(simulation);
//        for (Float t: simulation.keySet()) {
//            if(!plotTimes.contains(t)) plotTimes.add(t);
//        }
//    }
//
//    public static Pair<Boolean, ArrayList<Boolean>>
//    getRightPair(HashMap<Float, Pair<Boolean, ArrayList<Boolean>>> hash, Float key) {
//        if(hash.containsKey(key)) return hash.get(key);
//        Float nearest = 0f;
//        for (Float t: hash.keySet()) if(t > nearest && t < key) nearest = t;
//        return hash.get(nearest);
//    }
//}
