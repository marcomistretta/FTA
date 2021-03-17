package src.swe.smft.event;

// interfaccia per BasicEvent e per IntermediateEvent
public interface Event {
    boolean isWorking();

    float reset();

    float randomReset();

    String getLabel();

    void setLabel(int c);
}


