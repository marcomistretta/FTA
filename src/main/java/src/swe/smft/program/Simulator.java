package src.swe.smft.program;

import src.swe.smft.event.Event;
import src.swe.smft.event.EventManager;
import src.swe.smft.utilities.Timer;

public class Simulator {

    private Timer timer;
    private EventManager eventManager;
    private Event topEvent;

    /* TODO ho aggiunto top event in ingresso */
    public Simulator(float maxTime, Event topEvent, EventManager em) {
        timer = new Timer(maxTime);
        this.topEvent = topEvent;
        this.eventManager = em;
    }

    /* TODO simulation() */
    public void simulation(){
        /* just for debugging */
        System.out.print("Tempo: " + timer.getTime() + " ");
        System.out.println(topEvent.isWorking());
        while (true){

            eventManager.nextToggle();
            timer.nextTime();
            if(timer.getTime() < 0)
                break;
            System.out.print("Tempo: " + timer.getTime() + " ");
            System.out.println(topEvent.isWorking());
        }
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

