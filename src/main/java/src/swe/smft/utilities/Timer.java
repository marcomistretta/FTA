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

    public static void estimatedTime(int n, double start, int i, String action) {
        double actual = System.currentTimeMillis();
        if(i == n / 10 || i == 2 * n / 10 || i == 3 * n / 10 ||
                i == 4 * n / 10 || i == 5 * n / 10 || i == 6 * n / 10 ||
                i == 7 * n / 10 || i == 8 * n / 10 || i == 9 * n / 10) {
            double delta = (actual - start) / 1000;
            double estimated = (delta * n) / (i + 1);
            System.out.println(action + ": tempo rimasto ~ " + (estimated - delta));
        }
    }

}
