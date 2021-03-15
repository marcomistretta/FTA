package src.swe.smft.event;

public interface Event {
    boolean isWorking();

    float reset();
    float randomReset();

    String getLabel();

}


