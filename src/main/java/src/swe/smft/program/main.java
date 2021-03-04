/* new */
package src.swe.smft.program;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.EventManager;
import src.swe.smft.event.EventModeler;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        System.out.println("Progetto di Casciaro & Mistretta");

        /* simple case */
        /* twp basic events: A, B */
        /* A && B = C */
        /* start Gate & Events Testing */
        EventModeler modeler = new EventModeler();

        float lambdaA = 0.7f;
        float muA = 0.3f;
        boolean statusA = false;
        BasicEvent A = modeler.createBasicEvent(lambdaA, muA, statusA);

        float lambdaB = 0.4f;
        float muB = 0.6f;
        boolean statusB = false;
        BasicEvent B = modeler.createBasicEvent(lambdaB, muB, statusB);

        ArrayList<Event> children = new ArrayList<Event>();
        children.add(A);
        children.add(B);
        char opz = 'A';
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
//public static void main(String[] args) {
//
//    }
//}

