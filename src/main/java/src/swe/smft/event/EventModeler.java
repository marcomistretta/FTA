/* TODO check */
/* we assume that every event works initially in a default condition */
package src.swe.smft.event;

import java.util.ArrayList;

/* a concrete subject class */
public class EventModeler extends MySubject {
    /* start Subject stuff */
    /* TODO state, get, set */
    private BasicEvent subjectState;

    public BasicEvent getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(BasicEvent subjectState) {
        this.subjectState = subjectState;
    }
    /* end Subject stuff */

    /* start Modeler stuff */
    public BasicEvent createBasicEvent(float lambda, float mu, boolean status) {
        subjectState = new BasicEvent(lambda, mu, status);
        notifyAllObservers();
        return new BasicEvent(lambda, mu, status);
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



