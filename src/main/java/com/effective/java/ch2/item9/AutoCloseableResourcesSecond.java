package com.effective.java.ch2.item9;

/**
 * @author han
 */
public class AutoCloseableResourcesSecond implements AutoCloseable {

    public AutoCloseableResourcesSecond() {
        System.out.println("Constructor -> AutoCloseableResources_Second");
    }

    public void doSomething() {
        System.out.println("Something -> AutoCloseableResources_Second");
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closed AutoCloseableResources_Second");
    }
}
