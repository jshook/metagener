package com.metawiring.descriptors.bundled;

public class ContextBuilder {

    private int buildEntityPopulation;
    private String samplerName;
    private String buildEntity;


    public EntityPopulationBuilder entity(String entityName) {
        buildEntity = entityName;
        return (EntityPopulationBuilder) this;
    }

    public SamplerDistributionBuilder sampleEntity(String samplerName) {
        this.samplerName = samplerName;
        return (SamplerDistributionBuilder) this;
    }


    public static interface SamplerAsBuilder {
        public ContextBuilder as(String sampleAlias);
    }

    public static interface SamplerDistributionBuilder {
        public SamplerAsBuilder distribution(String distributionSpec);
    }

    public static interface EntityBuilder {
        public EntityPopulationBuilder entity(String name);
    }
    public static interface EntityPopulationBuilder {
        public FieldBuilder population(int population);
    }
    public static interface FieldBuilder {
        public FieldTypeBuilder field(String fieldName);
    }
    public static interface FieldTypeBuilder {
        public FieldGeneratorBuilder type(String fieldType);
    }
    public static interface FieldGeneratorBuilder {
        public FieldOrContextBuilder generator(String fieldGenerator); // haha
    }
    public static interface FieldOrContextBuilder {
        public FieldTypeBuilder field(String fieldName);
        public EntityPopulationBuilder entity(String entityName);
        public SamplerDistributionBuilder sampleEntity(String samplerName);
    }
}
