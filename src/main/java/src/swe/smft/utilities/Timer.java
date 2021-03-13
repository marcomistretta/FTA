/* new */
package src.swe.smft.utilities;

public class Timer {

    float simulation_time;
    float current_time;

    public Timer(float max_time) {
        simulation_time = max_time > 0 ? max_time : 100;
        current_time = 0;
    }

    public float getTime() {
        return current_time;
    }

    public void reset() {
        current_time = 0;
    }

    public float nextTime() {
        current_time += -Calculator.log2(Math.random());
        if (current_time >= simulation_time)
            current_time = -1;
        return current_time;
    }
}
