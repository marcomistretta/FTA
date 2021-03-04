package src.swe.smft.event;
import java.util.ArrayList;

/* a concrete subject class */
public class EventModeler extends MySubject {
    /* start Subject stuff */
    /* TODO state, get, set */
    private BasicEvent lastAddedBasicEvent;

    public BasicEvent getLastAddedBasicEvent() {
        return lastAddedBasicEvent;
    }

    public void setLastAddedBasicEvent(BasicEvent lastAddedBasicEvent) {
        this.lastAddedBasicEvent = lastAddedBasicEvent;
    }
    /* end Subject stuff */

    /* start Modeler stuff */
    public BasicEvent createBasicEvent(float lambda, float mu, boolean status) {
        lastAddedBasicEvent = new BasicEvent(lambda, mu, status);
        notifyAllObservers();
        return lastAddedBasicEvent;
    }

    public Event createIntermediateEvent(ArrayList<Event> children, char opz) {
        if (opz == 'A')
            return new AndGate(children);
        if (opz == 'O')
            return new OrGate(children);
            /* (opz == 'K') */
        else {
            /* TODO check */
            /* int k = (int) (Math.random() * (children.size() - 1) + 1); */
            int k = Character.getNumericValue(opz);
            return new KNGate(children, k);
        }
    }
    /* end Modeler stuff */
}



