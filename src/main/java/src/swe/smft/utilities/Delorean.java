package src.swe.smft.utilities;

import java.util.Random;

public class Delorean {
    /**
     * la celeberrima auto di ritorno al futuro, che ci consente di muoverci liberamente, o quasi, nel tempo
     * proprio ciò che dovrebbe fare la classe Timer
     *
     * se il tempo trascorso (current time) è maggiore del tempo di simulazioone massimo (simulation timer)
     * lo segnala, rendendo un tempo negativo
     */

    float simulation_time;
    float current_time;

    public Delorean(float max_time) {
        simulation_time = max_time > 0 ? max_time : 100;
        current_time = 0;
    }

    public float getTime() {
        return current_time;
    }

    public void nextTime() {
        current_time += -ChineseChildCalculator.log2(Math.random());
        if (current_time >= simulation_time)
            current_time = -1;
    }
}
