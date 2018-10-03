package code4ants.forgedmocks.commons;

public interface SelectionPolicy<T> {

    boolean hasNext();

    T next();
}
