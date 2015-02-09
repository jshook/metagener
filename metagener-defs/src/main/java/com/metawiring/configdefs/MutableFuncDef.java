package com.metawiring.configdefs;

import com.metawiring.types.FuncDef;

public class MutableFuncDef implements FuncDef {
    private String funcName;
    private String funcSpec;

    @Override
    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    @Override
    public String getFuncSpec() {
        return funcSpec;
    }

    public void setFuncSpec(String funcSpec) {
        this.funcSpec = funcSpec;
    }

    public FuncDef immutable() {
        return (FuncDef) this;
    }
}
