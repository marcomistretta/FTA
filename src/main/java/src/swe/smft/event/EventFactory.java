package src.swe.smft.event;

import src.swe.smft.harryplotter.HarryPlotter;

import java.util.List;

import static org.apache.commons.math3.util.Precision.round;

public class EventFactory {

    // tiene conto di quanti nodi ci sono nel modello
    private static int count = 0;

    static private EventFactory eventFactory = null;

    private EventFactory(){}

    public static EventFactory getInstance() {
        if (eventFactory != null) return eventFactory;
        eventFactory = new EventFactory();
        return eventFactory;
    }

    public Event createBasicEvent(float lambda, float mu, boolean status) {
        BasicEvent e = new BasicEvent(lambda, mu, status, ++count);
        HarryPlotter.addNode(e);
        return e;
    }

    public Event createRandomBasicEvent() {
        float lambda = round((float) Math.random(), 2);
        float mu = round((float) Math.random(), 2);
        boolean status = Math.random() >= 0.5;
        BasicEvent e = new BasicEvent(lambda, mu, status, ++count);
        HarryPlotter.addNode(e);
        return e;
    }


    public Event createIntermediateEvent(List<Event> children, String opz) {
        IntermediateEvent i;
        if (opz.equals("AND"))
            i = new AndGate(children, opz, ++count);
        else if (opz.equals("SEQAND"))
            i = new SeqAnd(children, opz, ++count);
        else if (opz.equals("OR"))
            i = new OrGate(children, opz, ++count);
        // opz == "K" con K numero intero
        else {
            // fixme
            int k = Integer.parseInt(opz);
            if (k > children.size()) {
                k = children.size();
                opz = String.valueOf(k);
            }
            i = new KNGate(children, k, opz, ++count);
        }
        HarryPlotter.addNodeAndEdges(i);
        return i;
    }


    public Event createRandomIntermediateEvent(List<Event> children) {
        IntermediateEvent i;
        double selection = Math.random();
        if (selection <= 0.33) {
            if(Math.random() > .9)
                i = new SeqAnd(children, "SEQAND", ++count);
            else i = new AndGate(children, "AND", ++count);
        } else if (selection <= 0.66) {
            i = new OrGate(children, "OR", ++count);
        }
        // opz == K
        else {
            // fixme
            int k = (int) (Math.random() * children.size() + 1);
            i = new KNGate(children, k, String.valueOf(k), ++count);
        }
        HarryPlotter.addNodeAndEdges(i);
        return i;

    }

}



