package com.effective.java.ch2.item9;

/**
 * @author han
 */
public class AutoCloseableResourcesFirst implements AutoCloseable {

    public AutoCloseableResourcesFirst() {
        System.out.println("Constructor -> AutoCloseableResources_First");
    }

    public void doSomething() {
        System.out.println("Something -> AutoCloseableResources_First");
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closed AutoCloseableResources_First");
    }
}
