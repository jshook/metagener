/*
*   Copyright 2015 jshook
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*/
package com.metawiring.generation.fieldgenfuncs;

import com.metawiring.types.functiontypes.TypedFieldFunction;

import java.security.InvalidParameterException;

public class ToScaledDouble implements TypedFieldFunction<Double> {

    private final int fractionalDigits;
    private final double fractionalScale;

    private final double min;
    private final double scaleFactor;

    public ToScaledDouble(double min, double max) {
        if (min >= max) {
            throw new InvalidParameterException("min must be less than max");
        }
        this.min = min;
        this.fractionalDigits = Math.max(calculateMinExponent(min), calculateMinExponent(max));
        this.fractionalScale = Math.pow(10.0d, fractionalDigits);
        this.scaleFactor = calculateScaleFactor(min, max);
    }

    private double calculateScaleFactor(double min, double max) {
        double targetRange = max - min;
        double longRange = (double) Long.MAX_VALUE - (double) Long.MIN_VALUE;
        return (targetRange / longRange);
    }

    public ToScaledDouble(String min, String max) {
        this(Double.valueOf(min), Double.valueOf(max));
    }

    private int calculateMinExponent(double min) {
        int exp = 0;
        while (fractional(min) > 0.0d) {
            min = fractional(min * 10.0d);
            exp++;
        }
        return exp;
    }

    private double whole(double number) {
        return (number - fractional(number));
    }

    private double fractional(double number) {
        return (number - (long) number);
    }

    @Override
    public Double apply(long value) {
        double scaled = scaleFactor * (((double) value) - (double) Long.MIN_VALUE);
        double shifted = scaled + min;
        if (fractionalDigits != 0) {
            double whole = Math.floor(shifted);
            double fractional = shifted - whole;
            double truncated = (Math.floor(fractional*fractionalScale) / fractionalScale);
            shifted = whole + truncated;
        }
        return shifted;
    }
}
