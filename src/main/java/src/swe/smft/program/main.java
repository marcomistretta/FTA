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
import src.swe.smft.utilities.Pair;
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

        float lambdaD = 0.7f;
        float muD = 0.3f;
        boolean statusD = true;
        BasicEvent D = modeler.createBasicEvent(lambdaD, muD, statusD);

        float lambdaB = 0.4f;
        float muB = 0.6f;
        boolean statusB = true;
        BasicEvent B = modeler.createBasicEvent(lambdaB, muB, statusB);

        ArrayList<Event> children = new ArrayList<Event>();
        children.add(A);
        children.add(B);
        children.add(D);
        char opz = 'O';
        Event C = modeler.createIntermediateEvent(children, opz);
        // System.out.println(C.isWorking());
        /* end Gate & Events Testing */

        // TODO fix RESET dei basic event a od ogni simulazione
        Simulator simulator0 = new Simulator(1000, C, em);
        Simulator simulator1 = new Simulator(1000, C, em);
        Simulator simulator2 = new Simulator(1000, C, em);
        Simulator simulator3 = new Simulator(1000, C, em);
        Simulator simulator4 = new Simulator(1000, C, em);
        Simulator simulator5 = new Simulator(1000, C, em);
        Simulator simulator6 = new Simulator(1000, C, em);


        DataCentre dc = new DataCentre();
        dc.appendData(simulator0.simulation());
        dc.appendData(simulator1.simulation());
        dc.appendData(simulator2.simulation());
        dc.appendData(simulator3.simulation());
        dc.appendData(simulator4.simulation());
        dc.appendData(simulator5.simulation());
        dc.appendData(simulator6.simulation());


        float alpha = 0.05f;
        float quantum = 5;
        ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults = dc.quantizedData(quantum);
        Statistic s = new Statistic(quantizedResults, alpha);
        double[][] CI;
        CI = s.confidenceInterval();
        int l = CI[0].length;
        double time = 0;
        double[] xData = new double[l];
        for (int i = 0; i < l; i++) {
            xData[i] = time;
            time = time + quantum;
        }
        /*
        for (int i = 0; i < l; i++) {
            System.err.println(xData[i]);
        }
        */

        String[] CInames = new String[2];
        CInames[0] = "Lower Bound";
        CInames[1] = "Upper Bound";
        XYChart chart = QuickChart.getChart("Confidence Interval", "times", "CI", CInames, xData, CI);
        //chart.addSeries("Upper Bound", xData, yUpper);
        //chart.addSeries("Upper Bound", yUpper);
        new SwingWrapper(chart).displayChart();


    }

}
//public static void main(String[] args) {
//
//    }
//}

