package com.metawiring.generation.valuemapfunctions;

import com.metawiring.generation.FieldFunction;
import com.metawiring.types.EntityDef;
import com.metawiring.types.EntityDefAware;
import com.metawiring.types.SamplerDef;
import com.metawiring.types.SamplerDefAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongLongDiagnostic implements FieldFunction<Long,Long>, EntityDefAware, SamplerDefAware {
    private static final Logger logger = LoggerFactory.getLogger(LongLongDiagnostic.class);

    private EntityDef entityDef;
    private SamplerDef samplerDef;
    private long cycles;

    private long triggerModulo;
    public LongLongDiagnostic(long triggerModulo) {
        this.triggerModulo = triggerModulo;
    }
    public LongLongDiagnostic(String triggerModulo) {
        this.triggerModulo = Long.valueOf(triggerModulo);
    }

    @Override
    public Long apply(Long aLong) {
        cycles++;
        if ((cycles % triggerModulo) == 0 ) {
            logger.debug("DIAG: entityDef:" + entityDef.getName() + ", samplerDef" + samplerDef.getName()
                    + "cycle:" + cycles + "/" + triggerModulo + ", input=" + aLong);
        }
        return aLong;
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
