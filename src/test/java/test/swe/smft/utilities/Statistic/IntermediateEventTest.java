package test.swe.smft.utilities.Statistic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.swe.smft.event.BasicEvent;
import src.swe.smft.event.Event;
import src.swe.smft.event.EventFactory;
import src.swe.smft.event.IntermediateEvent;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.ArrayList;
import java.util.Base64;

public class IntermediateEventTest {

    private ArrayList<Event> children;

    @BeforeEach
    public void setUp() {
        children = new ArrayList<>();
        //parametri irrilevanti, le commutazioni le forzo
        children.add(EventFactory.getInstance().createBasicEvent(.1f, .1f, true));
        children.add(EventFactory.getInstance().createBasicEvent(.1f, .1f, true));
        children.add(EventFactory.getInstance().createBasicEvent(.1f, .1f, true));
    }

    @Test
    @DisplayName("And test")
    void AndTest() {
        Event gate = EventFactory.getInstance().createIntermediateEvent(children, "AND");
        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(0)).setStatus(false);
        assertEquals(gate.isWorking(), false);
        ((BasicEvent) children.get(0)).setStatus(true);
        ((BasicEvent) children.get(1)).setStatus(false);
        assertEquals(gate.isWorking(), false);
        ((BasicEvent) children.get(1)).setStatus(true);
        ((BasicEvent) children.get(2)).setStatus(false);
        assertEquals(gate.isWorking(), false);
    }

    @Test
    @DisplayName("SeqAnd test")
    void SeqAndTest() {

        // la porta funziona su l'ipotesi che si guasti al più un componente a controllo

        Event gate = EventFactory.getInstance().createIntermediateEvent(children, "SEQAND");
        assertEquals(gate.isWorking(), true);

        ((BasicEvent) children.get(0)).setStatus(false);
        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(1)).setStatus(false);
        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(2)).setStatus(false);
        assertEquals(gate.isWorking(), false);

        ((BasicEvent) children.get(0)).setStatus(true);
        ((BasicEvent) children.get(1)).setStatus(true);
        ((BasicEvent) children.get(2)).setStatus(true);

        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(2)).setStatus(false);
        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(1)).setStatus(false);
        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(0)).setStatus(false);
        assertEquals(gate.isWorking(), true);

    }

    @Test
    @DisplayName("Or test")
    void OrTest() {

        // la porta funziona su l'ipotesi che si guasti al più un componente a controllo

        Event gate = EventFactory.getInstance().createIntermediateEvent(children, "OR");
        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(0)).setStatus(false);
        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(1)).setStatus(false);
        assertEquals(gate.isWorking(), true);
        ((BasicEvent) children.get(2)).setStatus(false);
        assertEquals(gate.isWorking(), false);

    }
    @Test
    @DisplayName("K/N test")
    void knTest() {
        children.add(EventFactory.getInstance().createBasicEvent(.1f, .1f, true));
        children.add(EventFactory.getInstance().createBasicEvent(.1f, .1f, true));

        Event gate = EventFactory.getInstance().createIntermediateEvent(children, "2");
        int i;
        for(i = 0; i < 3; i++){
            ((BasicEvent) children.get(i)).setStatus(false);
            assertEquals(true, gate.isWorking());
        }
        ((BasicEvent) children.get(i)).setStatus(false);
        assertEquals(false, gate.isWorking());

    }


}
