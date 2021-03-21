package src.swe.smft.harryplotter;

/*
// NOTAZIONE PARENTISIZZATA
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Style;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.Pseudograph;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.IntermediateEvent;

import static guru.nidi.graphviz.attribute.Attributes.attr;
import static guru.nidi.graphviz.attribute.Rank.RankDir.BOTTOM_TO_TOP;
import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Link.to;

public class GraphBuilder {
    private static final Graph<String, DefaultEdge> g = new Pseudograph<>(DefaultEdge.class);


    public static void addNode(BasicEvent e) {
        g.addVertex(e.getLabel());
        System.out.println(g.toString());
        System.out.println();
    }

    public static void addNodeAndEdges(IntermediateEvent i) {
        g.addVertex(i.getLabel());
        for (Event e : i.getChildren()) {
            g.addEdge(i.getLabel(), e.getLabel());
            System.out.println(g.toString());
            System.out.println();

        }
    }

    public void printGraph() {
    }
}
*/

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
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
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;


public class HarryPlotter {
    // private static final int width = 1000;
    // private static final int height = 700;
    private static HarryPlotter mainCharacter = null;
    private static final MutableGraph g = mutGraph("SMFT").setDirected(true);
    // TODO i chart dovrebbero essere static
    private final XYChart chartCI = new XYChartBuilder().width(1200).height(800).title("Confidence Intervals").xAxisTitle("times").yAxisTitle("CI").build();
    private final XYChart chartErgodic = new XYChartBuilder().width(600).height(400).title("Ergodic Nature").xAxisTitle("times").yAxisTitle("value").build();


    private HarryPlotter(){}

    public static HarryPlotter getInstance() {
        if (mainCharacter != null)
            return mainCharacter;
        mainCharacter = new HarryPlotter();
        return mainCharacter;
    }

    public void plotReliability(double[] times, double[][] CI, double[] sampleMean, boolean meanPlot, boolean fault) {

        // Customize Chart
        chartCI.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chartCI.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        // chartCI.getStyler().setYAxisMax(1d);
        chartCI.getStyler().setZoomEnabled(true);

        // Series
        XYSeries series = chartCI.addSeries("lower bound", times, CI[0]);
        series.setMarker(SeriesMarkers.NONE);
        if (meanPlot) {
            series = chartCI.addSeries("sample mean Reliability", times, sampleMean);
            series.setMarker(SeriesMarkers.NONE);
        }
        if(fault) {
            double[] faults = new double[sampleMean.length];
            for (int i = 0; i<sampleMean.length; i++)
                faults[i] = 1 - sampleMean[i];
                series = chartCI.addSeries("sample mean Fault", times, faults);
                series.setMarker(SeriesMarkers.NONE);
        }
        series = chartCI.addSeries("upper bound", times, CI[1]);
        series.setMarker(SeriesMarkers.NONE);

        new SwingWrapper(chartCI).displayChart();
    }

    public void plotErgodic(double[] times, double[] sampleMean, double[] sampleVariance) {

        // Customize Chart
        chartErgodic.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chartErgodic.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chartErgodic.getStyler().setZoomEnabled(true);
        chartCI.getStyler().setYAxisMax(1d);

        // Series
        XYSeries series = chartErgodic.addSeries("sampleMean", times, sampleMean);
        series.setMarker(SeriesMarkers.NONE);
        series = chartErgodic.addSeries("sampleVariance", times, sampleVariance);
        series.setMarker(SeriesMarkers.NONE);

        new SwingWrapper(chartErgodic).displayChart();
    }

    public static void addNode(BasicEvent e) {
        Color color;
        if (e.isWorking())
            color = GREEN;
        else
            color = RED;
        g.add(mutNode(e.getLabel()).add(color));
    }

    public static void addNodeAndEdges(IntermediateEvent i) {
        Color color;
        if (i.getLabel().contains("OR"))
            color = YELLOW;
        else if (i.getLabel().contains("SEQAND"))
            color = GOLD;
        else if (i.getLabel().contains("AND"))
            color = BLUE;
        else color = ORANGE;
        for (int j = 0; j < i.getChildren().size(); j++)
            g.add(mutNode(i.getLabel()).add(color).addLink(mutNode(i.getChildren().get(j).getLabel())));
    }

    public void printGraph() {
        try {
            Graphviz.fromGraph(g).height(1000).width(4000).render(Format.PNG).toFile(new File("example/modelloSMFT.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO fammi sapere se ti piacciono, nel caso ti piacciano, io le terrei qui in harry plotter
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

        return;
    }

    public void printErgodicInfo(boolean premade, int nBasics, float maxTime, int runs, float quantum, double meanPrecision, double varPrecision) {
        System.out.println();

        System.out.println("*** Avvio verifica ergodicità del seguente modello di SMFT ***");
        // entrambe utilizzano questa
        printGeneralInfo(premade, nBasics, maxTime, runs, quantum);
        System.out.println("Margine di errore ammissibile per la valutazione della convergenza della media campionaria: " + meanPrecision);
        System.out.println("Margine superiore di accettazione per la varianza campionaria: " + varPrecision);

        System.out.println();

        return;
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
