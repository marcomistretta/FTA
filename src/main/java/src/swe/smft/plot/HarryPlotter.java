package src.swe.smft.plot;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import org.knowm.xchart.XYSeries;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.IntermediateEvent;

import java.io.File;
import java.io.IOException;

import static guru.nidi.graphviz.attribute.Color.*;
import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;


public class HarryPlotter {
    private static HarryPlotter mainCharacter = null;
    private final MutableGraph graph = mutGraph("SMFT").setDirected(true);
    private final XYChart chartCI = new XYChartBuilder().width(600).height(400).title("Confidence Intervals").xAxisTitle("times").yAxisTitle("CI").build();
    private final XYChart chartErgodic = new XYChartBuilder().width(600).height(400).title("Ergodic Nature").xAxisTitle("times").yAxisTitle("value").build();
    private final XYChart chartErgodic2 = new XYChartBuilder().width(600).height(400).title("Ergodic Nature Alternative").xAxisTitle("times").yAxisTitle("sample means").build();
    private final String path = "example/modelloSMFT.png";

    private HarryPlotter() {
    }

    public static HarryPlotter getInstance() {
        if (mainCharacter != null)
            return mainCharacter;
        mainCharacter = new HarryPlotter();
        return mainCharacter;
    }

    public void plotReliability(double[] times, double[][] CI, double[] sampleMean, boolean meanPlot, boolean fault) {

        // Customize Chart
        chartCI.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        //chartCI.getStyler().setYAxisMax(1d);
        chartCI.getStyler().setZoomEnabled(true);
        chartCI.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);

        // Series
        chartCI.addSeries("lower bound", times, CI[0]).setMarker(SeriesMarkers.NONE);
        if (meanPlot) {
            chartCI.addSeries("sample mean Reliability", times, sampleMean).setMarker(SeriesMarkers.NONE);
        }
        if (fault) {
            double[] faults = new double[sampleMean.length];
            for (int i = 0; i < sampleMean.length; i++)
                faults[i] = 1 - sampleMean[i];
            chartCI.addSeries("sample mean Fault", times, faults).setMarker(SeriesMarkers.NONE);
        }
        chartCI.addSeries("upper bound", times, CI[1]).setMarker(SeriesMarkers.NONE);

        new SwingWrapper(chartCI).displayChart();
    }

    public void plotErgodic(double[] times, double[] sampleMean, double[] sampleVariance) {

        // Customize Chart
        chartErgodic.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        // chartErgodic.getStyler().setYAxisMax(1d);
        chartErgodic.getStyler().setZoomEnabled(true);
        chartErgodic.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);

        // Series
        chartErgodic.addSeries("sampleMean", times, sampleMean).setMarker(SeriesMarkers.NONE);
        chartErgodic.addSeries("sampleStandardDeviation", times, sampleVariance).setMarker(SeriesMarkers.NONE);

        new SwingWrapper(chartErgodic).displayChart();
    }

    public void plotErgodicAlternative(double[] times, double[][] sampleMeans) {
        // Customize Chart
        chartErgodic2.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        //chartErgodic2.getStyler().setYAxisMax(1d);
        chartErgodic2.getStyler().setZoomEnabled(true);
        chartErgodic2.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);

        for (int i = 0; i < sampleMeans.length; i++) {
            chartErgodic2.addSeries("M" + (i + 1), times, sampleMeans[i]).setMarker(SeriesMarkers.NONE);
        }

        new SwingWrapper(chartErgodic2).displayChart();
    }

    public void addNode(BasicEvent e) {
        Color color;
        if (e.isWorking())
            color = GREEN;
        else
            color = RED;
        graph.add(mutNode(e.getLabel()).add(color));
    }

    public void addNode(IntermediateEvent i) {
        Color color;
        if (i.getLabel().contains("OR"))
            color = YELLOW;
        else if (i.getLabel().contains("SEQAND"))
            color = GOLD;
        else if (i.getLabel().contains("AND"))
            color = BLUE;
        else color = ORANGE;
        for (int j = 0; j < i.getChildren().size(); j++)
            graph.add(mutNode(i.getLabel()).add(color).addLink(mutNode(i.getChildren().get(j).getLabel())));
    }

    public void printGraph() {
        System.out.println("*** Avvio salvataggio in formato png del modello impostato ***");
        try {
            Graphviz.fromGraph(graph).height(1000).width(4000).render(Format.PNG).toFile(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("*** Modello esportato nell directory " + path + " ***");
    }

    public void printCIInfo(boolean premade, int nBasics, float maxTime, int runs, float quantum, float alpha, boolean meanPlot, boolean faultPlot) {
        System.out.println();

        System.out.println("*** Avvio definizione intervallo di confidenza per il valore atteso dello stato di funzionamento del Top Event del seguente modello di SMFT ***");
        // entrambe utilizzano questa
        printGeneralInfo(premade, nBasics, maxTime, runs, quantum);
        System.out.println("Livello di significatività: " + alpha);
        if (meanPlot)
            System.out.println("Richiesta stampa della media campionaria dello stato di funzionamento");
        if (faultPlot)
            System.out.println("Richiesta stampa della media campionaria dello stato di mal-funzionamento");

        System.out.println();
    }

    public void printErgodicInfo(boolean premade, int nBasics, float maxTime, int runs, float quantum, double meanPrecision, double varPrecision) {
        System.out.println();

        System.out.println("*** Avvio verifica ergodicità del seguente modello di SMFT ***");
        // entrambe utilizzano questa
        printGeneralInfo(premade, nBasics, maxTime, runs, quantum);
        System.out.println("Margine di errore ammissibile per la valutazione della convergenza della media campionaria: " + meanPrecision);
        System.out.println("Margine superiore di accettazione per la varianza campionaria: " + varPrecision);

        System.out.println();
    }

    // utilizano questa
    public void printGeneralInfo(boolean premade, int nBasics, float maxTime, int runs, float quantum) {
        if (premade)
            System.out.println("Modello pre-costruito");
        else
            System.out.println("Modello creato in modo casuale con " + nBasics + " foglie");
        System.out.println("Max time della simulazione: " + maxTime);
        System.out.println("Numero di runs: " + runs);
        System.out.println("Passo di quantizzazione: " + quantum);
    }


}
