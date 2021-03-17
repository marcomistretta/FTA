package src.swe.smft.event;

// interfaccia per BasicEvent e per IntermediateEvent
public interface Event {
    boolean isWorking();

    String getLabel();

    void setLabel(int count);
}


