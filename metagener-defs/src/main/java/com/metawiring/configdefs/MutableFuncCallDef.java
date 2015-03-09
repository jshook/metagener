package com.metawiring.configdefs;

import com.metawiring.types.FuncCallDef;

import java.util.ArrayList;
import java.util.List;

public class MutableFuncCallDef implements FuncCallDef {

    private String assignTo;
    private String funcName;
    private List<String> funcArgs = new ArrayList<String>();

    @Override
    public String getAssignTo() {
        return assignTo;
    }

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

    public MutableFuncCallDef setAssignTo(String assignToName) {
        this.assignTo = assignToName;
        return this;
    }

    public FuncCallDef immutable() {
        return (FuncCallDef) this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (assignTo!=null) {
            sb.append(assignTo).append("=");
        }
        sb.append(funcName == null ? "<unset>" : funcName);
        String separator = "";
        sb.append("(");
        if (funcArgs != null && funcArgs.size() > 0) {
            for (String funcArg : funcArgs) {
                sb.append(separator);
                sb.append(funcArg);
                separator = ",";
            }
        }
        sb.append(")");

        return sb.toString();
    }

}
