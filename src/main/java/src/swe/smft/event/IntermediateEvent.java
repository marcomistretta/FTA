package src.swe.smft.event;

import java.util.ArrayList;

/* TODO check if its abstract */
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
}
