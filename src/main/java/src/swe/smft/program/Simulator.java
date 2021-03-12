package src.swe.smft.program;

import src.swe.smft.event.Event;
import src.swe.smft.event.TreeManager;
import src.swe.smft.utilities.Timer;
import src.swe.smft.utilities.Triplet;

import java.util.ArrayList;

public class Simulator {

    private Timer timer;
    private TreeManager treeManager;
    private Event topEvent;

    // TODO l'ha aggiunto marco, va bene?
    private float maxTime;
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

    // TODO ho aggiunto un booleano in input, mi sembra sensato
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
        System.err.println(simResult.get(0).getElement2());
        timer.nextTime();
        while (timer.getTime() >= 0) {
            System.err.println("SONO NEL WHILE DI SIMULATION");
            treeManager.nextToggle();
            System.err.println("SONO NEL WHILE DI 1");
            simResult.add(new Triplet<Float, Boolean, ArrayList<Boolean>>
                    (timer.getTime(),
                            topEvent.isWorking(),
                            treeManager.getStatus()));
            System.err.println("SONO NEL WHILE DI 2");
            timer.nextTime();
            System.err.println("HO FINITO IL WHILE");

        }
        return simResult;
    }

}


