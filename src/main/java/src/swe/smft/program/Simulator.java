package src.swe.smft.program;

import src.swe.smft.event.TreeManager;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.RawSample;

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

    public ArrayList<RawSample> simulation() {
        ArrayList<RawSample> simResult = new ArrayList<RawSample>();
        timer.reset();
        simResult.add(new RawSample
                (timer.getTime(),
                        treeManager.getTopEvent().isWorking(),
                        treeManager.getStatus()));
        timer.nextTime(treeManager.getOmega());
        while (timer.getTime() >= 0) {
            treeManager.nextToggle();
            simResult.add(new RawSample(timer.getTime(),
                    treeManager.getTopEvent().isWorking(),
                    treeManager.getStatus()));
            timer.nextTime(treeManager.getOmega());
        }
        return simResult;
    }


}


