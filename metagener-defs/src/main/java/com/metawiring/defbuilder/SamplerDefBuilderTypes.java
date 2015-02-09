package com.metawiring.defbuilder;

import com.metawiring.types.MetagenDef;

public class SamplerDefBuilderTypes {
    public static interface wantsSamplerDefs extends canBuild {
        /**
         * Create a sampler of the same name as the entity it is sampling.
         *
         * @param entityName the entity to sample, and hence the new sampler name.
         * @return a partial sampler definition
         */
        public wantsSamplerFunction sampler(String entityName);

        /**
         * Create a sampler under a different name than the sampler.
         *
         * @param entityName  The entity to sample
         * @param samplerName The name of the sampler to create
         * @return a partial sampler definition
         */
        public wantsSamplerFunction sampler(String entityName, String samplerName);
    }

    public static interface wantsSamplerFunction extends wantsSamplerDefs {
        public wantsSamplerDefs entityFunction(String entitySamplerFunction);

    }

    public interface canBuild {
        MetagenDef build();
    }


}
