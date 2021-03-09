package src.swe.smft.event;

public class BasicEvent implements Event {
    private float lambda;
    private float mu;
    private boolean status;

    public BasicEvent(float lambda, float mu, boolean status) {
        this.lambda = lambda;
        this.mu = mu;
        this.status = status;
    }

    public BasicEvent(float lambda, float mu) {
        this.lambda = this.lambda;
        this.mu = this.mu;
        this.status = true;
    }

    public float getLambda() {
        return lambda;
    }

    public void setLambda(float lambda) {
        this.lambda = lambda;
    }

    public float getMu() {
        return mu;
    }

    public void setMu(float mu) {
        this.mu = mu;
    }


    public void setStatus(boolean status) {
        this.status = status;
    }

    /* getStatus */
    @Override
    public boolean isWorking() {
        return status;
    }

    public float getP() {
        if (isWorking())
            return getLambda();
        else
            return getMu();
    }

    public void toggle() {
        setStatus(!isWorking());
    }
}
