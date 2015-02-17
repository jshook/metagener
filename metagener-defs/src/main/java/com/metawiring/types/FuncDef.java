package com.metawiring.types;

import java.util.List;

public interface FuncDef {
    String getFuncName();
    String getFuncSpec();
    List<FuncCallDef> getFuncCallDefs();
}
