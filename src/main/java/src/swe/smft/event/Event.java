package src.swe.smft.event;

import java.util.ArrayList;

/* TODO check if interface or abstract */
public interface Event {
    boolean isWorking();
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

