package src.swe.smft.program;

import org.javatuples.Pair;
import src.swe.smft.event.Event;
import src.swe.smft.event.EventManager;
import src.swe.smft.utilities.Timer;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulator {

    private final Timer timer;
    private final EventManager eventManager;
    private final Event topEvent;

    /* TODO ho aggiunto top event in ingresso */
    public Simulator(float maxTime, Event topEvent, EventManager em) {
        timer = new Timer(maxTime);
        eventManager = em;
        this.topEvent = topEvent;
    }

    /* TODO simulation() */
    public HashMap<Float, Pair<Boolean, ArrayList<Boolean>>> simulation(){
        /**
         * ai fini dell'analisi, ogni simulazione dev rendere la sequenza degli istanti temporali,
         * lo stato dell'albero e lo stato delle singole foglie ==> tripletta di dati
         */
        /* just for debugging */
        HashMap<Float, Pair<Boolean, ArrayList<Boolean>>>
                simulationResult = new HashMap<Float, Pair<Boolean, ArrayList<Boolean>>>();
        System.out.print("Tempo: " + timer.getTime() + " ");
        System.out.println(topEvent.isWorking());
        while (timer.getTime() >= 0){
            //salvo lo stato attualeù
            simulationResult.put(timer.getTime(), Pair.with(topEvent.isWorking(), eventManager.getStatus()));
            //calcolo il prossimo
            eventManager.nextToggle();
            timer.nextTime();
        }
        return simulationResult;
    }

}

//public class Simulator {
//
//    private final Timer timer;
//    private final EventManager eventManager;
//    private final Event topEvent;
//
//    /* TODO ho aggiunto top event in ingresso */
//public Simulator(float maxTime, Event topEvent, EventManager em) {
//    timer = new Timer(maxTime);
//    eventManager = em;
//    this.topEvent = topEvent;
//}
//
//    /* TODO simulation() */
//    public HashMap<Float, Pair<Boolean, ArrayList<Boolean>>> simulation(){
//        /**
//         * ai fini dell'analisi, ogni simulazione dev rendere la sequenza degli istanti temporali,
//         * lo stato dell'albero e lo stato delle singole foglie ==> tripletta di dati
//         */
//        /* just for debugging */
//        HashMap<Float, Pair<Boolean, ArrayList<Boolean>>>
//                simulationResult = new HashMap<Float, Pair<Boolean, ArrayList<Boolean>>>();
//        System.out.print("Tempo: " + timer.getTime() + " ");
//        System.out.println(topEvent.isWorking());
//        while (timer.getTime() >= 0){
//            //salvo lo stato attualeù
//            simulationResult.put(timer.getTime(), Pair.with(topEvent.isWorking(), eventManager.getStatus()));
//            //calcolo il prossimo
//            eventManager.nextToggle();
//            timer.nextTime();
//        }
//        return simulationResult;
//    }
//
//}

