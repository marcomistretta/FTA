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

            sim = new Simulator(maxTime, C, tm);
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
            String topNumberOfChildren = String.valueOf(topChildren.size() / 2);
            Event topEvent = modeler.createIntermediateEvent(topChildren, topNumberOfChildren);

            tm.setTopEvent((IntermediateEvent) topEvent);

            sim = new Simulator(maxTime, topEvent, tm);
        }

        Analyzer analyzer = new Analyzer(sim, dc);

        // un'ottima esecuzione:
        // premade model
        // timemax = 20
        // runs = 150'000
        // quantum = 0.1

        int runs = 100000;
        float quantum = 0.1f;
        boolean doCI = true;
        boolean doErgodic = true;
        double meanPrecision = 0.05f;
        double varPrecision = 0.25;
        float alpha = 0.05f;
        boolean meanPLot = true;
        boolean faultPLot = false;

        hp.printGraph();

        if (doCI) {
            printCIInfo(premadeModel, nBasic, maxTime, runs, quantum, alpha, meanPLot, faultPLot);
            analyzer.defineCI(runs, alpha, quantum, meanPLot, faultPLot);
        }
        if (doErgodic)
            printErgodicInfo(premadeModel, nBasic, maxTime, runs, quantum, meanPrecision, varPrecision);
            analyzer.verifyErgodic(runs, quantum, meanPrecision, varPrecision);

    }

    // TODO fammi sapere se ti piacciono, nel caso ti piacciano le vorrei spostare in harry plotter
    public static void printCIInfo(boolean premade, int nBasics, float maxTime, int runs, float quantum, float alpha, boolean meanPlot, boolean faultPlot) {
        System.out.println();

        System.out.println("*** Avvio definizione intervallo di confidenza per il valore atteso dello stato di funzionamento del Top Event del seguente modello di SMFT ***");
        printGeneralInfo(premade, nBasics, maxTime, runs, quantum);
        System.out.println("Livello di significatività: " + alpha);
        if (meanPlot)
            System.out.println("Richiesta stampa della media campionaria dello stato di funzionamento");
        if (faultPlot)
            System.out.println("Richiesta stampa della media campionaria dello stato di mal-funzionamento");

        System.out.println();

        return;
    }

    public static void printErgodicInfo(boolean premade, int nBasics, float maxTime, int runs, float quantum, double meanPrecision, double varPrecision) {
        System.out.println();

        System.out.println("*** Avvio verifica ergodicità del seguente modello di SMFT ***");
        printGeneralInfo(premade, nBasics, maxTime, runs, quantum);
        System.out.println("Margine di errore ammissibile per la valutazione della convergenza della media campionaria: " + meanPrecision);
        System.out.println("Margine superiore di accettazione per la varianza campionaria: " + varPrecision);

        System.out.println();

        return;
    }

    public static void printGeneralInfo(boolean premade, int nBasics, float maxTime, int runs, float quantum) {
        if (premade)
            System.out.println("Modello pre-costruito");
        else
            System.out.println("Modello creato in modo casuale con " + nBasics + " foglie");
        System.out.println("Max time della simulazione: " + maxTime);
        System.out.println("Numero di runs: " + runs);
        System.out.println("Passo di quantizzazione: " + quantum);
    }
}