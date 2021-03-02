package src.swe.smft.program;

import com.sun.tools.javap.BasicWriter;
import src.swe.smft.event.Event;
import src.swe.smft.event.EventManager;
import src.swe.smft.utilities.Delorean;

import java.util.ArrayList;

public class Takayama {
    /**
     * per capire la reference ==> https://www.youtube.com/watch?v=7fWkCmiHSLQ
     * è la classe che gestice la simulazione
     */

    private Delorean timer;
    private EventManager eventManager;
    private Event topEvent;

    public Takayama(float maxTime) {
        timer = new Delorean(maxTime);
    }


}
