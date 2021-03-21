package src.swe.smft.event;

import java.util.ArrayList;
import java.util.List;

public abstract class IntermediateEvent implements Event {

    private List<Event> children;
    private String opz;
    private String label;

    public IntermediateEvent(List<Event> children, String opz, int count) {
        this.children = children;
        this.opz = opz;
        setLabel(count);
    }

    public List<Event> getChildren() {
        return children;
    }

    public void setChildren(List<Event> children) {
        this.children = children;
    }

    public abstract boolean isWorking();

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    // "AND" "OR" "int"
    public void setLabel(int count) {
        if (!opz.equals("AND") && !opz.equals("OR") && !opz.equals("SEQAND"))
            label = count + ": " + opz + "/" + getChildren().size();
        else
            label = count + ": " + opz;
    }

}

// "AND"
class AndGate extends IntermediateEvent {

    public AndGate(List<Event> children, String opz, int count) {
        super(children, opz, count);
    }

    @Override
    public boolean isWorking() {
        boolean result = true;
        for (Event e : getChildren()) {
            if (!e.isWorking()) {
                result = false;
                break;
            }
        }
        return result;
    }
}

// "OR"
class OrGate extends IntermediateEvent {

    public OrGate(List<Event> children, String opz, int count) {
        super(children, opz, count);
    }

    @Override
    public boolean isWorking() {
        boolean result = false;
        for (Event e : getChildren()) {
            if (e.isWorking()) {
                result = true;
                break;
            }
        }
        return result;
    }
}

// "K" con String K = "un numero intero"
class KNGate extends IntermediateEvent {
    private final int K;

    public KNGate(List<Event> children, int k, String opz, int count) {
        super(children, opz, count);
        this.K = k;
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

class SeqAndGate extends AndGate {
    // generalizza l'and prioritario

    // l'and sequenziale propaga il guasto solo se avviene in un certo ordine
    // si può supporre senza perdere di generalità che l'ordine di propagazione sia sempre
    // l'ordine di inserimento dei figli, dato che esso è sempre influezabile

    private ArrayList<Event> faultSequence;

    public SeqAndGate(List<Event> children, String opz, int count) {
        super(children, opz, count);
        faultSequence = new ArrayList<>();
    }

    @Override
    public boolean isWorking() {
        for(Event event: getChildren()) {
            int index = faultSequence.indexOf(event);
            if(event.isWorking() && index != -1)
                //funziona, va tolto dalla lista
                faultSequence.remove(index);
            else if(!event.isWorking() && index == -1)
                //è rotto e manc in lista, aggiungere in fondo
                faultSequence.add(event);
        }

        if(faultSequence.size() != getChildren().size()) return true;
        for(int i = 0; i < getChildren().size(); i++)
            if(getChildren().get(i) != faultSequence.get(i))
                return true;

        return  false;
    }
}
