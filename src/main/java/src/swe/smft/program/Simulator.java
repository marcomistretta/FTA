package src.swe.smft.program;

import src.swe.smft.event.TreeManager;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.UnquantizedSample;

import java.util.ArrayList;

public class Simulator {

    private final Timer timer;
    private final TreeManager treeManager;

    public Simulator(float maxTime, TreeManager em) {
        timer = new Timer(maxTime);
        this.treeManager = em;
    }

    public float getMaxTime() {
        return timer.getMaxTime();
    }

    public TreeManager getTreeManager() {
        return treeManager;
    }

    public ArrayList<UnquantizedSample> simulation() {
        ArrayList<UnquantizedSample> simResult = new ArrayList<UnquantizedSample>();
        timer.reset();
        simResult.add(new UnquantizedSample
                (timer.getTime(),
                        treeManager.getTopEvent().isWorking(),
                        treeManager.getStatus()));
        timer.nextTime(treeManager.getOmega());
        while (timer.getTime() >= 0) {
            treeManager.nextToggle();
            simResult.add(new UnquantizedSample(timer.getTime(),
                    treeManager.getTopEvent().isWorking(),
                    treeManager.getStatus()));
            timer.nextTime(treeManager.getOmega());
        }
        return simResult;
    }


}


