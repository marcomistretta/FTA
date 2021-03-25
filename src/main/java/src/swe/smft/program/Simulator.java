package src.swe.smft.program;

import src.swe.smft.event.Event;
import src.swe.smft.event.TreeManager;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.Triplet;

import java.util.ArrayList;

public class Simulator {

    private final Timer timer;
    private final TreeManager treeManager;

    public float getMaxTime() {
        return timer.getMaxTime();
    }

    public Simulator(float maxTime, TreeManager em) {
        timer = new Timer(maxTime);
        this.treeManager = em;
    }

    public ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation(boolean randomReset) {
        ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simResult = new ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>();
        timer.reset();
        if (randomReset)
            treeManager.randomReset();
        else
            treeManager.reset();
        simResult.add(new Triplet<>
                (timer.getTime(),
                        treeManager.getTopEvent().isWorking(),
                        treeManager.getStatus()));
        timer.nextTime(treeManager.getOmega());
        while (timer.getTime() >= 0) {
            treeManager.nextToggle();
            simResult.add(new Triplet(timer.getTime(),
                    treeManager.getTopEvent().isWorking(),
                    treeManager.getStatus()));
            timer.nextTime(treeManager.getOmega());
        }
        return simResult;
    }


}


