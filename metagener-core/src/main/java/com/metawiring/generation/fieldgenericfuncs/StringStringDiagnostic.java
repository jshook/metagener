package com.metawiring.generation.fieldgenericfuncs;

import com.metawiring.types.functiontypes.EntityDefAware;
import com.metawiring.types.functiontypes.GenericFieldFunction;
import com.metawiring.types.EntityDef;
import com.metawiring.types.SamplerDef;
import com.metawiring.types.functiontypes.SamplerDefAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringStringDiagnostic implements GenericFieldFunction<String,String>, EntityDefAware, SamplerDefAware {
    private static final Logger logger = LoggerFactory.getLogger(StringStringDiagnostic.class);

    private EntityDef entityDef;
    private SamplerDef samplerDef;
    private long cycles;

    private long triggerModulo;

    public StringStringDiagnostic(long triggerModulo) {
        this.triggerModulo = triggerModulo;
    }

    public StringStringDiagnostic(String triggerModulo) {
        this(Long.valueOf(triggerModulo));
    }

    @Override
    public String apply(String string) {
        cycles++;
        if ((cycles % triggerModulo) == 0 ) {
            logger.debug("DIAG: entityDef:" + entityDef.getName() + ", samplerDef" + samplerDef.getSamplerName()
                    + "cycle:" + cycles + "/" + triggerModulo + ", input=" + string);
        }
        return string;
    }

    @Override
    public void applyEntityDef(EntityDef entityDef) {
        this.entityDef = entityDef;
    }

    @Override
    public void applySamplerDef(SamplerDef samplerDef) {
        this.samplerDef = samplerDef;
    }

}
