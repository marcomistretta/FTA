/* new */
package src.swe.smft.program;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.EventManager;
import src.swe.smft.event.EventModeler;
import src.swe.smft.memory.DataCentre;
import src.swe.smft.utilities.Statistic;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        System.out.println("Progetto di Casciaro & Mistretta");

        /* simple case */
        /* twp basic events: A, B */
        /* A && B = C */
        /* start Gate & Events Testing */
        EventManager em = new EventManager();
        EventModeler modeler = new EventModeler(em);

        float lambdaA = 0.7f;
        float muA = 0.3f;
        boolean statusA = false;
        BasicEvent A = modeler.createBasicEvent(lambdaA, muA, statusA);

        float lambdaB = 0.4f;
        float muB = 0.6f;
        boolean statusB = false;
        BasicEvent B = modeler.createBasicEvent(lambdaB, muB, statusB);

        ArrayList<Event> children = new ArrayList<Event>();
        children.add(A);
        children.add(B);
        char opz = 'O';
        Event C = modeler.createIntermediateEvent(children, opz);
        // System.out.println(C.isWorking());
        /* end Gate & Events Testing */

        /* TODO ema abbiamo un problema:
        ma forse ho risolto
         */
        ArrayList<BasicEvent> childrenBasic = new ArrayList<BasicEvent>();
        childrenBasic.add(A);
        childrenBasic.add(B);
        Simulator simulator = new Simulator(1000, C, em);
        DataCentre dc = new DataCentre();
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        Statistic s = new Statistic(dc.quantizedData(100));
        s.confidenceInterval();
        double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

        // Create Chart
        XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        chart.addSeries("Y2", new double[]{0.0, 1.0, 2.0}, new double[]{0.0, 1.0, 2.0});

        // Show it
        new SwingWrapper(chart).displayChart();

    }

}
//public static void main(String[] args) {
//
//    }
//}

