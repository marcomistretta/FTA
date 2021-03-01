package src.swe.smft.event;

import java.util.ArrayList;

/* TODO check if interface or abstract */
public interface Event {
    public boolean isWorking();
}

class BasicEvent implements Event {
    private float lambda;
    private float mu;
    private boolean status;

    public BasicEvent(float lambda, float mu, boolean status) {
        this.lambda = lambda;
        this.mu = mu;
        this.status = status;
    }

    public BasicEvent(float lambda, float mu) {
        this.lambda = lambda;
        this.mu = mu;
        this.status = true;
    }

    public float getLambda() {
        return lambda;
    }

    public void setLambda(float lambda) {
        this.lambda = lambda;
    }

    public float getMu() {
        return mu;
    }

    public void setMu(float mu) {
        this.mu = mu;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    /* getStatus */
    @Override
    public boolean isWorking() {
        return status;
    }

    public float getP() {
        if (isWorking())
            return getLambda();
        else
            return getMu();
    }

    public void toggle() {
        setStatus(!isWorking());
    }
}

/* TODO check if its abstract */
abstract class IntermediateEvent implements Event {
    private ArrayList<Event> children;

    public IntermediateEvent(ArrayList<Event> children) {
        this.children = children;
    }

    public ArrayList<Event> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Event> children) {
        this.children = children;
    }

    public abstract boolean isWorking();
}

class AndGate extends IntermediateEvent {

    public AndGate(ArrayList<Event> children) {
        super(children);
    }

    @Override
    public boolean isWorking() {
        boolean result = true;
        for (Event e : getChildren()) {
            if (!e.isWorking()) {
                result = false;
                break;
            }
        }
        return result;
    }
}

class OrGate extends IntermediateEvent {

    public OrGate(ArrayList<Event> children) {
        super(children);
    }

    @Override
    public boolean isWorking() {
        boolean result = false;
        for (Event e : getChildren()) {
            if (e.isWorking()) {
                result = true;
                break;
            }
        }
        return result;
    }
}

class KNGate extends IntermediateEvent {
    private int K;

    public KNGate(ArrayList<Event> children, int k) {
        super(children);
        K = k;
    }

    @Override
    public boolean isWorking() {
        boolean result = false;
        int count = 0;
        for (int i = 0; i < getChildren().size(); i++) {
            if (getChildren().get(i).isWorking())
                count++;
            if (count >= K) {
                result = true;
                break;
            }
        }
        return result;
    }

}

/* my observer */
abstract class MyObserver {
    public abstract void update();
}

/* my subject */
abstract class MySubject {
    private ArrayList<MyObserver> observers = new ArrayList<MyObserver>();

    public void attach(MyObserver o) {
        observers.add(o);
    }

    /* detach() is not necessary at the moment */
    /* private void detach(MyObserver o){}; */

    public void notifyAllObservers() {
        for (MyObserver o : observers)
            o.update();
    }

}

/* concrete observer class */
class EventManager extends MyObserver {
    /* TODO check */
    /* start Observer stuff */
    private Modeler concreteSubject;

    public EventManager(Modeler m) {
        this.concreteSubject = m;
        this.concreteSubject.attach(this);
    }

    @Override
    public void update() {
        /* TODO check, i make it a local variable pt1 */
        BasicEvent observerState = concreteSubject.getSubjectState();
        basicEvents.add(observerState);
    }

    /* TODO check, i make it a local variable pt2 */
    /* private BasicEvent observerState; */
    /* end Observer stuff */

    /* start EventManager stuff */
    private ArrayList<BasicEvent> basicEvents;

    public EventManager(ArrayList<BasicEvent> basicEvents) {
        this.basicEvents = basicEvents;
    }

    public void nextToggle() {
        ArrayList<Float> pList = calculateP();
        int choose = sample(pList);
        basicEvents.get(choose).toggle();

    }

    public ArrayList<Float> calculateP() {
        ArrayList<Float> pList = new ArrayList<Float>();
        for (BasicEvent b : basicEvents) {
            pList.add(b.getP());
        }
        return pList;
    }

    public int sample(ArrayList<Float> pList) {
        /* will return -1 in case of error */
        int choose = -1;

        float rand = (float) Math.random();
        float sum = 0;
        for (float p : pList) {
            sum += p;
        }
        float sample = rand * sum;
        for (int i = 0; i <= basicEvents.size(); i++) {
            float adder = 0;
            if (sample <= (pList.get(i) + adder))
                choose = i;
            else
                adder += pList.get(i);
        }

        return choose;
    }

    /* TODO will take a list of triplets (lambda, mu, status) to initialize basic events */
    public void initialize() {}
}

