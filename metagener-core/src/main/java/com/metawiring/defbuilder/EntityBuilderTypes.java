package com.metawiring.defbuilder;

public interface EntityBuilderTypes {
    interface wantsEntityDefs {
        public wantsEntityPop entity(String entityName);
    }

    interface wantsEntityFields {
        wantsFieldType field(String fieldName);
    }

    interface wantsEntityPop {
        wantsEntityFields population(int entityPopulation);
    }

    interface wantsFieldFunction {
        public wantsEntityFields function(String fieldFunction);
    }

    interface wantsFieldType {
        wantsFieldFunction type(String fieldType);
    }
}
