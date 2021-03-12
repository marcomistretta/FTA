package src.swe.smft.event;

import java.util.ArrayList;


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

    public Event createIntermediateEvent(ArrayList<Event> children, String opz) {
        if (opz.equals("A"))
            return new AndGate(children);
        if (opz.equals("O"))
            return new OrGate(children);
            /* (opz == 'K') */
        else {
            int k = Integer.parseInt(opz);
            if(k > children.size())
                k = children.size();
            return new KNGate(children, k);
        }
    }

}



