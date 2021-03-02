package src.swe.smft.program;
import src.swe.smft.harryplotter.*;
import src.swe.smft.event.*;
import src.swe.smft.utilities.Delorean;

import java.util.ArrayList;

public class main {
//    public static void main(String[] args) {
//        System.out.println("Progetto di Casciaro & Mistretta");
//
//        /* simple case */
//        /* twp basic events: A, B */
//        /* A && B = C */
//        /* start Gate & Events Testing */
//        EventModeler modeler = new EventModeler();
//
//        float lambdaA = 0.5f;
//        float muA = 0.5f;
//        boolean statusA = false;
//        Event A = modeler.createBasicEvent(lambdaA, muA, statusA);
//
//        float lambdaB = 0.5f;
//        float muB = 0.5f;
//        boolean statusB = false;
//        Event B = modeler.createBasicEvent(lambdaB, muB, statusB);
//
//        ArrayList<Event> children = new ArrayList<Event>();
//        children.add(A);
//        children.add(B);
//        char opz = '1';
//        Event C = modeler.createIntermediateEvent(children, opz);
//        System.out.println(C.isWorking());
//        /* end Gate & Events Testing */
//
//
//    }
    public static void main(String[] args) {
        Delorean delorean = new Delorean(1e5f);
        float t = delorean.getTime();
        int c = 0;
        float sum = 0;
        while (t >= 0) {
            c += 1;
            float p = t;
            delorean.nextTime();
            t = delorean.getTime();
            sum += t-p;
            System.out.println(t);
        }
        System.out.println(sum/c);
    }
}
