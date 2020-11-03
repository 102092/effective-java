package com.effective.java.ch4.item20;

/**
 * @author han
 */
public class ImplementationFoo implements Foo {


    @Override
    public String hello() {
        return new InnerFoo().hello();
    }

    class InnerFoo extends ImplementationFoo {

        public String hello () {
            return "hello";
        }
    }
}
