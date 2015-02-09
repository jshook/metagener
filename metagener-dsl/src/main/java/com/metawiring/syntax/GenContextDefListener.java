package com.metawiring.syntax;

import com.metawiring.configdefs.*;
import com.metawiring.generated.MetagenBaseListener;
import com.metawiring.generated.MetagenParser;
import com.metawiring.types.MetagenDef;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenContextDefListener extends MetagenBaseListener {
    private static Logger logger = LoggerFactory.getLogger(GenContextDefListener.class);
    private MutableMetagenDef mutableMetagenDef;
    private MutableEntityDef mutableEntityDef;
    private MutableFieldDef mutableFieldDef;
    private MutableFuncDef mutableFuncDef;
    private MutableSamplerDef mutableSamplerDef;

    public MetagenDef getGenContextDef() {
        return mutableMetagenDef.immutable();
    }

    @Override
    public void enterGencontextdef(MetagenParser.GencontextdefContext ctx) {
        logger.info("building GenContextDef");
        mutableMetagenDef = new MutableMetagenDef();
    }

    @Override
    public void exitGencontextdef(MetagenParser.GencontextdefContext ctx) {
        logger.info("built GenContextDef");
    }

    @Override
    public void enterEntitydef(MetagenParser.EntitydefContext ctx) {
        mutableEntityDef = new MutableEntityDef();
    }

    @Override
    public void exitEntitydef(MetagenParser.EntitydefContext ctx) {
        mutableEntityDef.setName(ctx.entityName().getText());
        if (ctx.popSize()!=null) {
            mutableEntityDef.setPopulationSize(Long.valueOf(ctx.popSize().getText()));
        }
        mutableMetagenDef.addEntityDef(mutableEntityDef.immutable());
        mutableEntityDef=null;
    }

    @Override
    public void enterFielddef(MetagenParser.FielddefContext ctx) {
        mutableFieldDef = new MutableFieldDef();
    }

    @Override
    public void exitFielddef(MetagenParser.FielddefContext ctx) {
        mutableFieldDef.setFieldName(ctx.fieldName().getText());
        mutableFieldDef.setFieldType(ctx.fieldType().getText());
        if (ctx.composedFuncSpec()!=null) {
            mutableFieldDef.setFunction(ctx.composedFuncSpec().getText());
        }
        mutableEntityDef.addFieldDescriptor(mutableFieldDef.immutable());
        mutableFieldDef=null;
    }

    @Override
    public void enterFuncdef(MetagenParser.FuncdefContext ctx) {
        mutableFuncDef = new MutableFuncDef();
    }


    @Override
    public void exitFuncdef(MetagenParser.FuncdefContext ctx) {
        mutableFuncDef.setFuncName(ctx.funcName().getText());
        mutableFuncDef.setFuncSpec(ctx.composedFuncSpec().getText());
        mutableEntityDef.addFuncDef(mutableFuncDef);
        mutableFuncDef=null;
    }

    @Override
    public void enterSamplerdef(MetagenParser.SamplerdefContext ctx) {
        mutableSamplerDef=new MutableSamplerDef();
    }

    @Override
    public void exitSamplerdef(MetagenParser.SamplerdefContext ctx) {
        mutableSamplerDef.setSamplerName(ctx.samplerName().getText());
        if (ctx.samplerEntity()!=null) {
            mutableSamplerDef.setEntityName(ctx.samplerEntity().getText());
        } else {
            mutableSamplerDef.setEntityName(ctx.samplerName().getText());
        }
        if (ctx.samplerFunc()!=null) {
            mutableSamplerDef.setSamplerFunc(ctx.samplerFunc().getText());
        }
        mutableMetagenDef.addSamplerDef(mutableSamplerDef.immutable());
        mutableSamplerDef = null;
    }


    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
    }
}
