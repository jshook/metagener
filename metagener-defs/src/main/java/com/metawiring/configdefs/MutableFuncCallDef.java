package com.metawiring.configdefs;

import com.metawiring.types.FuncCallDef;

import java.util.ArrayList;
import java.util.List;

public class MutableFuncCallDef implements FuncCallDef {

    private String funcName;
    private List<String> funcArgs = new ArrayList<String>();

    @Override
    public String getFuncName() {
        return funcName;
    }

    @Override
    public List<String> getFuncArgs() {
        return funcArgs;
    }

    public MutableFuncCallDef setFuncName(String funcName) {
        this.funcName = funcName;
        return this;
    }

    public MutableFuncCallDef setFuncArgs(List<String> funcArgs) {
        this.funcArgs = funcArgs;
        return this;
    }

    public FuncCallDef immutable() {
        return (FuncCallDef) this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(funcName == null ? "NULL" : funcName);
        String separator = "";
        if (funcArgs != null && funcArgs.size() > 0) {
            sb.append("(");
            for (String funcArg : funcArgs) {
                sb.append(separator);
                sb.append(funcArg);
                separator = ",";
            }
            sb.append(")");
        }

        return sb.toString();
    }

}
