package src.swe.smft.event;

// N.B. convenzione:
// status = true <=> "funzionamento"
// status = false <=> "malfunzionamento"

public class BasicEvent implements Event {
    private float lambda;
    private float mu;
    private boolean status;
    private boolean firstStatus;
    private String label;

    // per capire lo scopo e utilizzo di int count vedi EventModeler.createBasicEvent();
    public BasicEvent(float lambda, float mu, boolean status, int count) {
        this.lambda = lambda;
        this.mu = mu;
        this.status = status;
        firstStatus = status;
        // alla creazione viene in modo automatico assegnata un etichetta
        setLabel(count);
    }

    // in caso di mancata specifica
    // di default un BasicEvent si considera inizialmente funzionante
    public BasicEvent(float lambda, float mu, int count) {
        this.lambda = lambda;
        this.mu = mu;
        this.status = true;
        setLabel(count);
    }

    public float getLambda() {
        return lambda;
    }

    public float getMu() {
        return mu;
    }

    public void setLambda(float lambda) {
        this.lambda = lambda;
    }

    public void setMu(float mu) {
        this.mu = mu;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // getStatus
    @Override
    public boolean isWorking() {
        return status;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(int count) {
        label = count + ": (λ:" + getLambda() + ", μ:" + getMu() + ")";
    }

    @Override
    // TODO non dovrebbe mettere al primo stato???
    public void reset() {
        setStatus(firstStatus);
    }

    @Override
    public void randomReset() {
        setStatus(Math.random() >= 0.5d);
    }

    public float getP() {
        if (isWorking())
            return getLambda();
        else
            return getMu();
    }

    public float toggle() {
        setStatus(!isWorking());
        return status ? lambda - mu : mu - lambda;
    }
}
