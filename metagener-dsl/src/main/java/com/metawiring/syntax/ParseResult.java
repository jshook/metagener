package com.metawiring.syntax;

import com.metawiring.types.MetagenDef;

import java.util.Optional;
import java.util.function.Supplier;

public class ParseResult {
    private Optional<MetagenDef> optionalMetagenDef;
    private Optional<MetagenParserDiagnostic> optionalDiagnostic;

    /*
    The metagen definition may be partially complete. It is saved here for deeper diagnostics, when needed.
     */
    public ParseResult(MetagenDef metagenDef, MetagenParserDiagnostic errorHandler) {
        this.optionalMetagenDef = Optional.ofNullable(metagenDef);
        this.optionalDiagnostic = Optional.ofNullable(errorHandler);

    }

    public String getErrorSummary() {
        return optionalDiagnostic.orElse(new MetagenParserDiagnostic()).getErrorSummary();
    }

    public MetagenDef getMetagenDef() {
        return optionalMetagenDef.orElseThrow(new Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new RuntimeException("Attempted to access parsed MetagenDef when there were parser errors.");
            }
        });
    }


    public boolean hasErrors() {
        return optionalDiagnostic.orElse(new MetagenParserDiagnostic()).hasErrors();
    }
}
