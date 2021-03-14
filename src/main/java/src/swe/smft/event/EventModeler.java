package src.swe.smft.event;

import src.swe.smft.graph.GraphBuilder;

import java.util.List;

public class EventModeler {

    static private EventModeler eventFactory = null;

    public static EventModeler getInstance() {
        if (eventFactory != null) return eventFactory;
        eventFactory = new EventModeler();
        return eventFactory;
    }

    public Event createBasicEvent(float lambda, float mu, boolean status) {
        BasicEvent e = new BasicEvent(lambda, mu, status);
        GraphBuilder.addNode(e);
        return e;
    }
    public BasicEvent createRandomBasicEvent() {
        float lambda = (float) Math.random();
        float mu = (float) Math.random();
        boolean status = Math.random() >= 0.5;
        BasicEvent e = new BasicEvent(lambda, mu, status);
        GraphBuilder.addNode(e);
        return e;
    }


    public Event createIntermediateEvent(List<Event> children, String opz) {
        IntermediateEvent i;
        if (opz.equals("AND"))
            i = new AndGate(children, opz);
        else if (opz.equals("OR"))
            i = new OrGate(children, opz);
            /* (opz == 'K') */
        else {
            int k = Integer.parseInt(opz);
            if (k > children.size()) {
                k = children.size();
                opz = String.valueOf(k);
            }
            i = new KNGate(children, k, opz);
        }
        GraphBuilder.addNodeAndEdges(i);
        return i;
    }


    public Event createRandomIntermediateEvent(List<Event> children) {
        IntermediateEvent i;
        if (Math.random() <= 0.33) {
            i = new AndGate(children, "AND");
        } else if (Math.random() <= 0.66) {
            i = new OrGate(children, "OR");
        }
        /* (opz == 'K') */
        else {
            int k;
            if (Math.random() <= 0.33) k = 0;
                //opz = "0";
            else if (Math.random() <= 0.66) k = 1;
                //opz = "1";
            else k = 2;//opz = "2";
            if (k > children.size())
                k = children.size();
            i = new KNGate(children, k, String.valueOf(k));
        }
        GraphBuilder.addNodeAndEdges(i);
        return i;

    }

}



