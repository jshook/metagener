package com.metawiring.generation.stackdouble;

import com.metawiring.annotations.Input;
import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.DoubleStackAware;
import com.metawiring.types.functiontypes.GenericFieldFunction;

import java.util.Stack;

@Input(Double.class)
@Output(Double.class)
public class StackPushDouble implements GenericFieldFunction<Double,Double>, DoubleStackAware {

    private Stack<Double> stack;

    @Override
    public void applyDoubleStack(Stack<Double> doubleStack) {
        this.stack = doubleStack;
    }

    @Override
    public Double apply(Double aDouble) {
        stack.push(aDouble);
        return aDouble;
    }
}
