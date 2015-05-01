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

import com.metawiring.annotations.Output;
import com.metawiring.types.functiontypes.TypedFieldFunction;

@Output(Double.class)
public class FFTSim implements TypedFieldFunction<Double> {

    private double[] fftSignature;

    public FFTSim() {
        this(1.0, 3,0, 7.0, 2.0, 11.0, 0.2);
    }

    public FFTSim(double... fftparams) {
        fftSignature = fftparams;
    }

    public FFTSim(String fftparamsJoined) {
        String[] fftparams = fftparamsJoined.split(",");
        fftSignature = new double[fftparams.length];
        for (int idx = 0; idx < fftparams.length; idx++) {
            fftSignature[idx]=Double.valueOf(fftparams[idx]);
        }
    }

    @Override
    public Double apply(long value) {
        double y=0.0d;
        for (int idx = 0; idx < fftSignature.length; idx+=2) {
            y+= fftSignature[idx] * Math.sin(fftSignature[idx+1]*value);
        }
        return y;
    }
}
