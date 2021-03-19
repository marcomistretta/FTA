package src.swe.smft.program;

import src.swe.smft.event.Event;
import src.swe.smft.event.TreeManager;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.Triplet;

import java.util.ArrayList;

public class Simulator {
    // TODDO aggiunto solo per il debug
    static int count = 0;

    private final Timer timer;
    private final TreeManager treeManager;
    private final Event topEvent;


    public float getMaxTime() {
        return timer.getMaxTime();
    }

    public Simulator(float maxTime, Event topEvent, TreeManager em) {
        timer = new Timer(maxTime);
        this.topEvent = topEvent;
        this.treeManager = em;
    }

    public ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation(boolean randomReset) {
        count++;
        ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simResult = new ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>();
        timer.reset();
        if (randomReset)
            treeManager.randomReset();
        else
            treeManager.reset();
        for (int i = 0; i < treeManager.getBasicEvents().size(); i++) {
            if (!treeManager.getBasicEvents().get(i).isWorking()) {
                System.err.println(treeManager.getBasicEvents().get(i).isWorking());
                System.err.println("id: " + i);
                System.err.println("iterazione: " + count);
            }
        }
        simResult.add(new Triplet<Float, Boolean, ArrayList<Boolean>>
                (timer.getTime(),
                        topEvent.isWorking(),
                        treeManager.getStatus()));
        timer.nextTime(treeManager.getOmega());
        while (timer.getTime() >= 0) {
            treeManager.nextToggle();
            simResult.add(new Triplet(timer.getTime(),
                    topEvent.isWorking(),
                    treeManager.getStatus()));
            timer.nextTime(treeManager.getOmega());
        }
        return simResult;
    }


}


