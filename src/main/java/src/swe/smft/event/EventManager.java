package src.swe.smft.event;

import java.util.ArrayList;

/* concrete observer class */
public class EventManager extends MyObserver {
    /* TODO check */
    /* start Observer stuff */
    private EventModeler concreteSubject;

    public EventManager(EventModeler m) {
        this.concreteSubject = m;
        this.concreteSubject.attach(this);
    }

    @Override
    public void update() {
        /* TODO check, i make it a local variable pt1 */
        BasicEvent observerState = concreteSubject.getSubjectState();
        basicEvents.add(observerState);
    }

    /* TODO check, i make it a local variable pt2 */
    /* private BasicEvent observerState; */
    /* end Observer stuff */

    /* start EventManager stuff */
    private ArrayList<BasicEvent> basicEvents;

    public EventManager(ArrayList<BasicEvent> basicEvents) {
        this.basicEvents = basicEvents;
    }

    public void nextToggle() {
        ArrayList<Float> pList = calculateP();
        int choose = sample(pList);
        basicEvents.get(choose).toggle();

    }

    public ArrayList<Float> calculateP() {
        ArrayList<Float> pList = new ArrayList<Float>();
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
            sum += p;
        }
        float sample = rand * sum;
        for (int i = 0; i <= basicEvents.size(); i++) {
            float adder = 0;
            if (sample <= (pList.get(i) + adder))
                choose = i;
            else
                adder += pList.get(i);
        }

        return choose;
    }

    /* TODO will take a list of triplets (lambda, mu, status) to initialize basic events */
    public void initialize() {
    }
}
