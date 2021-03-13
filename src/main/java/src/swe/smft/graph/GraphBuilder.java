package src.swe.smft.graph;

/*
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.IntermediateEvent;

public class GraphBuilder {

    private static Graph graph = new SingleGraph("SMFT");
    private static boolean initialize = true;

    public static void startInitialization(){
        System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.display();
    }
    public static void addNode(BasicEvent e) {
        if(initialize) {
            startInitialization();
            initialize = false;
        }

        graph.addNode(e.getLabel());
    }

    public static void addNodeAndEdges(IntermediateEvent i) {
        if(initialize) {
            startInitialization();
            initialize = false;
        }

        graph.addNode(i.getLabel());
        for(Event e : i.getChildren()){
            graph.addNode(e.getLabel());
            graph.addEdge(i.getLabel()+e.getLabel(), i.getLabel(), e.getLabel(), true);
        }
    }
}
*/

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.IntermediateEvent;

public class GraphBuilder {
    private static final Graph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);


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


}