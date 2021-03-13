package src.swe.smft.event;

import java.util.ArrayList;

public interface Event {
    boolean isWorking();

    void reset();

    void randomReset();

    String getLabel();

    ArrayList<Event> getChildren();
}


