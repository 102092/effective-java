package com.effective.java.ch11.item79;

/**
 * @author han
 */
@FunctionalInterface public interface SetObserver<E> {

    void added(ObservableSet<E> set, E element);
}
