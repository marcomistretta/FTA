package src.swe.smft.program;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.EventManager;
import src.swe.smft.event.EventModeler;
import src.swe.smft.utilities.Timer;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        System.out.println("Progetto di Casciaro & Mistretta");

        /* simple case */
        /* twp basic events: A, B */
        /* A && B = C */
        /* start Gate & Events Testing */
        EventModeler modeler = new EventModeler();

        float lambdaA = 0.5f;
        float muA = 0.5f;
        boolean statusA = false;
        BasicEvent A = modeler.createBasicEvent(lambdaA, muA, statusA);

        float lambdaB = 0.5f;
        float muB = 0.5f;
        boolean statusB = false;
        BasicEvent B = modeler.createBasicEvent(lambdaB, muB, statusB);

        ArrayList<Event> children = new ArrayList<Event>();
        children.add(A);
        children.add(B);
        char opz = '1';
        Event C = modeler.createIntermediateEvent(children, opz);
        // System.out.println(C.isWorking());
        /* end Gate & Events Testing */

        /* TODO ema abbiamo un problema:
        ma forse ho risolto
         */
        ArrayList<BasicEvent> childrenBasic = new ArrayList<BasicEvent>();
        childrenBasic.add(A);
        childrenBasic.add(B);
        EventManager em = new EventManager(childrenBasic);
        Simulator simulator = new Simulator(1000, C, em);
        simulator.simulation();

    }
}
//    public static void main(String[] args) {
//        Timer delorean = new Timer(1e5f);
//        float t = delorean.getTime();
//        int c = 0;
//        float sum = 0;
//        while (t >= 0) {
//            c += 1;
//            float p = t;
//            delorean.nextTime();
//            t = delorean.getTime();
//            sum += t-p;
//            System.out.println(t);
//        }
//        System.out.println(sum/c);
//    }
//}
