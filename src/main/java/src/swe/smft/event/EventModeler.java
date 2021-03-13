package src.swe.smft.event;

import java.util.ArrayList;

public class EventModeler {

    static private EventModeler eventFactory = null;

    public Event createBasicEvent(float lambda, float mu, boolean status) {
        return new BasicEvent(lambda, mu, status);
    }

    public static EventModeler getInstance() {
        if(eventFactory != null) return eventFactory;
        eventFactory = new EventModeler();
        return eventFactory;
    }

    public Event createIntermediateEvent(ArrayList<Event> children, String opz) {
        if (opz.equals("AND"))
            return new AndGate(children, opz);
        if (opz.equals("OR"))
            return new OrGate(children, opz);
            /* (opz == 'K') */
        else {
            int k = Integer.parseInt(opz);
            if (k > children.size()) {
                k = children.size();
                opz = String.valueOf(k);
            }
            return new KNGate(children, k, opz);
        }
    }

}



