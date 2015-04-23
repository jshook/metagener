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

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(modelBuilder.hasErrors()).isEqualTo(false);
        MetagenDef genContextDef = modelBuilder.getGenContextDef();

    }

    @Test
    public void testParseEntity() {
        MetagenDef md = parseString("entity parsley");
        assertThat(md.getEntityDefs().size()).isEqualTo(1);
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat(entityDef.getName()).isEqualTo("parsley");
        assertThat(entityDef.getPopulationSize()).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    public void testParseEntityWithPopulation() {
        MetagenDef md = parseString("entity carrots pop=42343");
        assertThat(md.getEntityDefs().size()).isEqualTo(1);
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat(entityDef.getName()).isEqualTo("carrots");
        assertThat(entityDef.getPopulationSize()).isEqualTo(42343l);
    }

    @Test
    public void testParseEntityField() {
        MetagenDef md = parseString("entity carrots pop=42343\nfield color text\n");
        assertThat(md.getEntityDefs().size()).isEqualTo(1);
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat(entityDef.getName()).isEqualTo("carrots");
        assertThat(entityDef.getPopulationSize()).isEqualTo(42343l);
        assertThat(entityDef.getFieldDefs().size()).isEqualTo(1);
        FieldDef color = entityDef.getFieldDefs().get(0);
        assertThat(color.getFieldName()).isEqualTo("color");
        assertThat(color.getFieldType()).isEqualTo(FieldType.TEXT);
        assertThat(color.getFieldFunc()).isNull();
    }


    @Test
    public void testParseEntityFieldWithFunction() {
        MetagenDef md = parseString("entity carrots pop=42343\nfield color:text <- entity()\n");
        EntityDef entityDef = md.getEntityDefs().get(0);
        FieldDef color = entityDef.getFieldDefs().get(0);
        assertThat(color.getFieldName()).isEqualTo("color");
        assertThat(color.getFieldType()).isEqualTo(FieldType.TEXT);
        assertThat(color.getFieldFunc()).isEqualTo("entity()");
        assertThat(color.getFieldFuncDef().getFuncName()).isNull();
    }

    // This probably neeeds to be rewritten after function calls are added
    @Test(enabled=false)
    public void testParseEntityFieldWithFunctionAssignment() {
        MetagenDef md = parseString("entity carrots pop=42343\nfield color:text <- fassign=entity()\n");
        EntityDef entityDef = md.getEntityDefs().get(0);
        FieldDef color = entityDef.getFieldDefs().get(0);
        assertThat(color.getFieldName()).isEqualTo("color");
        assertThat(color.getFieldType()).isEqualTo(FieldType.TEXT);
        assertThat(color.getFieldFunc()).isEqualTo("fassign=entity()");
    }

    @Test
    public void testParseSamplerDef() {
        MetagenDef md = parseString("sampler somefood");
        assertThat(md.getSamplerDefs().size()).isEqualTo(1);
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName()).isEqualTo("somefood");
        assertThat(samplerDef.getEntityName()).isEqualTo("somefood");
        assertThat(samplerDef.getSamplerFuncDef()).isNull();
    }
    @Test
    public void testParseSamplerDefWithAlias() {
        MetagenDef md = parseString("sampler somefood:foosball");
        assertThat(md.getSamplerDefs().size()).isEqualTo(1);
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName()).isEqualTo("somefood");
        assertThat(samplerDef.getEntityName()).isEqualTo("foosball");
        assertThat(samplerDef.getSamplerFuncDef()).isNull();
    }

    @Test
    public void testParseSamplerDefWithAliasAndFunction() {
        MetagenDef md = parseString("sampler somefood:foosball <- lizardgills()");
        assertThat(md.getSamplerDefs().size()).isEqualTo(1);
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName()).isEqualTo("somefood");
        assertThat(samplerDef.getEntityName()).isEqualTo("foosball");
        assertThat(samplerDef.getSamplerFuncDef().getFuncName()).isNull();
        assertThat(samplerDef.getSamplerFuncDef().getFuncCallDefs().get(0).getFuncName()).isEqualTo("lizardgills");
    }


    @Test
    public void testParseEntityDefFieldWithFuncDef() {
        MetagenDef md = parseString("entity foo\nfield bar:text <- funca(one,two)");
        assertThat(md.getEntityDefs().size()).isEqualTo(1);
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat( entityDef.getFieldDefs().size()).isEqualTo(1);
        FieldDef fieldBar = entityDef.getFieldDef("bar");
        assertThat(fieldBar).isNotNull();
        assertThat(fieldBar.getFieldFuncDef()).isNotNull();
        FuncDef fieldFuncDef = fieldBar.getFieldFuncDef();
        assertThat(fieldFuncDef.getFuncCallDefs().size()).isEqualTo(1);
        List<FuncCallDef> funcCallDefs = fieldFuncDef.getFuncCallDefs();

        FuncCallDef fcd0 = funcCallDefs.get(0);
        assertThat(fcd0.getFuncName()).isEqualTo("funca");
        assertThat(fcd0.getFuncArgs().size()).isEqualTo(2);
        assertThat(fcd0.getFuncArgs().get(0)).isEqualTo("one");
        assertThat(fcd0.getFuncArgs().get(1)).isEqualTo("two");
    }

    @Test
    public void testFieldFunctionDefProvidesSimpleParameterValues() {
        MetagenDef md = parseString("entity foo\nfield bar:text <- funca(1,5,10)");
        assertThat(md.getEntityDefs().size()).isEqualTo(1);
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat(entityDef.getFieldDefs().size()).isEqualTo(1);
        FieldDef fieldDef = entityDef.getFieldDefs().get(0);
        FuncDef fieldFuncDef = fieldDef.getFieldFuncDef();
        assertThat(fieldFuncDef).isNotNull();
        assertThat(fieldFuncDef.getFuncCallDefs().size()).isEqualTo(1);
        FuncCallDef funcCallDef = fieldFuncDef.getFuncCallDefs().get(0);
        List<String> funcArgs = funcCallDef.getFuncArgs();
        assertThat(funcArgs.size()).isEqualTo(3);
    }

    @Test
    public void testParseEntityDefFieldWithFuncDefs() {
        MetagenDef md = parseString("entity foo\nfield bar:text <- funca();funcb(one,two);funcc(p1=v1,p2=v2)");
        assertThat(md.getEntityDefs().size()).isEqualTo(1);
        EntityDef entityDef = md.getEntityDefs().get(0);
        assertThat( entityDef.getFieldDefs().size()).isEqualTo(1);
        FieldDef fieldBar = entityDef.getFieldDef("bar");
        assertThat(fieldBar).isNotNull();
        assertThat(fieldBar.getFieldFuncDef()).isNotNull();
        FuncDef fieldFuncDef = fieldBar.getFieldFuncDef();
        assertThat(fieldFuncDef.getFuncCallDefs().size()).isEqualTo(3);
        List<FuncCallDef> funcCallDefs = fieldFuncDef.getFuncCallDefs();

        FuncCallDef fcd0 = funcCallDefs.get(0);
        assertThat(fcd0.getFuncName()).isEqualTo("funca");
        assertThat(fcd0.getFuncArgs().size()).isEqualTo(0);

        FuncCallDef fcd1 = funcCallDefs.get(1);
        assertThat(fcd1.getFuncName()).isEqualTo("funcb");
        assertThat(fcd1.getFuncArgs().size()).isEqualTo(2);
        assertThat(fcd1.getFuncArgs().get(0)).isEqualTo("one");
        assertThat(fcd1.getFuncArgs().get(1)).isEqualTo("two");

        FuncCallDef fcd2 = funcCallDefs.get(2);
        assertThat(fcd2.getFuncName()).isEqualTo("funcc");
        assertThat(fcd2.getFuncArgs().size()).isEqualTo(2);
        assertThat(fcd2.getFuncArgs().get(0)).isEqualTo("p1=v1");
        assertThat(fcd2.getFuncArgs().get(1)).isEqualTo("p2=v2");

    }

    @Test
    public void testParseEntityDefFieldWithPathNameArgs() {
        MetagenDef md = parseString("entity foo\nfield bar:text <- funca(/file/path);");
        FieldDef fieldDef = md.getEntityDefs().get(0).getFieldDefs().get(0);
        assertThat(fieldDef).isNotNull();
        FuncCallDef funcCallDef = fieldDef.getFieldFuncDef().getFuncCallDefs().get(0);
        assertThat(funcCallDef).isNotNull();
        assertThat(funcCallDef.getFuncArgs().size()).isEqualTo(1);
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