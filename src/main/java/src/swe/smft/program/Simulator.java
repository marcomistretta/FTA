package src.swe.smft.program;

import src.swe.smft.event.Event;
import src.swe.smft.event.EventManager;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.Triplet;

import java.util.ArrayList;

public class Simulator {

    private Timer timer;
    private EventManager eventManager;
    private Event topEvent;

    /* xxx ho aggiunto top event in ingresso */
    public Simulator(float maxTime, Event topEvent, EventManager em) {
        timer = new Timer(maxTime);
        this.topEvent = topEvent;
        this.eventManager = em;
    }

    /* TODO simulation() */
    public ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation(){
        /* just for debugging */
        ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simResult = new ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>();
        while (timer.getTime()  >= 0){
            eventManager.nextToggle();
            simResult.add(new Triplet<Float, Boolean, ArrayList<Boolean>>
                (timer.getTime(),
                topEvent.isWorking(),
                eventManager.getStatus()));
            timer.nextTime();
        }
        return simResult;
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

