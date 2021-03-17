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

    private static final int width = 1000;
    private static final int height = 700;
    private static HarryPlotter mainCharacter = null;
    private static MutableGraph g = mutGraph("SMFT").setDirected(true);
    private final XYChart chartCI = new XYChartBuilder().width(1200).height(800).title("Confidence Intervals").xAxisTitle("times").yAxisTitle("CI").build();
    private final XYChart chartErgodic = new XYChartBuilder().width(600).height(400).title("Ergodic Nature").xAxisTitle("times").yAxisTitle("value").build();


    private HarryPlotter(){}

    public static HarryPlotter getInstance() {
        if (mainCharacter != null)
            return mainCharacter;
        mainCharacter = new HarryPlotter();
        return mainCharacter;
    }

    public void plotReliability(double[] times, boolean meanPlot, double[][] CI, double[] sampleMean) {

        // Customize Chart
        chartCI.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chartCI.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Series
        XYSeries series = chartCI.addSeries("lower bound", times, CI[0]);
        series.setMarker(SeriesMarkers.NONE);
        if (meanPlot) {
            series = chartCI.addSeries("sample mean", times, sampleMean);
            series.setMarker(SeriesMarkers.NONE);
        }
        series = chartCI.addSeries("upper bound", times, CI[1]);
        series.setMarker(SeriesMarkers.NONE);

        new SwingWrapper(chartCI).displayChart();
    }

    public void plotErgodic(double[] times, double[] epsF, double[] differences) {

        // Customize Chart
        chartErgodic.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chartErgodic.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Series
        XYSeries series = chartErgodic.addSeries("differences", times, differences);
        series.setMarker(SeriesMarkers.NONE);
        series = chartErgodic.addSeries("epsilon", times, epsF);
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

}
