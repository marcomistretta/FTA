/* new */

package src.swe.smft.program;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.TreeManager;
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
        TreeManager tm = new TreeManager();
        EventModeler modeler = EventModeler.getInstance();

        float lambdaA = 0.7f;
        float muA = 0.3f;
        boolean statusA = true;
        Event A = modeler.createBasicEvent(lambdaA, muA, statusA);

        float lambdaD = 0.7f;
        float muD = 0.3f;
        boolean statusD = true;
        Event D = modeler.createBasicEvent(lambdaD, muD, statusD);

        float lambdaB = 0.4f;
        float muB = 0.6f;
        boolean statusB = true;
        Event B = modeler.createBasicEvent(lambdaB, muB, statusB);

        ArrayList<Event> children = new ArrayList<Event>();
        children.add(A);
        children.add(B);
        char opz = '2';

        Event C = modeler.createIntermediateEvent(children, opz);
        tm.addBasicEvent((BasicEvent) A);
        tm.addBasicEvent((BasicEvent) D);
        tm.addBasicEvent((BasicEvent) B);



        // System.out.println(C.isWorking());
        /* end Gate & Events Testing */

        Simulator simulator = new Simulator(1000, C, tm);

        DataCentre dc = new DataCentre();
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());

        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());

        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());
        dc.appendData(simulator.simulation());

        try{


            float alpha = 0.05f;
            float quantum = 5;
            ArrayList<ArrayList<Pair<Boolean, ArrayList<Boolean>>>> quantizedResults = dc.quantizedData(quantum, 1000);

            double[][] CI;
            CI = Statistic.confidenceInterval(quantizedResults, alpha);
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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

}
//public static void main(String[] args) {
//
//    }
//}

