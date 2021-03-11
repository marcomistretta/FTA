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

    public Event createIntermediateEvent(ArrayList<Event> children, char opz) {
        if (opz == 'A')
            return new AndGate(children);
        if (opz == 'O')
            return new OrGate(children);
            /* (opz == 'K') */
        else {
            //TODO converti opz da char a str: com'è ora non può avere K > 9
            int k = Character.getNumericValue(opz);
            return new KNGate(children, k);
        }
    }

}



