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
package com.metawiring.generation.longfuncs;

import com.metawiring.generation.core.HashedSamplingAdapter;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;

import java.util.Random;

public class RangedRandom implements LongUnaryFieldFunction{

    private final int minInclusive;
    private final int maxInclusive;
    private HashedSamplingAdapter samplingAdapter;

    public RangedRandom(String minInclusive, String maxInclusive) {
        this(Integer.valueOf(minInclusive),Integer.valueOf(maxInclusive));
    }

    public RangedRandom(int minInclusive, int maxInclusive) {
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
        this.samplingAdapter = new HashedSamplingAdapter(minInclusive,maxInclusive,"uniform");
    }

    @Override
    public long applyAsLong(long operand) {
        return samplingAdapter.sample(operand);
    }

}
