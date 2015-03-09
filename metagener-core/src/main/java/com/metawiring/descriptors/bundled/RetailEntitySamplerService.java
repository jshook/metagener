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
package com.metawiring.descriptors.bundled;

import com.metawiring.types.EntitySampler;
import com.metawiring.types.EntitySamplerService;
import com.metawiring.types.SamplerDef;
import com.metawiring.wiring.GenContext;
import com.metawiring.wiring.Metagener;

import java.util.List;
import java.util.Map;

public class RetailEntitySamplerService implements EntitySamplerService {

    private GenContext retailContext = Metagener.fromFile("prebundled/retail.metagener");

    @Override
    public Map<String, SamplerDef> getSampleStreamMap() {
        return retailContext.getSampleStreamMap();
    }

    @Override
    public EntitySampler getEntitySampleStream(String name) {
        return retailContext.getEntitySampleStream(name);
    }

    @Override
    public List<SamplerDef> getDefinedEntitySamplers() {
        return retailContext.getDefinedEntitySamplers();
    }
}
