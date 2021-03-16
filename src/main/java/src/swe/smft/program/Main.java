package src.swe.smft.program;

import src.swe.smft.event.*;
import src.swe.smft.graph.GraphBuilder;
import src.swe.smft.memory.DataCentre;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {
        GraphBuilder gb = new GraphBuilder();
        TreeManager tm = new TreeManager();
        EventModeler modeler = EventModeler.getInstance();
        float maxTime = 10000;
        DataCentre dc = new DataCentre();
        Simulator sim;

        boolean premadeModel = true;
        int nBasic = 10;

        if (premadeModel || nBasic <= 2) {
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

            float lambdaC = 0.7f;
            float muC = 0.3f;
            boolean statusC = true;
            Event C = modeler.createBasicEvent(lambdaC, muC, statusC);
            tm.addBasicEvent((BasicEvent) C);

            float lambdaD = 0.4f;
            float muD = 0.6f;
            boolean statusD = true;
            Event D = modeler.createBasicEvent(lambdaD, muD, statusD);
            tm.addBasicEvent((BasicEvent) D);


            List<Event> childrenC = List.of(A, B);
            List<Event> childrenD = List.of(C, D);
            String opzC = "AND";
            String opzD = "OR";
            Event E = modeler.createIntermediateEvent(childrenC, opzC);
            Event F = modeler.createIntermediateEvent(childrenD, opzD);

            List<Event> childrenTop = List.of(E, F);
            String opzTop = "AND";
            Event top = modeler.createIntermediateEvent(childrenTop, opzTop);
            tm.setTopEvent((IntermediateEvent) top);

            sim = new Simulator(maxTime, C, tm);
        } else {
            // TODO
            // potresti per favore fare in modo che tutti i basic event che non vengono
            // selezionati come figli degli intermediate, siano ALMENO figli del top event
            // perchè sennò risultano nodi inutili senza padri

            for (int i = 0; i < nBasic; i++) {
                BasicEvent e = modeler.createRandomBasicEvent();
                tm.addBasicEvent(e);
            }
            List<Event> topChildren = new ArrayList<>();
            for (int j = 0; j < nBasic / 2; j++) {
                List<Event> children = new ArrayList<>();
                for (int k = 0; k < nBasic / 2; k++) {
                    int choose = (int) (Math.random() * nBasic);
                    children.add(tm.getBasicEvents().get(choose));
                }

                topChildren.add(modeler.createRandomIntermediateEvent(children));
            }
            // dato che con topEvent != da AND la simulazione non risulta interessante
            //Event topEvent = modeler.createRandomIntermediateEvent(topChildren);
            Event topEvent = modeler.createIntermediateEvent(topChildren, "AND");

            tm.setTopEvent((IntermediateEvent) topEvent);

            sim = new Simulator(maxTime, topEvent, tm);
        }


        Analyzer anal = new Analyzer(sim, dc);
        int N = 10;
        int quantum = 100;

        boolean defineCI = true;
        boolean verifyErgodic = true;


        if (defineCI) {
            float alpha = 0.05f;
            boolean meanSimPLot = true;
            anal.defineCI(N, alpha, quantum, meanSimPLot);
        }

        if (verifyErgodic) {
            float eps = 0.1f;
            anal.verifyErgodic(N, quantum, eps);
        }


        gb.printGraph();

    }
}