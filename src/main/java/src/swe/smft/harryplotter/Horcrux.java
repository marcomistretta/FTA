package src.swe.smft.harryplotter;

import org.knowm.xchart.*;

public class Horcrux implements Voldemort {

    private static Horcrux myHorcrux = null;

    public static Horcrux getInstance() {
        if(myHorcrux != null)
            return myHorcrux;
        myHorcrux = new Horcrux();
        return myHorcrux;
    }

    @Override
    public void plotSimulation() {

    }

    @Override
    public void plotReliability() {

    }

    @Override
    public void plotAvarage() {

    }

    @Override
    public void plotTree() {

    }
}
