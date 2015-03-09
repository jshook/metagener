package com.metawiring.generation.longfuncs;

import com.metawiring.types.functiontypes.EntityDefAware;
import com.metawiring.types.functiontypes.LongUnaryFieldFunction;
import com.metawiring.types.EntityDef;
import com.metawiring.types.SamplerDef;
import com.metawiring.types.functiontypes.SamplerDefAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongLongUnaryDiagnostic implements LongUnaryFieldFunction, EntityDefAware, SamplerDefAware {
    private static final Logger logger = LoggerFactory.getLogger(LongLongUnaryDiagnostic.class);

    private EntityDef entityDef;
    private SamplerDef samplerDef;
    private long cycles;

    private long triggerModulo;

    public LongLongUnaryDiagnostic(long triggerModulo) {
        this.triggerModulo = triggerModulo;
    }

    public LongLongUnaryDiagnostic(String triggerModulo) {
        this.triggerModulo = Long.valueOf(triggerModulo);
    }

    @Override
    public void applyEntityDef(EntityDef entityDef) {
        this.entityDef = entityDef;
    }

    @Override
    public void applySamplerDef(SamplerDef samplerDef) {
        this.samplerDef = samplerDef;
    }

    @Override
    public long applyAsLong(long operand) {
        cycles++;
        if ((cycles % triggerModulo) == 0) {
            logger.debug("DIAG: entityDef:" + entityDef.getName() + ", samplerDef" + samplerDef.getSamplerName()
                    + "cycle:" + cycles + "/" + triggerModulo + ", input=" + operand);
        }
        return operand;
    }
}
