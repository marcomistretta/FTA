package src.swe.smft.utilities;

public class Pair<T, S> {
    private T element1;
    private S element2;

    public Pair(T t, S s) {
        element1 = t;
        element2 = s;
    }

    public T getElement1() {
        return element1;
    }

    public S getElement2() {
        return element2;
    }
}
