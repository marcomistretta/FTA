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
        // TODO MODEL GRAPH
        GraphBuilder.addNode(e);
        return e;
    }

    // TODO dimmi che ne pensi
    public Event createRandomBasicEvent() {
        float lambda = (float) Math.random();
        float mu = (float) Math.random();
        boolean status;
        status = Math.random() >= 0.5;
        BasicEvent e = new BasicEvent(lambda, mu, status);
        // TODO MODEL GRAPH
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
        // TODO MODEL GRAPH
        GraphBuilder.addNodeAndEdges(i);
        return i;
    }

    // TODO dimmi che ne pensi

    public Event createRandomIntermediateEvent(List<Event> children) {
        IntermediateEvent i;
        String opz;
        if (Math.random() <= 0.33) {
            opz = "AND";
            i = new AndGate(children, opz);
        } else if (Math.random() <= 0.66) {
            opz = "OR";
            i = new OrGate(children, opz);
        }
        /* (opz == 'K') */
        else {
            if (Math.random() <= 0.33)
                opz = "0";
            else if (Math.random() <= 0.66)
                opz = "1";
            else opz = "2";
            int k = Integer.parseInt(opz);
            if (k > children.size()) {
                k = children.size();
                opz = String.valueOf(k);
            }
            i = new KNGate(children, k, opz);
        }
        // TODO MODEL GRAPH
        GraphBuilder.addNodeAndEdges(i);
        return i;

    }

}



