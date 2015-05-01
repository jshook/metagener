package com.metawiring.generation;

import com.metawiring.generation.stackdouble.*;
import com.metawiring.generation.stacklong.*;
import org.testng.annotations.Test;

import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

public class StackDoubleTests {

    @Test
    public void testStackPushDouble() {
        StackPushDouble push = new StackPushDouble();
        Stack<Double> stack = new Stack<Double>();
        push.applyDoubleStack(stack);
        push.apply(12.34);
        assertThat(stack.peek()).isEqualTo(12.34);
    }

    @Test
    public void testStackPopDouble() {
        StackPopDouble pop = new StackPopDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(13.57); push(24.68); }};
        pop.applyDoubleStack(stack);
        assertThat(pop.apply(234.432)).isEqualTo(24.68);
    }

    @Test
    public void testStackSwapDouble() {
        StackSwapDouble swap = new StackSwapDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(123.456); push(654.321); }};
        swap.applyDoubleStack(stack);
        assertThat(swap.apply(234.234)).isEqualTo(123.456);
        assertThat(stack.pop()).isEqualTo(123.456);
        assertThat(stack.pop()).isEqualTo(654.321);
    }

    @Test
    public void testStackExchDouble() {
        StackExchangeDouble exch = new StackExchangeDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(123.456); push(654.321); }};
        exch.applyDoubleStack(stack);
        assertThat(exch.apply(234.234)).isEqualTo(654.321);
        assertThat(stack.pop()).isEqualTo(234.234);
        assertThat(stack.pop()).isEqualTo(123.456);
    }

    @Test
    public void testStackClearDouble() {
        StackClearDouble clear = new StackClearDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(123.456); push(654.321); }};
        clear.applyDoubleStack(stack);
        assertThat(clear.apply(112.334)).isEqualTo(112.334);
        assertThat(stack.size()).isEqualTo(0);
    }

    @Test
    public void testStackInitDouble() {
        StackInitDouble init = new StackInitDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(123.456); push(654.321); }};
        init.applyDoubleStack(stack);
        assertThat(init.apply(112.334)).isEqualTo(112.334);
        assertThat(stack.pop()).isEqualTo(112.334);
    }

    @Test
    public void testStackAddDouble() {
        StackAddDouble add = new StackAddDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(123.456); push(654.321); }};
        add.applyDoubleStack(stack);
        assertThat(add.apply(111.111)).isEqualTo(765.432);
        assertThat(stack.pop()).isEqualTo(765.432);
        assertThat(stack.pop()).isEqualTo(123.456);
    }

    @Test
    public void testStackSubtractDouble() {
        StackSubtractDouble sub = new StackSubtractDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(654.321); push(456.456); }};
        sub.applyDoubleStack(stack);
        assertThat(sub.apply(111.111)).isEqualTo(345.345);
        assertThat(stack.pop()).isEqualTo(345.345);
        assertThat(stack.pop()).isEqualTo(654.321);
    }

    @Test
    public void testStackMultiplyDouble() {
        StackMultiplyDouble mul = new StackMultiplyDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(1.2); push(3.4); }};
        mul.applyDoubleStack(stack);
        assertThat(mul.apply(2.0)).isEqualTo(6.8);
        assertThat(stack.pop()).isEqualTo(6.8);
        assertThat(stack.pop()).isEqualTo(1.2);
    }

    @Test
    public void testStackDivideDouble() {
        StackDivideDouble div = new StackDivideDouble();
        Stack<Double> stack = new Stack<Double>() {{ push(7.0); push(4.0); }};
        div.applyDoubleStack(stack);
        assertThat(div.apply(2.0)).isEqualTo(2.0);
        assertThat(stack.pop()).isEqualTo(2.0);
        assertThat(stack.pop()).isEqualTo(7.0);
    }

}
