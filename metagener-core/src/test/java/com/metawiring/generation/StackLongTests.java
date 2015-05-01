package com.metawiring.generation;

import com.metawiring.generation.stacklong.*;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StackLongTests {

    @Test
    public void testStackPush() {
        StackPushLong push = new StackPushLong();
        long[] stack = new long[10];
        push.applyLongStack(stack);
        push.applyAsLong(5);
        assertThat(stack[0]).isEqualTo(1);
        assertThat(stack[1]).isEqualTo(5);
    }

    @Test
    public void testStackPop() {
        StackPopLong pop = new StackPopLong();
        long[] stack = new long[] { 2, 5, 7 };
        pop.applyLongStack(stack);
        long top7 = pop.apply(new Object());
        assertThat(top7).isEqualTo(7);
        assertThat(stack[0]).isEqualTo(1l);
        long top5 = pop.apply(new Object());
        assertThat(top5).isEqualTo(5);
        assertThat(stack[0]).isEqualTo(0l);
    }

    @Test
    public void testStackSwap() {
        StackSwapLong swap = new StackSwapLong();
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
        StackAddLong add = new StackAddLong();
        long[] stack = new long[] { 3,7,11,19 };
        add.applyLongStack(stack);
        assertThat(add.applyAsLong(3200)).isEqualTo(30);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(add.applyAsLong(3200)).isEqualTo(37);
        assertThat(stack[0]).isEqualTo(1);
    }

    @Test
    public void testStackSubtract() {
        StackSubtractLong subtract = new StackSubtractLong();
        long[] stack = new long[] { 3,23,17,11 };
        subtract.applyLongStack(stack);
        assertThat(subtract.applyAsLong(3200)).isEqualTo(6);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(subtract.applyAsLong(3200)).isEqualTo(17);
        assertThat(stack[0]).isEqualTo(1);
    }

    @Test
    public void testStackMultiply() {
        StackMultiplyLong multiply = new StackMultiplyLong();
        long[] stack = new long[] { 3,23,17,11 };
        multiply.applyLongStack(stack);
        assertThat(multiply.applyAsLong(3200)).isEqualTo(187);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(multiply.applyAsLong(3200)).isEqualTo(4301);
        assertThat(stack[0]).isEqualTo(1);
    }

    @Test
    public void testStackDivide() {
        StackDivideLong divide = new StackDivideLong();
        long[] stack = new long[] { 3,50,10,3 };
        divide.applyLongStack(stack);
        assertThat(divide.applyAsLong(3200)).isEqualTo(3);
        assertThat(stack[0]).isEqualTo(2);
        assertThat(divide.applyAsLong(3200)).isEqualTo(16);
        assertThat(stack[0]).isEqualTo(1);
    }

    @Test
    public void testStackClear() {
        StackClearLong clear = new StackClearLong();
        long[] stack = new long[] { 3,50,10,3 };
        clear.applyLongStack(stack);
        assertThat(stack[0]).isEqualTo(3);
        assertThat(clear.applyAsLong(23433423l)).isEqualTo(23433423l);
        assertThat(stack[0]).isEqualTo(0);
    }

    @Test
    public void testStackExchange() {
        StackExchangeLong exchange = new StackExchangeLong();
        long[] stack = new long[] { 3,50,10,3 };
        exchange.applyLongStack(stack);
        assertThat(stack[0]).isEqualTo(3);
        assertThat(exchange.applyAsLong(23433423l)).isEqualTo(3l);
        assertThat(stack[0]).isEqualTo(3);
        assertThat(stack[3]).isEqualTo(23433423l);
    }

    @Test
    public void testStackInit() {
        StackInitLong init = new StackInitLong();
        long[] stack = new long[] { 3,50,10,3 };
        init.applyLongStack(stack);
        assertThat(stack[0]).isEqualTo(3);
        assertThat(init.applyAsLong(234l)).isEqualTo(234l);
        assertThat(stack[0]).isEqualTo(1);
        assertThat(stack[1]).isEqualTo(234l);
    }




}
