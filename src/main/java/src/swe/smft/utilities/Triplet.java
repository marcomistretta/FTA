package src.swe.smft.utilities;

public class Triplet<S, T, U> {
    private S element1;
    private T element2;
    private U element3;

    public Triplet(S s, T t, U u) {
        element1 = s;
        element2 = t;
        element3 = u;
    }

    public S getElement1() {
        return element1;
    }

    public T getElement2() {
        return element2;
    }

    public U getElement3() {
        return element3;
    }
}
