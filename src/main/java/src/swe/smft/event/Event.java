package src.swe.smft.event;

public interface Event {
    boolean isWorking();

    void reset();

    void randomReset();

    // TODO alla fine non ho resistito:
    // ho aggiunto l'ayytibuto String label
    void setLabel(int count);

    String getLabel();
}


