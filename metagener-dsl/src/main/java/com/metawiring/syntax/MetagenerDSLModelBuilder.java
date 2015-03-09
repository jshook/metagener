package com.metawiring.syntax;

import com.metawiring.configdefs.*;
import com.metawiring.generated.MetagenBaseListener;
import com.metawiring.generated.MetagenParser;
import com.metawiring.types.FuncCallDef;
import com.metawiring.types.MetagenDef;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MetagenerDSLModelBuilder extends MetagenBaseListener {
    private static Logger logger = LoggerFactory.getLogger(MetagenerDSLModelBuilder.class);
    private MutableMetagenDef mutableMetagenDef;
    private MutableEntityDef mutableEntityDef;
    private MutableFieldDef mutableFieldDef;
    private MutableFuncDef mutableFuncDef;
    private MutableSamplerDef mutableSamplerDef;
    private MutableFuncCallDef mutableFuncCallDef;

    private List<ErrorNode> errorNodes = new ArrayList<ErrorNode>();

    public MetagenDef getGenContextDef() {
        return mutableMetagenDef.immutable();
    }

    public FuncCallDef getFuncCallDef() {
        return mutableFuncCallDef.immutable();
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
        if (ctx.popSize() != null) {
            mutableEntityDef.setPopulationSize(Long.valueOf(ctx.popSize().getText()));
        }
        mutableMetagenDef.addEntityDef(mutableEntityDef.immutable());
        mutableEntityDef = null;
    }

    @Override
    public void enterFielddef(MetagenParser.FielddefContext ctx) {
        mutableFieldDef = new MutableFieldDef();
    }

    @Override
    public void exitFielddef(MetagenParser.FielddefContext ctx) {
        mutableFieldDef.setFieldName(ctx.fieldName().getText());
        mutableFieldDef.setFieldType(ctx.fieldType().getText());
        if (ctx.chainedFuncSpec() != null) {
            mutableFieldDef.setFieldFunc(ctx.chainedFuncSpec().getText());
            mutableFieldDef.setFieldFuncDef(mutableFuncDef.immutable());
            mutableFuncDef = null;
        }
        mutableEntityDef.addFieldDescriptor(mutableFieldDef.immutable());
        mutableFieldDef = null;
    }

    @Override
    public void exitChainedFuncSpec(MetagenParser.ChainedFuncSpecContext ctx) {
        mutableFuncDef.setFuncSpec(ctx.getText());
    }

    @Override
    public void enterChainedFuncSpec(MetagenParser.ChainedFuncSpecContext ctx) {
        mutableFuncDef = new MutableFuncDef();
    }

    @Override
    public void exitChainedFuncPart(MetagenParser.ChainedFuncPartContext ctx) {
        mutableFuncCallDef = new MutableFuncCallDef();
        if (ctx.functionCall() != null) {
            mutableFuncCallDef.setFuncName(ctx.functionCall().functionName().id().getText());
            for (MetagenParser.FuncArgContext funcArgContext : ctx.functionCall().funcArgs().funcArg()) {
                StringBuilder sb = new StringBuilder();
                if (funcArgContext.assignment()!=null) {
                    sb.append(funcArgContext.assignment().assignTo().id().getText())
                            .append("=");
                }
                MetagenParser.ValueContext cvalue = funcArgContext.value();
                String valueText = funcArgContext.getText();

                if (cvalue.stringValue()!=null) {
                    String text = funcArgContext.value().stringValue().SQUOTESTRING().getText();
                    sb.append(text.substring(1,text.length()-1));
                } else {
                    sb.append(cvalue.getText());
                }

                mutableFuncCallDef.getFuncArgs().add(sb.toString());
            }
        }
        mutableFuncDef.getFuncCallDefs().add(mutableFuncCallDef);
    }


    @Override
    public void enterFuncdef(MetagenParser.FuncdefContext ctx) {
        mutableFuncDef = new MutableFuncDef();
    }


    @Override
    public void exitFuncdef(MetagenParser.FuncdefContext ctx) {
        mutableFuncDef.setFuncName(ctx.funcName().getText());
        mutableFuncDef.setFuncSpec(ctx.chainedFuncSpec().getText());
        mutableEntityDef.addFuncDef(mutableFuncDef);
        mutableFuncDef = null;
    }

    @Override
    public void enterSamplerdef(MetagenParser.SamplerdefContext ctx) {
        mutableSamplerDef = new MutableSamplerDef();
    }

    @Override
    public void exitSamplerdef(MetagenParser.SamplerdefContext ctx) {
        mutableSamplerDef.setSamplerName(ctx.samplerName().getText());
        if (ctx.samplerEntity() != null) {
            mutableSamplerDef.setEntityName(ctx.samplerEntity().getText());
        } else {
            mutableSamplerDef.setEntityName(ctx.samplerName().getText());
        }
        if (mutableFuncDef != null) {
            mutableSamplerDef.setSamplerFuncDef(mutableFuncDef);
            mutableFuncDef = null;
        }
        if (ctx.samplerFunc() != null) {
            mutableSamplerDef.setSamplerFunc(ctx.samplerFunc().getText());
        }
        mutableMetagenDef.addSamplerDef(mutableSamplerDef.immutable());
        mutableSamplerDef = null;
    }


    @Override
    public void visitErrorNode(ErrorNode node) {
        super.visitErrorNode(node);
        errorNodes.add(node);
    }

    public boolean hasErrors() {
        return (errorNodes.size() > 0);
    }

    public List<ErrorNode> getErrorNodes() {
        return errorNodes;
    }



}
