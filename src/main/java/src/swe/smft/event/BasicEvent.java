package src.swe.smft.event;

import jdk.jshell.spi.SPIResolutionException;

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
        this.lambda = lambda;
        this.mu = mu;
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


    public float setStatus(boolean status) {
        float prev = getP();
        this.status = status;
        return getP() - prev;
    }

    /* getStatus */
    @Override
    public boolean isWorking() {
        return status;
    }

    @Override
    public float reset() {
        return setStatus(true);
    }

    @Override
    public float randomReset() {
        return setStatus(Math.random() >= 0.5d);
    }

    @Override
    public String getLabel() {
        return isWorking() + "(λ:" + getLambda() + ", μ:" + getMu() + ")";
    }

    public float getP() {
        if (isWorking())
            return getLambda();
        else
            return getMu();
    }

    public float toggle() {
        setStatus(!isWorking());
        //status true => prima era off: - mu + lambda
        return status ? lambda - mu : mu - lambda;
    }
}
