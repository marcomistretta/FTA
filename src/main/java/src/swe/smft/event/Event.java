package src.swe.smft.event;

public interface Event {
    boolean isWorking();

    void reset();

    void randomReset();

    String getLabel();

    void setLabel(int c);
}


