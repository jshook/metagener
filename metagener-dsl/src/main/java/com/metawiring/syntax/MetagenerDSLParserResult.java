package com.metawiring.syntax;

import com.metawiring.types.MetagenDef;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Parser result for result object of type T
 * @param <T> Type of result produce by parser listener
 */
public class MetagenerDSLParserResult<T> {
    protected Optional<T> optionalMetagenDef;
    protected Optional<MetagenerDSLErrorHandler> optionalDiagnostic;

    /*
    The metagen definition may be partially complete. It is saved here for deeper diagnostics, when needed.
     */
    public MetagenerDSLParserResult(T metagenDef, MetagenerDSLErrorHandler errorHandler) {
        this.optionalMetagenDef = Optional.ofNullable(metagenDef);
        this.optionalDiagnostic = Optional.ofNullable(errorHandler);

    }

    public String getErrorSummary() {
        return optionalDiagnostic.orElse(new MetagenerDSLErrorHandler()).getErrorSummary();
    }

    public T getMetagenDef() {
        if (optionalDiagnostic.orElse(new MetagenerDSLErrorHandler()).hasErrors()) {
            throw new RuntimeException("Attempted to access incomplete MetagenDef when there were parser errors. This is only allowed via protected access.");
        }
        return optionalMetagenDef.orElseThrow(new Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new RuntimeException("MetagenDef was not set. This indicates a serious error.");
            }
        });
    }


    public boolean hasErrors() {
        return optionalDiagnostic.orElse(new MetagenerDSLErrorHandler()).hasErrors();
    }
}
