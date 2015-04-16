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

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;

public class FFTSimTest {

    @Test
    public void testFFTSim1327311() {
        FFTSim fft1 = new FFTSim(1,0.03,2,0.07,3,0.011);
        assertThat(fft1.apply(0), is(0d));
        assertThat(fft1.apply(1),is(closeTo(0.0398d, 0.001d)));
        assertThat(fft1.apply(2),is(closeTo(0.0404d, 0.001d)));
        assertThat(fft1.apply(4),is(closeTo(0.0821d, 0.001d)));
        assertThat(fft1.apply(8),is(closeTo(0.1743d, 0.001d)));
        assertThat(fft1.apply(16),is(closeTo(0.4080d, 0.001d)));
        assertThat(fft1.apply(32),is(closeTo(0.8880d, 0.001d)));
        assertThat(fft1.apply(64),is(closeTo(0.9147d, 0.001d)));
        assertThat(fft1.apply(128),is(closeTo(-0.5965d, 0.001d)));
        assertThat(fft1.apply(256),is(closeTo(1.0544d, 0.001d)));
        assertThat(fft1.apply(512),is(closeTo(0.3575d, 0.001d)));
        assertThat(fft1.apply(1024),is(closeTo(-0.6732d, 0.001d)));
        assertThat(fft1.apply(2048),is(closeTo(-1.0411d, 0.001d)));
    }
}
