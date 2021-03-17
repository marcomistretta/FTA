package src.swe.smft.event;

// interfaccia per BasicEvent e per IntermediateEvent
public interface Event {
    boolean isWorking();

    void reset();

    void randomReset();

    String getLabel();

    void setLabel(int c);
}


