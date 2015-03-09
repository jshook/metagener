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
        if (samplerFuncDef==null) {
            return this;
        }
        for (FuncCallDef funcCallDef : samplerFuncDef.getFuncCallDefs()) {
            this.funcCallDefs.add(funcCallDef);
        }
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(funcName==null ? "NULL" : funcName + " ");
        for (FuncCallDef funcCallDef : funcCallDefs) {
            sb.append(funcCallDef.toString()).append("; ");
        }
        return sb.toString();
    }
}
