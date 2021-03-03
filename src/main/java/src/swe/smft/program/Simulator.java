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
        System.err.print(timer.getTime() + " ");
        System.err.println(topEvent.isWorking());
        while (true){
            eventManager.nextToggle();
            timer.nextTime();
            if(timer.getTime() < 0)
                break;
            System.err.print(timer.getTime() + " ");
            System.err.println(topEvent.isWorking());
        }
    }


}
