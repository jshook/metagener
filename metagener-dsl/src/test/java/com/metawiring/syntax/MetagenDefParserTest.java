package com.metawiring.syntax;

import com.metawiring.generated.MetagenLexer;
import com.metawiring.generated.MetagenParser;
import com.metawiring.types.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.CharBuffer;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class MetagenDefParserTest {

    @Test
    public void testParseTestSyntax() {
        char[] chars = readFile("test-syntax.metagener");
        ANTLRInputStream ais = new ANTLRInputStream(chars, chars.length);
        MetagenLexer lexer = new MetagenLexer(ais);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MetagenParser parser = new MetagenParser(tokens);
        MetagenerDSLModelBuilder modelBuilder = new MetagenerDSLModelBuilder();
        parser.addParseListener(modelBuilder);
        MetagenParser.GencontextdefContext parseTree = parser.gencontextdef();
        System.out.println(parseTree.toStringTree(parser));
        if (modelBuilder.hasErrors()) {
            System.out.println(modelBuilder.getErrorNodes());
        }
        assertThat(modelBuilder.hasErrors(),is(false));
        MetagenDef genContextDef = modelBuilder.getGenContextDef();

    }

    @Test
    public void testParseEntity() {
        MetagenDef md = parseString("entity parsley");
        assertThat(md.getEntityDefs().size(), is(1));
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat(entityDef.getName(), is("parsley"));
        assertThat(entityDef.getPopulationSize(),is(Long.MAX_VALUE));
    }

    @Test
    public void testParseEntityWithPopulation() {
        MetagenDef md = parseString("entity carrots pop=42343");
        assertThat(md.getEntityDefs().size(), is(1));
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat(entityDef.getName(), is("carrots"));
        assertThat(entityDef.getPopulationSize(),is(42343l));
    }

    @Test
    public void testParseEntityField() {
        MetagenDef md = parseString("entity carrots pop=42343\nfield color text\n");
        assertThat(md.getEntityDefs().size(), is(1));
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat(entityDef.getName(), is("carrots"));
        assertThat(entityDef.getPopulationSize(),is(42343l));
        assertThat(entityDef.getFieldDefs().size(), is(1));
        FieldDef color = entityDef.getFieldDefs().get(0);
        assertThat(color.getFieldName(),is("color"));
        assertThat(color.getFieldType(),is(FieldType.type_text));
        assertThat(color.getFieldFunc(),is(nullValue()));
    }


    @Test
    public void testParseEntityFieldWithFunction() {
        MetagenDef md = parseString("entity carrots pop=42343\nfield color:text <- entity()\n");
        EntityDef entityDef = md.getEntityDefs().get(0);
        FieldDef color = entityDef.getFieldDefs().get(0);
        assertThat(color.getFieldName(),is("color"));
        assertThat(color.getFieldType(),is(FieldType.type_text));
        assertThat(color.getFieldFunc(),is("entity()"));
        assertThat(color.getFieldFuncDef().getFuncName(),is(nullValue()));

    }

    // This probably neeeds to be rewritten after function calls are added
    @Test(enabled=false)
    public void testParseEntityFieldWithFunctionAssignment() {
        MetagenDef md = parseString("entity carrots pop=42343\nfield color:text <- fassign=entity()\n");
        EntityDef entityDef = md.getEntityDefs().get(0);
        FieldDef color = entityDef.getFieldDefs().get(0);
        assertThat(color.getFieldName(),is("color"));
        assertThat(color.getFieldType(),is(FieldType.type_text));
        assertThat(color.getFieldFunc(),is("fassign=entity()"));
    }

    @Test
    public void testParseSamplerDef() {
        MetagenDef md = parseString("sampler somefood");
        assertThat(md.getSamplerDefs().size(),is(1));
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName(),is("somefood"));
        assertThat(samplerDef.getEntityName(),is("somefood"));
        assertThat(samplerDef.getSamplerFuncDef(),is(nullValue()));
    }
    @Test
    public void testParseSamplerDefWithAlias() {
        MetagenDef md = parseString("sampler somefood:foosball");
        assertThat(md.getSamplerDefs().size(),is(1));
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName(),is("somefood"));
        assertThat(samplerDef.getEntityName(),is("foosball"));
        assertThat(samplerDef.getSamplerFuncDef(),is(nullValue()));
    }

    @Test
    public void testParseSamplerDefWithAliasAndFunction() {
        MetagenDef md = parseString("sampler somefood:foosball <- lizardgills()");
        assertThat(md.getSamplerDefs().size(),is(1));
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName(),is("somefood"));
        assertThat(samplerDef.getEntityName(),is("foosball"));
        assertThat(samplerDef.getSamplerFuncDef().getFuncName(),is(nullValue()));
        assertThat(samplerDef.getSamplerFuncDef().getFuncCallDefs().get(0).getFuncName(),is("lizardgills"));
    }


    @Test
    public void testParseEntityDefFieldWithFuncDef() {
        MetagenDef md = parseString("entity foo\nfield bar:text <- funca(one,two)");
        assertThat(md.getEntityDefs().size(),is(1));
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat( entityDef.getFieldDefs().size(),is(1));
        FieldDef fieldBar = entityDef.getFieldDef("bar");
        assertThat(fieldBar,is(notNullValue()));
        assertThat(fieldBar.getFieldFuncDef(),is(notNullValue()));
        FuncDef fieldFuncDef = fieldBar.getFieldFuncDef();
        assertThat(fieldFuncDef.getFuncCallDefs().size(),is(1));
        List<FuncCallDef> funcCallDefs = fieldFuncDef.getFuncCallDefs();

        FuncCallDef fcd0 = funcCallDefs.get(0);
        assertThat(fcd0.getFuncName(),is("funca"));
        assertThat(fcd0.getFuncArgs().size(),is(2));
        assertThat(fcd0.getFuncArgs().get(0),is("one"));
        assertThat(fcd0.getFuncArgs().get(1),is("two"));
    }

    @Test
    public void testParseEntityDefFieldWithFuncDefs() {
        MetagenDef md = parseString("entity foo\nfield bar:text <- funca();funcb(one,two);funcc(p1=v1,p2=v2)");
        assertThat(md.getEntityDefs().size(),is(1));
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat( entityDef.getFieldDefs().size(),is(1));
        FieldDef fieldBar = entityDef.getFieldDef("bar");
        assertThat(fieldBar,is(notNullValue()));
        assertThat(fieldBar.getFieldFuncDef(),is(notNullValue()));
        FuncDef fieldFuncDef = fieldBar.getFieldFuncDef();
        assertThat(fieldFuncDef.getFuncCallDefs().size(),is(3));
        List<FuncCallDef> funcCallDefs = fieldFuncDef.getFuncCallDefs();

        FuncCallDef fcd0 = funcCallDefs.get(0);
        assertThat(fcd0.getFuncName(),is("funca"));
        assertThat(fcd0.getFuncArgs().size(),is(0));

        FuncCallDef fcd1 = funcCallDefs.get(1);
        assertThat(fcd1.getFuncName(),is("funcb"));
        assertThat(fcd1.getFuncArgs().size(),is(2));
        assertThat(fcd1.getFuncArgs().get(0),is("one"));
        assertThat(fcd1.getFuncArgs().get(1),is("two"));

        FuncCallDef fcd2 = funcCallDefs.get(2);
        assertThat(fcd2.getFuncName(),is("funcc"));
        assertThat(fcd2.getFuncArgs().size(),is(2));
        assertThat(fcd2.getFuncArgs().get(0),is("p1=v1"));
        assertThat(fcd2.getFuncArgs().get(1),is("p2=v2"));

    }


    private MetagenDef parseString(String syntax) {
        ANTLRInputStream ais = new ANTLRInputStream(syntax);
        MetagenLexer lexer = new MetagenLexer(ais);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MetagenParser parser = new MetagenParser(tokens);
        MetagenerDSLModelBuilder modelBuilder = new MetagenerDSLModelBuilder();
        parser.addParseListener(modelBuilder);
        MetagenParser.GencontextdefContext parseTree = parser.gencontextdef();
        System.out.println(parseTree.toStringTree(parser));

        MetagenDef genContextDef = modelBuilder.getGenContextDef();
        return genContextDef;
    }
    private static char[] readFile(String filename) {
        BufferedReader sr = new BufferedReader(
                new InputStreamReader(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(filename)
                )
        );
        CharBuffer cb = CharBuffer.allocate(1000000);
        try {
            while (sr.read(cb) > 0) {
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cb.flip();
        char[] cbimage = new char[cb.limit()];
        cb.get(cbimage, 0, cb.limit());
        return cbimage;
    }


}