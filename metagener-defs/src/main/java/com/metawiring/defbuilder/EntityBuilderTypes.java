package com.metawiring.defbuilder;

import com.metawiring.types.MetagenDef;

public interface EntityBuilderTypes {
    interface wantsEntityDefs {
        public wantsEntityPop entity(String entityName);
    }

    interface wantsEntityPop {
        wantsEntityFields population(int entityPopulation);
    }

    interface wantsEntityFields extends canBuild {
        wantsFieldType field(String fieldName);
    }

    interface wantsFieldType {
        wantsFieldFunction type(String fieldType);
    }

    interface wantsFieldFunction {
        public wantsEntityFields function(String fieldFunction);
    }

    interface canBuild {
        MetagenDef build();
    }
}
