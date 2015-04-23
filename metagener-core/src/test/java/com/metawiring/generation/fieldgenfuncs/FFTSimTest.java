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

import org.assertj.core.data.Offset;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class FFTSimTest {

    @Test
    public void testFFTSim1327311() {
        FFTSim fft1 = new FFTSim(1,0.03,2,0.07,3,0.011);
        assertThat(fft1.apply(0)).isEqualTo(0d);
        assertThat(fft1.apply(1)).isCloseTo(0.0398d, within(0.001d));
        assertThat(fft1.apply(2)).isCloseTo(0.0404d, within(0.001d));
        assertThat(fft1.apply(4)).isCloseTo(0.0821d, within(0.001d));
        assertThat(fft1.apply(8)).isCloseTo(0.1743d, within(0.001d));
        assertThat(fft1.apply(16)).isCloseTo(0.4080d, within(0.001d));
        assertThat(fft1.apply(32)).isCloseTo(0.8880d, within(0.001d));
        assertThat(fft1.apply(64)).isCloseTo(0.9147d, within(0.001d));
        assertThat(fft1.apply(128)).isCloseTo(-0.5965d, within(0.001d));
        assertThat(fft1.apply(256)).isCloseTo(1.0544d, within(0.001d));
        assertThat(fft1.apply(512)).isCloseTo(0.3575d, within(0.001d));
        assertThat(fft1.apply(1024)).isCloseTo(-0.6732d, within(0.001d));
        assertThat(fft1.apply(2048)).isCloseTo(-1.0411d, within(0.001d));
    }
}
