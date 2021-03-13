package src.swe.smft.event;

import java.util.ArrayList;

public class TreeManager {

    private ArrayList<BasicEvent> basicEvents = new ArrayList<>();

    private IntermediateEvent topEvent;

    public Event getTopEvent() {
        return topEvent;
    }

    public void setTopEvent(IntermediateEvent topEvent) {
        this.topEvent = topEvent;
    }

    public TreeManager() {
    }

    public void nextToggle() {
        ArrayList<Float> pList = new ArrayList<>();
        pList = calculateP(pList);
        int choose = sample(pList);
        basicEvents.get(choose).toggle();

        /* just for debugging */
        //for(BasicEvent b : basicEvents)
        //    System.out.println(b.isWorking());
    }

    public ArrayList<Float> calculateP(ArrayList<Float> pList) {
        for (BasicEvent b : basicEvents) {
            pList.add(b.getP());
        }
        return pList;
    }

    public int sample(ArrayList<Float> pList) {
        /* will return -1 in case of error */
        int choose = -1;

        float rand = (float) Math.random();
        float sum = 0;
        for (float p : pList) {
            //System.err.println("Pcalc: " + p);
            sum += p;
        }
        //System.err.println("sum: " + sum);
        float sample = rand * sum;
        //System.err.println("sample: " + sample);
        float adder = 0;
        for (int i = 0; i < basicEvents.size(); i++) {
            if (sample <= (pList.get(i) + adder)) {
                choose = i;
                break;
            } else
                adder += pList.get(i);
        }
        //System.err.println("stato scelto: " + choose);
        return choose;
    }

//    /* ritona false se non ha inizializzato con successo */
//    public boolean initialize(ArrayList<Float> lambdas, ArrayList<Float> mus, ArrayList<Boolean> status) {
//        if (lambdas.size() == mus.size() && mus.size() == status.size() && status.size() == basicEvents.size()) {
//            int n = lambdas.size();
//            for (int i = 0; i < n; i++) {
//                basicEvents.get(i).setLambda(lambdas.get(i));
//                basicEvents.get(i).setMu(mus.get(i));
//                basicEvents.get(i).setStatus(status.get(i));
//            }
//            return true;
//        } else
//            /* problema di inconsistenza dimensionale */
//            return false;
//    }

    public ArrayList<Boolean> getStatus() {
        ArrayList<Boolean> status = new ArrayList<>();
        for (BasicEvent b : basicEvents)
            status.add(b.isWorking());
        return status;
    }

    public void addBasicEvent(BasicEvent b) {
        basicEvents.add(b);

    }

    public void clearTree() {
        basicEvents = null;
        topEvent = null;

    }

}

