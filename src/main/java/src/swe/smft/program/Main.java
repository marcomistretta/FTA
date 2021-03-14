package src.swe.smft.program;

import src.swe.smft.event.*;
import src.swe.smft.memory.DataCentre;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        TreeManager tm = new TreeManager();
        EventModeler modeler = EventModeler.getInstance();
        float maxTime = 10000;
        DataCentre dc = new DataCentre();
        Simulator sim;

        boolean premadeModel = false;

        if (premadeModel) {
            float lambdaA = 0.7f;
            float muA = 0.3f;
            boolean statusA = true;
            Event A = modeler.createBasicEvent(lambdaA, muA, statusA);
            tm.addBasicEvent((BasicEvent) A);

            float lambdaB = 0.4f;
            float muB = 0.6f;
            boolean statusB = true;
            Event B = modeler.createBasicEvent(lambdaB, muB, statusB);
            tm.addBasicEvent((BasicEvent) B);


            List<Event> children = List.of(A, B);
            String opz = "OR";
            Event C = modeler.createIntermediateEvent(children, opz);
            tm.setTopEvent((IntermediateEvent) C);

            sim = new Simulator(maxTime, C, tm);
        } else {
            // TODO non funziona sempre...perchè?...
            /**
             * la funzione che fa il plot del grafo fa crashare il programma quando non sa chi deve essere collegato a chi:
             *  ovvero quando ci sono due nodi che hanno la stessa label
             *  SECONDO ME: serve la Stringa Id da mettere dentro Basic e Intermediate
             *  OPPURE un int statico dentro entrambi (tanto a parità di numero la label sarà diversa)
             */
            int nBasic = 4;
            // int nIntermediate = nBasic/2;
            for (int i = 0; i < nBasic; i++)
                tm.addBasicEvent((BasicEvent) modeler.createRandomBasicEvent());
            List<Event> topChildren = new ArrayList<>();
            List<Event> children = new ArrayList<>();
            for (int j = 0; j < nBasic / 2; j++) {
                for (int k = j * 2; k < j * 2 + 2; k++) {
                    children.add(tm.getBasicEvents().get(k));
                    System.err.println(k);
                }
                topChildren.add(modeler.createRandomIntermediateEvent(children));
            }

            Event topEvent = modeler.createRandomIntermediateEvent(topChildren);
            tm.setTopEvent((IntermediateEvent) topEvent);

            sim = new Simulator(maxTime, topEvent, tm);
        }


        Analyzer anal = new Analyzer(sim, dc);
        int N = 100;
        int quantum = 50;

        boolean defineCI = true;
        boolean verifyErgodic = true;
        if (defineCI) {
            float alpha = 0.05f;
            anal.defineCI(N, alpha, quantum);
        }
        if (verifyErgodic) {
            float eps = 0.5f;
            anal.verifyErgodic(N, quantum, eps);
        }

    }
}