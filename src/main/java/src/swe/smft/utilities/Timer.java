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
        current_time -= (Math.log(1 - Math.random())) / omega;
        if (current_time >= maxTime)
            current_time = -1;
        return current_time;
    }

    public static void estimatedTime(int N, double start, int i, String action) {
        double actual = System.currentTimeMillis();
        if(i % (N/10) == 0 && i != 0) {
            double delta = (actual - start) / 1000;
            double estimated = (delta * N) / (i + 1);
            if((estimated-delta)>= 7e-1)
                System.out.println(action + ": tempo rimasto ~ " + (estimated - delta));
        }
    }

}
