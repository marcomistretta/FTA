package src.swe.smft.program;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import src.swe.smft.memory.DataCentre;
import src.swe.smft.utilities.Pair;
import src.swe.smft.utilities.Statistic;

import java.util.ArrayList;

public class Analyzer {
    private Simulator s;
    private DataCentre dc;

    public Analyzer(Simulator s, DataCentre dc) {
        this.s = s;
        this.dc = dc;
    }

    public void defineCI(int N, float alpha, int quantum){
        System.err.println("SONO NEL DEFINE");
        for(int i = 0; i<N; i++){
            System.err.println("SONO NEL FOR DEL DEFINE");
            dc.appendData(s.simulation(false));
        }
        // TODO CHECK OUT OF BOUND
        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults =
                dc.quantizedData(quantum, s.getMaxTime());

        double[][] CI = Statistic.confidenceInterval(quantizedResults, alpha);
        int l = CI[0].length;
        double time = 0;
        double[] xData = new double[l];
        for (int i = 0; i < l; i++) {
            xData[i] = time;
            time = time + quantum;
        }

        String[] CInames = new String[2];
        CInames[0] = "Lower Bound";
        CInames[1] = "Upper Bound";
        XYChart chart = QuickChart.getChart("Confidence Interval", "times", "CI", CInames, xData, CI);

        // new SwingWrapper(chart).displayChart();

    }

    public void verifyErgodic(int N, int quantum){
        for(int i = 0; i<N; i++){
            dc.appendData(s.simulation(true));
        }
        // TODO implement

    }
}
