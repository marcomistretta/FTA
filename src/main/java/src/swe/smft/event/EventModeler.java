package src.swe.smft.event;
import java.util.ArrayList;

public class EventModeler  {

    private EventManager em;

    public EventModeler(EventManager em) {
        this.em = em;
    }

    public BasicEvent createBasicEvent(float lambda, float mu, boolean status) {
        BasicEvent b = new BasicEvent(lambda, mu, status);
        notifyManager(b);
        return b;
    }

    private void notifyManager(BasicEvent b) {
        em.update(b);
    }

    public Event createIntermediateEvent(ArrayList<Event> children, char opz) {
        if (opz == 'A')
            return new AndGate(children);
        if (opz == 'O')
            return new OrGate(children);
            /* (opz == 'K') */
        else {
            /* TODO check */
            /* int k = (int) (Math.random() * (children.size() - 1) + 1); */
            int k = Character.getNumericValue(opz);
            return new KNGate(children, k);
        }
    }
    /* end Modeler stuff */
}



