package src.swe.smft.program;

import src.swe.smft.event.*;
import src.swe.smft.memory.DataCentre;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        TreeManager tm = new TreeManager();
        EventModeler modeler = EventModeler.getInstance();

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

        ArrayList<Event> children = new ArrayList<Event>();
        // TODO change ArrayList to List
        children.add(A);
        children.add(B);
        String opz = "OR";
        Event C = modeler.createIntermediateEvent(children, opz);
        tm.setTopEvent((IntermediateEvent) C);
        // TODO XXX
        tm.buildGraph();

        float maxTime = 10000;
        Simulator sim = new Simulator(maxTime, C, tm);
        DataCentre dc = new DataCentre();

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