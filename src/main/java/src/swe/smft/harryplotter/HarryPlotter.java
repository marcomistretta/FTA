package src.swe.smft.harryplotter;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class HarryPlotter {

    /**
     * stampa magicamente i dati! Amo la magia
     */

    private static int width = 1000;
    private static int height = 700;
    private static HarryPlotter mainCharacter = null;

    public static HarryPlotter getInstance() {
        if (mainCharacter != null)
            return mainCharacter;
        mainCharacter = new HarryPlotter();
        return mainCharacter;
    }

    public void plotSimulation() {

    }

    public void plotReliability(double[] times, boolean meanPlot, double[][] CI, double[] sampleMean) {
        final XYChart chart = new XYChartBuilder().width(1200).height(800).title("Confidence Interval").xAxisTitle("times").yAxisTitle("CI").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

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

    public void plotErgodic(double[] times, double[] epsF, double[] differences) {
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

    public void plotTree() {

    }

}
