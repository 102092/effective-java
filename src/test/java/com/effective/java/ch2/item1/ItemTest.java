package com.effective.java.ch2.item1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ItemTest {

    @DisplayName("Boolean은 불변 클래스임을 확인한다.")
    @Test
    void test() {
        Boolean b1 = Boolean.valueOf(true);
        Boolean b2 = Boolean.valueOf("true");
        Boolean b3 = new Boolean(true);

        assertThat(b1).isSameAs(b2);
        assertThat(b1).isNotSameAs(b3);
    }
}
