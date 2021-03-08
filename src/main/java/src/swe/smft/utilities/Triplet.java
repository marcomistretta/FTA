package src.swe.smft.utilities;

public class Triplet<T, S, U> {
    private T element1;
    private S element2;
    private U element3;

    public Triplet(T t, S s, U u) {
        element1 = t;
        element2 = s;
        element3 = u;
    }

    public T getElement1() {
        return element1;
    }

    public S getElement2() {
        return element2;
    }

    public U getElement3() {
        return element3;
    }
}
