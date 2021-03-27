package src.swe.smft.program;

import src.swe.smft.event.*;
import src.swe.smft.event.Event;
import src.swe.smft.plot.HarryPlotter;
import src.swe.smft.memory.DataCentre;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        HarryPlotter hp = HarryPlotter.getInstance();
        TreeManager tm = new TreeManager();
        EventFactory modeler = EventFactory.getInstance();
        DataCentre dc = new DataCentre();
        Simulator sim;
        Scanner istream = new Scanner(System.in);
        System.out.println("Utility di creazione ed analisi di modelli SMFT:\nUsare un modello preimpostato [p] o uno casuale[R]? ");
        boolean premadeModel = false;
        if(istream.next().equals("p")) {
            // premade flow

            premadeModel = true;
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
            String opzE = "OR";
            String opzF = "AND";
            Event E = modeler.createIntermediateEvent(childrenE, opzE);
            Event F = modeler.createIntermediateEvent(childrenF, opzF);

            List<Event> childrenTop = List.of(E, F);
            String opzTop = "AND";
            Event top = modeler.createIntermediateEvent(childrenTop, opzTop);
            tm.setTopEvent((IntermediateEvent) top);
        } else {
            // random flow (default)

            int nBasic;
            System.out.println("Numero di floglie (almeno 2)[10]: ");
            try {
                nBasic = Integer.parseInt(istream.next());
                if(nBasic < 2) {
                    System.err.println("Poche foglie, uso default");
                    nBasic = 10;
                }
            } catch(NumberFormatException e) {
                System.err.println("Non hai inserito un numero, uso default");
                nBasic = 10;
            }


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
        }



//        float maxTime = 15;
//        boolean premadeModel = false;
//        int nBasic = 10;
//
//        if (premadeModel || nBasic <= 2) {
//            float lambdaA = 0.7f;
//            float muA = 0.3f;
//            boolean statusA = true;
//            Event A = modeler.createBasicEvent(lambdaA, muA, statusA);
//            tm.addBasicEvent((BasicEvent) A);
//
//            float lambdaB = 0.4f;
//            float muB = 0.6f;
//            boolean statusB = true;
//            Event B = modeler.createBasicEvent(lambdaB, muB, statusB);
//            tm.addBasicEvent((BasicEvent) B);
//
//            float lambdaC = 0.7f;
//            float muC = 0.3f;
//            boolean statusC = true;
//            Event C = modeler.createBasicEvent(lambdaC, muC, statusC);
//            tm.addBasicEvent((BasicEvent) C);
//
//            float lambdaD = 0.4f;
//            float muD = 0.6f;
//            boolean statusD = true;
//            Event D = modeler.createBasicEvent(lambdaD, muD, statusD);
//            tm.addBasicEvent((BasicEvent) D);
//
//            List<Event> childrenE = List.of(A, B, C);
//            List<Event> childrenF = List.of(B, C, D);
//            String opzE = "OR";
//            String opzF = "AND";
//            Event E = modeler.createIntermediateEvent(childrenE, opzE);
//            Event F = modeler.createIntermediateEvent(childrenF, opzF);
//
//            List<Event> childrenTop = List.of(E, F);
//            String opzTop = "AND";
//            Event top = modeler.createIntermediateEvent(childrenTop, opzTop);
//            tm.setTopEvent((IntermediateEvent) top);
//
//            sim = new Simulator(maxTime, tm);
//        } else {
//            List<Event> topChildren = new ArrayList<>();
//            boolean[] chosenBasic = new boolean[nBasic];
//
//            for (int i = 0; i < nBasic; i++) {
//                BasicEvent e = (BasicEvent) modeler.createRandomBasicEvent();
//                tm.addBasicEvent(e);
//                chosenBasic[i] = false;
//            }
//
//            for (int j = 0; j < nBasic / 2; j++) {
//                List<Event> children = new ArrayList<>();
//                for (int k = 0; k < (int) (Math.random() * (nBasic - 1) + 2); k++) {
//                    int choose = (int) (Math.random() * nBasic);
//                    children.add(tm.getBasicEvents().get(choose));
//                    chosenBasic[choose] = true;
//                }
//                // j-esimo IntermediateEvent
//                Event i = modeler.createRandomIntermediateEvent(children);
//                topChildren.add(i);
//            }
//
//            for (int i = 0; i < nBasic; i++) {
//                if (!chosenBasic[i]) {
//                    Event e = tm.getBasicEvents().get(i);
//                    topChildren.add(e);
//                }
//            }
//
//            // dato che con topEvent != da K=runs/2 la simulazione non risulta interessante
//            // Event topEvent = modeler.createRandomIntermediateEvent(topChildren);
//            String topOpz = String.valueOf(topChildren.size() / 2);
//            Event topEvent = modeler.createIntermediateEvent(topChildren, topOpz);
//
//            tm.setTopEvent((IntermediateEvent) topEvent);
//
//        }
        System.out.println("Inserisci la durata delle simulazioni [15s]: ");
        float maxTime;
        try {
            maxTime = Float.parseFloat(istream.next());

            if(maxTime < 0) {
                System.err.println("Il tempo non può essere negativo, uso valore di default");
                maxTime = 15;
            }
        } catch(NumberFormatException e) {
            System.err.println("Non hai inserito un numero, userò il valore di default");
            maxTime = 15;
        }
        //elimino le cifre decimali in eccesso
        int aux = (int)(maxTime * 10);
        maxTime = aux/10f;


        sim = new Simulator(maxTime, tm);
        Analyzer analyzer = new Analyzer(sim, dc);

        // un'ottima esecuzione:
        // premade model
        // timemax = 15
        // runs = 150'000
        // quantum = 0.1


        System.out.println("Simulazioni per test [100000]: ");
        int runs;
        try {
            runs = Integer.parseInt(istream.next());
            if(runs < 1) {
                System.err.println("Poche simulazioni, imposto a dafault");
                runs = 100000;
            }
        } catch(NumberFormatException e) {
            System.err.println("Non hai inserito un numero, imposto a default");
            runs = 100000;
        }

        System.out.println("Quanto di campionamento [0.1]. Nota, deve dividere " + String.valueOf(maxTime) + " e 10");
        float quantum;
        try {
            quantum = Float.parseFloat(istream.next());
            if(quantum < 0) {
                System.err.println("Quanto negativo, imposto a dafault");
                quantum = .1f;
            }
        } catch(NumberFormatException e) {
            System.err.println("Non hai inserito un numero, imposto a default");
            quantum = .1f;
        }

        boolean doCI = false;
        boolean doErgodic = false;
        int selection;
        System.out.println("Cosa analizzare?\n -1: Intervalli di confidenza\n -2: Ergodicità\n -3: Entrambi (default)");

        try {
            selection = Integer.parseInt(istream.next());
        } catch(NumberFormatException e) {
            System.err.println("Non hai inserito un numero, imposto a default");
            selection = 3;
        }

        float alpha = 0;
        boolean meanPLot = true;
        boolean faultPLot = false;

        double stDeviationPrecision = 0;
        double meanPrecision = 0;

        if(selection == 1 || selection == 3) {
            doCI = true;
            System.out.println("Inserisci l'alpha del grafico");
            try {
                alpha = Float.parseFloat(istream.next());
                if(alpha <= 0 || alpha >= 1) {
                    System.err.println("Alpha non accettabile, imposto a dafault");
                    alpha = 0.05f;
                }
            } catch(NumberFormatException e) {
                System.err.println("Non hai inserito un numero, imposto a default");
                alpha = 0.05f;
            }

            //float alpha = 0.05f;
            System.out.println("Stampare media campionaria? Y/n ");
            if(istream.next().equals("n")) meanPLot = false;

            System.out.println("Stampare fault plot? y/N ");
            if(istream.next().equals("y")) faultPLot = true;
        }
        if(selection == 2 || selection == 3) {
            doErgodic = true;
            System.out.println("Precisione della deviazione standard [0.35]: ");
            try {
                stDeviationPrecision = Float.parseFloat(istream.next());
                if(stDeviationPrecision <= 0) {
                    System.err.println("Deve essere positivo, uso default");
                    stDeviationPrecision = 0.35;
                }
            } catch(NumberFormatException e) {
                System.err.println("Non hai inserito un numero, imposto a default");
                stDeviationPrecision = 0.35;
            }

            System.out.println("Precisione della media campionaria [0.35]: ");
            try {
                meanPrecision = Float.parseFloat(istream.next());
                if(stDeviationPrecision <= 0) {
                    System.err.println("Deve essere positivo, uso default");
                    meanPrecision = 0.03;
                }
            } catch(NumberFormatException e) {
                System.err.println("Non hai inserito un numero, imposto a default");
                meanPrecision = 0.03;
            }
        }

        hp.printGraph();

        if (doCI) {
            hp.printCIInfo(premadeModel, tm.getBasicEvents().size(), maxTime, runs, quantum, alpha, meanPLot, faultPLot);
            analyzer.defineCI(runs, alpha, quantum, meanPLot, faultPLot);
        }
        if (doErgodic) {
            hp.printErgodicInfo(premadeModel, tm.getBasicEvents().size(), maxTime, runs, quantum, meanPrecision, stDeviationPrecision);
            analyzer.verifyErgodic(runs, quantum, meanPrecision, stDeviationPrecision);
        }
    }
}