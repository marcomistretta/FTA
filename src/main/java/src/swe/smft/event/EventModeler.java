package src.swe.smft.event;

import java.util.ArrayList;

// TODO deve essere un singleton
// TODO guardare se è Builder/Factory
// TODO daje ema fallo te
public class EventModeler {

    static private EventModeler eventFactory = null;

    public Event createBasicEvent(float lambda, float mu, boolean status) {
        BasicEvent b = new BasicEvent(lambda, mu, status);
        return b;
    }

    public static EventModeler getInstance() {
        if(eventFactory != null) return eventFactory;
        eventFactory = new EventModeler();
        return eventFactory;
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

}



