package use_case.helpers;

/**
 * Three-field tuple implementation for getCensored() method.
 * @param <T> first value
 * @param <U> second value
 * @param <V> third value
 */
class Tuple3<T, U, V> {
    private final T first;
    private final U second;
    private final V third;

    Tuple3(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }

    public V getThird() {
        return third;
    }
}
