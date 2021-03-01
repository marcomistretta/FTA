package src.swe.smft.program;
import src.swe.smft.event.*;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        System.out.println("Progetto di Casciaro & Mistretta");

        /* simple case */
        /* twp basic events: A, B */
        /* A && B = C */
        /* start Gate & Events Testing */
        Modeler modeler = new Modeler();

        float lambdaA = 0.5f;
        float muA = 0.5f;
        boolean statusA = false;
        Event A = modeler.createBasicEvent(lambdaA, muA, statusA);

        float lambdaB = 0.5f;
        float muB = 0.5f;
        boolean statusB = false;
        Event B = modeler.createBasicEvent(lambdaB, muB, statusB);

        ArrayList<Event> children = new ArrayList<Event>();
        children.add(A);
        children.add(B);
        char opz = '1';
        Event C = modeler.createIntermediateEvent(children, opz);
        System.out.println(C.isWorking());
        /* end Gate & Events Testing */


    }
}
