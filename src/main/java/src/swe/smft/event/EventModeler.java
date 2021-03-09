package src.swe.smft.event;

import java.util.ArrayList;

public class EventModeler {

    public BasicEvent createBasicEvent(float lambda, float mu, boolean status) {
        BasicEvent b = new BasicEvent(lambda, mu, status);
        eventManager.addBasicEvent(b);
        return b;
    }

    private EventManager eventManager;



    public EventModeler(EventManager em) {
        this.eventManager = em;
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

    // TODO agli intermediate event ci penserà il garbage collector di java
    public void clearTree() {
        eventManager.clearBasicEvent();
    }
}



