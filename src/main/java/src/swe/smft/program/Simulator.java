package src.swe.smft.program;

import src.swe.smft.event.Event;
import src.swe.smft.event.TreeManager;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.Triplet;

import java.util.ArrayList;

public class Simulator {

    private final Timer timer;
    private final TreeManager treeManager;
    private final Event topEvent;

    // TODO l'ha aggiunto marco, va bene?
    private final float maxTime;

    // TODO idem
    public float getMaxTime() {
        return maxTime;
    }

    /* xxx ho aggiunto top event in ingresso */
    public Simulator(float maxTime, Event topEvent, TreeManager em) {
        this.maxTime = maxTime;
        timer = new Timer(maxTime);
        this.topEvent = topEvent;
        this.treeManager = em;
    }

    public ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation(boolean randomReset) {
        ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simResult = new ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>();
        timer.reset();
        if (randomReset)
            topEvent.randomReset();
        else
            topEvent.reset();
        simResult.add(new Triplet<Float, Boolean, ArrayList<Boolean>>
                (timer.getTime(),
                        topEvent.isWorking(),
                        treeManager.getStatus()));
        timer.nextTime();
        while (timer.getTime() >= 0) {
            treeManager.nextToggle();
            simResult.add(new Triplet<Float, Boolean, ArrayList<Boolean>>
                    (timer.getTime(),
                            topEvent.isWorking(),
                            treeManager.getStatus()));
            timer.nextTime();

        }
        return simResult;
    }

}


