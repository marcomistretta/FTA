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
            // int nIntermediate = nBasic-2;
            for (int i = 0; i < nBasic; i++) {
                BasicEvent e = modeler.createRandomBasicEvent();
                tm.addBasicEvent(e);
            }
            List<Event> topChildren = new ArrayList<>();
            for (int j = 0; j < nBasic / 2; j++) {
                List<Event> children = new ArrayList<>();
                for (int k = 0; k < nBasic / 2; k++) {
                    children.add(tm.getBasicEvents().get((int) (Math.random() * (nBasic - 1))));
                }
                boolean random = true;
                if (random) topChildren.add(modeler.createRandomIntermediateEvent(children));
                else topChildren.add(modeler.createIntermediateEvent(children, "OR"));
            }

            Event topEvent = modeler.createRandomIntermediateEvent(topChildren);
            tm.setTopEvent((IntermediateEvent) topEvent);

            sim = new Simulator(maxTime, topEvent, tm);
        }


        Analyzer anal = new Analyzer(sim, dc);
        int N = 1000;
        int quantum = 100;

        boolean defineCI = true;
        boolean verifyErgodic = true;

        // TODO Ema guarda perchè l'ordine di esecuzione tra defineCI e verifyErgodic è importante
        // probabile collegato a dataCdnter.clear()? o qualcosa lì vicino, il fatto di aver già simulato?
        // si potrebbe fare in modo di evitare di ripetere la simulazione nel caso si eseguano entrambi

        if (defineCI) {
            float alpha = 0.05f;
            boolean meanSimPLot = true;
            anal.defineCI(N, alpha, quantum, meanSimPLot);
        }
        if (verifyErgodic) {
            float eps = 0.1f;
            anal.verifyErgodic(N, quantum, eps);
        }


    }
}