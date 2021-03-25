package src.swe.smft.program;

import src.swe.smft.event.*;
import src.swe.smft.event.Event;
import src.swe.smft.harryplotter.HarryPlotter;
import src.swe.smft.memory.DataCentre;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        HarryPlotter hp = HarryPlotter.getInstance();
        TreeManager tm = new TreeManager();
        EventFactory modeler = EventFactory.getInstance();
        DataCentre dc = new DataCentre();
        Simulator sim;

        float maxTime = 15;
        boolean premadeModel = false;
        int nBasic = 6;

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

            List<Event> childrenE = List.of(A, B, C);
            List<Event> childrenF = List.of(B, C, D);
            String opzE = "AND";
            String opzF = "AND";
            Event E = modeler.createIntermediateEvent(childrenE, opzE);
            Event F = modeler.createIntermediateEvent(childrenF, opzF);

            List<Event> childrenTop = List.of(E, F);
            String opzTop = "AND";
            Event top = modeler.createIntermediateEvent(childrenTop, opzTop);
            tm.setTopEvent((IntermediateEvent) top);

            sim = new Simulator(maxTime, tm);
        } else {
            List<Event> topChildren = new ArrayList<>();
            boolean[] chosenBasic = new boolean[nBasic];

            for (int i = 0; i < nBasic; i++) {
                BasicEvent e = (BasicEvent) modeler.createRandomBasicEvent();
                tm.addBasicEvent(e);
                chosenBasic[i] = false;
            }

            for (int j = 0; j < nBasic / 2; j++) {
                List<Event> children = new ArrayList<>();
                for (int k = 0; k < (int) (Math.random() * (nBasic - 1) + 2); k++) {
                    int choose = (int) (Math.random() * nBasic);
                    children.add(tm.getBasicEvents().get(choose));
                    chosenBasic[choose] = true;
                }
                // j-esimo IntermediateEvent
                Event i = modeler.createRandomIntermediateEvent(children);
                topChildren.add(i);
            }

            for (int i = 0; i < nBasic; i++) {
                if (!chosenBasic[i]) {
                    Event e = tm.getBasicEvents().get(i);
                    topChildren.add(e);
                }
            }

            // dato che con topEvent != da K=runs/2 la simulazione non risulta interessante
            // Event topEvent = modeler.createRandomIntermediateEvent(topChildren);
            String topOpz = String.valueOf(topChildren.size() / 2);
            Event topEvent = modeler.createIntermediateEvent(topChildren, topOpz);

            tm.setTopEvent((IntermediateEvent) topEvent);

            sim = new Simulator(maxTime, tm);
        }

        Analyzer analyzer = new Analyzer(sim, dc);

        // un'ottima esecuzione:
        // premade model
        // timemax = 20
        // runs = 150'000
        // quantum = 0.1

        int runs = 150000;
        float quantum = 0.1f;
        boolean doCI = true;
        boolean doErgodic = true;
        double meanPrecision = 0.03f;
        double varPrecision = 0.25;
        float alpha = 0.05f;
        boolean meanPLot = true;
        boolean faultPLot = false;

        hp.printGraph();

        if (doCI) {
            hp.printCIInfo(premadeModel, nBasic, maxTime, runs, quantum, alpha, meanPLot, faultPLot);
            analyzer.defineCI(runs, alpha, quantum, meanPLot, faultPLot);
        }
        if (doErgodic)
            hp.printErgodicInfo(premadeModel, nBasic, maxTime, runs, quantum, meanPrecision, varPrecision);
            analyzer.verifyErgodic(runs, quantum, meanPrecision, varPrecision);
    }
}