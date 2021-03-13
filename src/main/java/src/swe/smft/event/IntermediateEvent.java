package src.swe.smft.event;

import java.util.ArrayList;

public abstract class IntermediateEvent implements Event {
    private ArrayList<Event> children;
    String opz;

    public IntermediateEvent(ArrayList<Event> children, String opz) {
        this.children = children;
        this.opz = opz;
    }

    @Override
    public ArrayList<Event> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Event> children) {
        this.children = children;
    }

    public abstract boolean isWorking();

    @Override
    public String getLabel() {
        String type;
        if (!opz.equals("AND") && !opz.equals("OR"))
            type = opz + "/" + getChildren().size();
        else
            type = opz;
        return type;
    }

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

    public AndGate(ArrayList<Event> children, String opz) {
        super(children, opz);
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

    public OrGate(ArrayList<Event> children, String opz) {
        super(children, opz);
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
    private final int K;

    public KNGate(ArrayList<Event> children, int k, String opz) {
        super(children, opz);
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

