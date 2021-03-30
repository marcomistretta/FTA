package src.swe.smft.event;

import java.util.ArrayList;

public class TreeManager {

    private ArrayList<BasicEvent> basicEvents = new ArrayList<>();

    private IntermediateEvent topEvent;

    private float omega;

    public void setTopEvent(IntermediateEvent topEvent) {
        this.topEvent = topEvent;
    }

    public TreeManager() {
    }

    public Event getTopEvent() {
        return topEvent;
    }

    public void nextToggle() {
        ArrayList<Float> pList = new ArrayList<>();
        // calcola le singolo probabilità per ciascun BasicEvent
        // di essere lui a mutare stato di funzionamento
        pList = calculateP(pList);
        // sceglie il BasicEvent che dovrà cambiare stato di funzionamento
        int choose = sample(pList);
        omega += basicEvents.get(choose).toggle();
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

        for (float p : pList)
            sum += p;

        float sample = rand * sum;
        float adder = 0;

        for (int i = 0; i < basicEvents.size(); i++) {
            if (sample <= (pList.get(i) + adder)) {
                choose = i;
                break;
            } else
                adder += pList.get(i);
        }
        return choose;
    }

    public ArrayList<Boolean> getStatus() {
        ArrayList<Boolean> status = new ArrayList<>();
        for (BasicEvent b : basicEvents)
            status.add(b.isWorking());
        return status;
    }

    public void addBasicEvent(BasicEvent b) {
        basicEvents.add(b);
        omega += b.getP();

    }

    public void reset() {
        for (BasicEvent child : basicEvents) {
            omega += child.reset();
        }
    }

    public void randomReset() {
        for (BasicEvent child : basicEvents) {
            omega += child.randomReset();
        }
    }

    // cancella tutto l'albero
    public void clearTree() {
        basicEvents = null;
        topEvent = null;

    }

    public ArrayList<BasicEvent> getBasicEvents() {
        return basicEvents;
    }

    public ArrayList<Boolean> getBasicEventsStatus() {
        ArrayList<Boolean> basicEventsStatus = new ArrayList<>();
        for (BasicEvent b : basicEvents) {
            basicEventsStatus.add(b.isWorking());
        }
        return basicEventsStatus;
    }

    public void setBasicEventsStatus(ArrayList<Boolean> basicEventsStatus) {
        for (int i = 0; i < basicEvents.size(); i++) {
            basicEvents.get(i).setStatus(basicEventsStatus.get(i));
        }
        updateOmega();
    }

    public float getOmega() {
        return omega;
    }

    public void updateOmega(){
        omega = 0;
        for(BasicEvent b : basicEvents)
            omega += b.getP();
    }

}

