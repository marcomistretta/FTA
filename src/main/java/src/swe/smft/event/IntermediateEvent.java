/* new */
package src.swe.smft.event;

import java.util.ArrayList;

public abstract class IntermediateEvent implements Event {
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

    @Override
    public void reset() {
        for (Event child : children) {
            child.reset();
        }
    }

    @Override
    public void randomReset() {
        for (Event child : children) {
            child.reset();
        }
    }
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
        this.K = k;
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

