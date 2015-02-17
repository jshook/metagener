package com.metawiring.configdefs;

import com.metawiring.types.FuncCallDef;
import com.metawiring.types.FuncDef;

import java.util.ArrayList;
import java.util.List;

public class MutableFuncDef implements FuncDef {
    private String funcName;
    private String funcSpec;
    private List<FuncCallDef> funcCallDefs = new ArrayList<FuncCallDef>();

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

    public void setFuncCallDefs(List<FuncCallDef> funcCallDefs) {
        this.funcCallDefs = funcCallDefs;
    }

    @Override
    public List<FuncCallDef> getFuncCallDefs() {
        return funcCallDefs;
    }

    public void setFuncSpec(String funcSpec) {
        this.funcSpec = funcSpec;
    }

    public FuncDef immutable() {
        return (FuncDef) this;
    }

    public FuncDef addFuncCallDefs(FuncDef samplerFuncDef) {
        for (FuncCallDef funcCallDef : samplerFuncDef.getFuncCallDefs()) {
            this.funcCallDefs.add(funcCallDef);
        }
        return this;
    }
}
