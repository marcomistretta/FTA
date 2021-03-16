package src.swe.smft.event;

import src.swe.smft.graph.GraphBuilder;

import java.util.List;

import static org.apache.commons.math3.util.Precision.round;

public class EventModeler {

    // TODO davanti ad ogni label c'è un identificatore
    private static int count = 0;


    static private EventModeler eventFactory = null;

    public static EventModeler getInstance() {
        if (eventFactory != null) return eventFactory;
        eventFactory = new EventModeler();
        return eventFactory;
    }

    public Event createBasicEvent(float lambda, float mu, boolean status) {
        BasicEvent e = new BasicEvent(lambda, mu, status, ++count);
        GraphBuilder.addNode(e);
        return e;
    }

    public Event createRandomBasicEvent() {
        float lambda = round((float) Math.random(), 2);
        float mu = round((float) Math.random(), 2);
        boolean status = Math.random() >= 0.5;
        BasicEvent e = new BasicEvent(lambda, mu, status, ++count);
        GraphBuilder.addNode(e);
        return e;
    }


    public Event createIntermediateEvent(List<Event> children, String opz) {
        IntermediateEvent i;
        if (opz.equals("AND"))
            i = new AndGate(children, opz, ++count);
        else if (opz.equals("OR"))
            i = new OrGate(children, opz, ++count);
            /* (opz == 'K') */
        else {
            int k = Integer.parseInt(opz);
            if (k > children.size()) {
                k = children.size();
                opz = String.valueOf(k);
            }
            i = new KNGate(children, k, opz, ++count);
        }
        GraphBuilder.addNodeAndEdges(i);
        return i;
    }


    public Event createRandomIntermediateEvent(List<Event> children) {
        IntermediateEvent i;
        if (Math.random() <= 0.33) {
            i = new AndGate(children, "AND", ++count);
        } else if (Math.random() <= 0.66) {
            i = new OrGate(children, "OR", ++count);
        }
        /* (opz == 'K') */
        else {
            int k = (int) (Math.random() * children.size() + 1);
            i = new KNGate(children, k, String.valueOf(k), ++count);
        }
        GraphBuilder.addNodeAndEdges(i);
        return i;

    }

}



