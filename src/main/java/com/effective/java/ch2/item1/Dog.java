package com.effective.java.ch2.item1;

/**
 * @author han
 */
public class Dog {

    private Dog() { }

    public static Animal howling() {
        return new Animal() {
            @Override
            public String Howling() {
                return "bark! bark";
            }
        };
    }
}
