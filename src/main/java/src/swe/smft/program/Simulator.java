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
