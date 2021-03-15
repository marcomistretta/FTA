package src.swe.smft.event;

import java.util.List;

public abstract class IntermediateEvent implements Event {
    private List<Event> children;
    String opz;

    public IntermediateEvent(List<Event> children, String opz) {
        this.children = children;
        this.opz = opz;
    }

    public List<Event> getChildren() {
        return children;
    }

    public void setChildren(List<Event> children) {
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
    public float reset() {
        float offset = 0;
        for (Event child : children) {
            offset += child.reset();
        }
        return offset;
    }

    @Override
    public float randomReset() {
        float offset = 0;
        for (Event child : children) {
            offset += child.reset();
        }
        return offset;
    }
}


class AndGate extends IntermediateEvent {

    public AndGate(List<Event> children, String opz) {
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

    public OrGate(List<Event> children, String opz) {
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

    public KNGate(List<Event> children, int k, String opz) {
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

