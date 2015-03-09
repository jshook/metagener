package com.metawiring.types;

import java.util.List;

public interface FuncCallDef {
    // If this function is meant to assign a value, this will be the named parameter to assign it to
    public String getAssignTo();
    public String getFuncName();
    public List<String> getFuncArgs();
}
