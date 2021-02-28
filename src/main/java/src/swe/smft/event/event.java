package src.swe.smft.event;

import java.util.ArrayList;

interface Event {
    /* TODO check */
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean isWorking() {
        return status;
    }
}

class IntermediateEvent implements Event {
    private ArrayList<Event> children;
    private boolean status;

    public IntermediateEvent(ArrayList<Event> children, boolean status) {
        this.children = children;
        this.status = status;
    }

    public ArrayList<Event> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Event> children) {
        this.children = children;
    }

    @Override
    public boolean isWorking() {
        return status;
    }
}

class AndGate extends IntermediateEvent {

    public AndGate(ArrayList<Event> children, boolean status) {
        super(children, status);
    }

    @Override
    public boolean isWorking() {
        boolean result = true;
        for (int i = 0; i < getChildren().size(); i++) {
            if (!getChildren().get(i).isWorking()) {
                result = false;
                break;
            }
        }
        return result;
    }
}

class OrGate extends IntermediateEvent {

    public OrGate(ArrayList<Event> children, boolean status) {
        super(children, status);
    }

    @Override
    public boolean isWorking() {
        boolean result = false;
        for (int i = 0; i < getChildren().size(); i++) {
            if (getChildren().get(i).isWorking()) {
                result = true;
                break;
            }
        }
        return result;
    }
}

class KNGate extends IntermediateEvent {
    private int K;

    public KNGate(ArrayList<Event> children, boolean status, int k) {
        super(children, status);
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

