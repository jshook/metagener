package com.metawiring.generation;

import com.metawiring.generation.stack.*;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StackTests {

    @Test
    public void testStackPush() {
        StackPush push = new StackPush();
        long[] stack = new long[10];
        push.applyLongStack(stack);
        push.applyAsLong(5);
        assertThat(stack[0]).isEqualTo(1);
        assertThat(stack[1]).isEqualTo(5);
    }

    @Test
    public void testStackPop() {
        StackPop pop = new StackPop();
        long[] stack = new long[] { 2, 5, 7 };
        pop.applyLongStack(stack);
        long top7 = pop.applyAsLong(234l);
        assertThat(top7).isEqualTo(7);
        assertThat(stack[0]).isEqualTo(1l);
        long top5 = pop.applyAsLong(3423l);
        assertThat(top5).isEqualTo(5);
        assertThat(stack[0]).isEqualTo(0l);
    }

    @Test
    public void testStackSwap() {
        StackSwap swap = new StackSwap();
        long[] stack = new long[] { 2,3,4};
        swap.applyLongStack(stack);
        long swapped = swap.applyAsLong(23);
        assertThat(swapped).isEqualTo(3);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(stack[1]).isEqualTo(4);
        assertThat(stack[2]).isEqualTo(3);
    }

    @Test
    public void testStackAdd() {
        StackAdd add = new StackAdd();
        long[] stack = new long[] { 3,7,11,19 };
        add.applyLongStack(stack);
        assertThat(add.applyAsLong(3200)).isEqualTo(30);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(add.applyAsLong(3200)).isEqualTo(37);
        assertThat(stack[0]).isEqualTo(1);
    }

    @Test
    public void testStackSubtract() {
        StackSubtract subtract = new StackSubtract();
        long[] stack = new long[] { 3,23,17,11 };
        subtract.applyLongStack(stack);
        assertThat(subtract.applyAsLong(3200)).isEqualTo(6);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(subtract.applyAsLong(3200)).isEqualTo(17);
        assertThat(stack[0]).isEqualTo(1);
    }

    @Test
    public void testStackMultiply() {
        StackMultiply multiply = new StackMultiply();
        long[] stack = new long[] { 3,23,17,11 };
        multiply.applyLongStack(stack);
        assertThat(multiply.applyAsLong(3200)).isEqualTo(187);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(multiply.applyAsLong(3200)).isEqualTo(4301);
        assertThat(stack[0]).isEqualTo(1);
    }

    @Test
    public void testStackDivide() {
        StackDivide divide = new StackDivide();
        long[] stack = new long[] { 3,50,10,3 };
        divide.applyLongStack(stack);
        assertThat(divide.applyAsLong(3200)).isEqualTo(3);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(divide.applyAsLong(3200)).isEqualTo(16);
        assertThat(stack[0]).isEqualTo(1);
    }

    @Test
    public void testStackClear() {
        StackClear clear = new StackClear();
        long[] stack = new long[] { 3,50,10,3 };
        clear.applyLongStack(stack);
        assertThat(stack[0]).isEqualTo(3);
        assertThat(clear.applyAsLong(23433423l)).isEqualTo(23433423l);
        assertThat(stack[0]).isEqualTo(0);
    }

    @Test
    public void testStackExchange() {
        StackExchange exchange = new StackExchange();
        long[] stack = new long[] { 3,50,10,3 };
        exchange.applyLongStack(stack);
        assertThat(stack[0]).isEqualTo(3);
        assertThat(exchange.applyAsLong(23433423l)).isEqualTo(3l);
        assertThat(stack[0]).isEqualTo(3);
        assertThat(stack[3]).isEqualTo(23433423l);
    }



}
