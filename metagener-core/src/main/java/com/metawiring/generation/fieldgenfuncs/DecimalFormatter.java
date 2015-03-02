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

import java.text.DecimalFormat;

public class DecimalFormatter implements TypedFieldFunction<String> {

    private final double min;
    private final double max;
    private final double scale;
    private final DecimalFormat decimalFormat;

    public DecimalFormatter(double min, double max, DecimalFormat decimalFormat) {
        this.min = min;
        this.max = max;
        this.scale = max-min / (double) Long.MAX_VALUE;
        this.decimalFormat = decimalFormat;
    }

    public DecimalFormatter(String min, String max, String decimalFormat) {
        this(Double.valueOf(min),Double.valueOf(max),new DecimalFormat(decimalFormat));
    }


    @Override
    public String apply(long value) {
        double decimalValue = (((double) value) * scale) + min;
        return decimalFormat.format(decimalValue);
    }
}
