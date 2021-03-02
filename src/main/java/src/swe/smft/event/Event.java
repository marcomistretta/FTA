package src.swe.smft.event;

import java.util.ArrayList;

/* TODO check if interface or abstract */
public interface Event {
    boolean isWorking();
}

// TODO basic event serve che sia pubblica, il simulatore deve accedere liberamente alle foglie
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

