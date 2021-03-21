package src.swe.smft.utilities;

public class Pair<S, T> {
    private S element1;
    private T element2;

    public Pair(S s, T t) {
        element1 = s;
        element2 = t;
    }

    public S getElement1() {
        return element1;
    }

    public T getElement2() {
        return element2;
    }

}
