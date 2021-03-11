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

    /* xxx ho aggiunto top event in ingresso */
    public Simulator(float maxTime, Event topEvent, TreeManager em) {
        timer = new Timer(maxTime);
        this.topEvent = topEvent;
        this.treeManager = em;
    }

    /* TODO simulation(): a me sembra fatta */
    public ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simulation(){
        ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>> simResult = new ArrayList<Triplet<Float, Boolean, ArrayList<Boolean>>>();
        timer.reset();
        topEvent.reset();
        simResult.add(new Triplet<Float, Boolean, ArrayList<Boolean>>
                (timer.getTime(),
                        topEvent.isWorking(),
                        treeManager.getStatus()));
        System.err.println(simResult.get(0).getElement2());
        timer.nextTime();
        while (timer.getTime()  >= 0){
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


