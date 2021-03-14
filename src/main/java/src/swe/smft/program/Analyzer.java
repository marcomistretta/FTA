package src.swe.smft.program;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;
import src.swe.smft.memory.DataCentre;
import src.swe.smft.utilities.Pair;
import src.swe.smft.utilities.Statistic;

import java.util.ArrayList;

public class Analyzer {
    private final Simulator s;
    private final DataCentre dc;

    public Analyzer(Simulator s, DataCentre dc) {
        this.s = s;
        this.dc = dc;
    }

    public void defineCI(int N, float alpha, int quantum) {
        dc.clear();

        for (int i = 0; i < N; i++) {
            dc.appendData(s.simulation(false));
        }

        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults =
                dc.quantizedData(quantum, s.getMaxTime());

        double[][] CI = Statistic.confidenceInterval(quantizedResults, alpha);
        double[] sampleMean = Statistic.sampleMean(quantizedResults);

        int l = CI[0].length;
        double time = 0;
        double[] times = new double[l];
        for (int i = 0; i < l; i++) {
            times[i] = time;
            time = time + quantum;
        }

        // TODO incapsulate in harry plotter
        // Create Chart
        final XYChart chart = new XYChartBuilder().width(600).height(400).title("Confidence Interval").xAxisTitle("times").yAxisTitle("CI").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // TODO deve essere opzionale
        boolean meanPlot = false;
        // Series
        XYSeries series = chart.addSeries("lower bound", times, CI[0]);
        series.setMarker(SeriesMarkers.NONE);
        if (meanPlot) {
            series = chart.addSeries("sample mean", times, sampleMean);
            series.setMarker(SeriesMarkers.NONE);
        }
        series = chart.addSeries("upper bound", times, CI[1]);
        series.setMarker(SeriesMarkers.NONE);

        new SwingWrapper(chart).displayChart();


    }

    public void verifyErgodic(int N, int quantum, float eps) {
        dc.clear();

        for (int i = 0; i < N; i++) {
            dc.appendData(s.simulation(true));
        }

        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults =
                dc.quantizedData(quantum, s.getMaxTime());

        double[] differences = Statistic.allDifference(quantizedResults);
        double time = 0;
        double[] times = new double[differences.length];
        double[] epsF = new double[differences.length];
        //double[] minusEpsF = new double[differences.length];
        for (int i = 0; i < differences.length; i++) {
            times[i] = time;
            time = time + quantum;
            epsF[i] = eps;
            //minusEpsF[i] = -eps;
        }

        // TODO incapsulate in harry plotter
        // Create Chart
        final XYChart chart = new XYChartBuilder().width(600).height(400).title("Ergodic Nature").xAxisTitle("times").yAxisTitle("value").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Series
        //chart.addSeries("-eps", times, minusEpsF);
        XYSeries series = chart.addSeries("difference", times, differences);
        series.setMarker(SeriesMarkers.NONE);
        series = chart.addSeries("+eps", times, epsF);
        series.setMarker(SeriesMarkers.NONE);


        new SwingWrapper(chart).displayChart();

    }
}
