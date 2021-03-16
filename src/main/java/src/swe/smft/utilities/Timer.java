/* new */
package src.swe.smft.utilities;

public class Timer {

    float maxTime;
    float current_time;

    public Timer(float max_time) {
        maxTime = max_time > 0 ? max_time : 100;
        current_time = 0;
    }

    public float getTime() {
        return current_time;
    }

    public float getMaxTime() {
        return maxTime;
    }

    public void reset() {
        current_time = 0;
    }

    public float nextTime(float omega) {
        //log(0) è voluto: fine imprevedibile della simulazione
        current_time -= (Math.log(1 - Math.random())) / omega;
        if (current_time >= maxTime)
            current_time = -1;
        return current_time;
    }
}
