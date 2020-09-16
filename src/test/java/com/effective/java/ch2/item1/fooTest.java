package com.effective.java.ch2.item1;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author han
 */
class fooTest {

    @DisplayName("동반 클래스를 만들어서 정적 메서드를 정의한다.")
    @Test
    void test() {
        Animal animal = Dog.howling();
        System.out.println(animal.Howling());
    }
}