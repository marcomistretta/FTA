/*
package src.swe.smft.program;

import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.EventModeler;
import src.swe.smft.event.IntermediateEvent;
import src.swe.smft.utilities.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public Client() {
    }

    public static void main(String[] args) {
        EventModeler em = EventModeler.getInstance();

        System.out.println("Analizzatore-Simulatore-Modellatore di Stochastic Markovian Fault Tree \ndi Casciaro e Mistretta");
        Scanner input = new Scanner(System.in);

        System.out.print("Digitare il numero di Basic Event desiderato: ");
        int nBasics = input.nextInt();
        System.out.println();
        ArrayList<Pair<BasicEvent, String>> basicEvents = new ArrayList<Pair<BasicEvent, String>>();

        System.out.print("Digitare il numero di Intermediate Event desiderato: ");
        int nIntermediates = input.nextInt();
        ArrayList<Pair<Event, String>> interEvents = new ArrayList<Pair<Event, String>>();

        System.out.println();
        System.out.println("Digitare il nome (char) dei Basic: (uno alla volta)");
        String[] basicNames = new String[nBasics];
        for (int i = 0; i < nBasics; i++) {
            System.out.print((i + 1) + ": ");
            basicNames[i] = input.next();
            if (i % 2 == 0) {
                System.out.println(basicNames[i] + " true(lambada = 0.6, mu = 0.4)");
                basicEvents.add(new Pair(new BasicEvent(0.6f, 0.4f, true), basicNames[i]));
            } else {
                System.out.println(basicNames[i] + " false(lambada = 0.4, mu = 0.6)");
                basicEvents.add(new Pair(new BasicEvent(0.4f, 0.6f, false), basicNames[i]));
            }
        }

        ArrayList<Event> children = new ArrayList<Event>();
        for (int i = 0; i < nBasics; i++) {
            children.add(basicEvents.get(i).getElement1());
        }

        System.out.println();
        System.out.println("Digitare il nome degli Intermediate: (uno alla volta)");
        String[] interNames = new String[nIntermediates];
        for (int i = 0; i < nIntermediates; i++) {
            System.out.print((i + 1) + ": ");
            // TODO START TO FIX
            char c;
            if (i == 0)
                c = 'D';
            else if (i == 1)
                c = 'E';
            else
                c = 'F';
            // TODO END FIX


            interNames[i] = input.next();
            if (i % 2 == 0) {
                // TODO after fix remove c and put interNames
                System.out.println(c + " AND");
                Event e = em.createIntermediateEvent(children, 'A');
                // TODO after fix remove c and put interNames
                interEvents.add(new Pair<Event, String>(e, Character.toString(c)));
            } else {
                System.out.println(interNames[i] + " OR");
                Event e = em.createIntermediateEvent(children, 'A');
                // TODO after fix remove c and put interNames
                interEvents.add(new Pair<Event, String>(e, Character.toString(c)));
            }
        }
        System.out.println("Ad ogni intermediate, ogni basic...");



    }


}

 */

