package src.swe.smft.graph;

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

public class GraphBuilder {

    private static MutableGraph g;

    public GraphBuilder() {
        g = mutGraph("SMFT").setDirected(true);
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
