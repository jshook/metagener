package com.metawiring.syntax;

import com.metawiring.generated.MetagenLexer;
import com.metawiring.generated.MetagenParser;
import com.metawiring.types.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.CharBuffer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class MetagenDefParserTest {

    @Test
    public void testParseTestSyntax() {
        char[] chars = readFile("test-syntax.metagener");
        ANTLRInputStream ais = new ANTLRInputStream(chars, chars.length);
        MetagenLexer lexer = new MetagenLexer(ais);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MetagenParser parser = new MetagenParser(tokens);
        GenContextDefListener modelBuilder = new GenContextDefListener();
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
        MetagenDef md = parseString("entity carrots pop=42343\nfield color:text <- entity\n");
        EntityDef entityDef = md.getEntityDefs().get(0);
        FieldDef color = entityDef.getFieldDefs().get(0);
        assertThat(color.getFieldName(),is("color"));
        assertThat(color.getFieldType(),is(FieldType.type_text));
        assertThat(color.getFieldFunc(),is("entity"));
    }

    @Test
    public void testParseSamplerDef() {
        MetagenDef md = parseString("sampler somefood");
        assertThat(md.getSamplerDefs().size(),is(1));
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName(),is("somefood"));
        assertThat(samplerDef.getEntityName(),is("somefood"));
        assertThat(samplerDef.getSamplerFunc(),is(nullValue()));
    }
    @Test
    public void testParseSamplerDefWithAlias() {
        MetagenDef md = parseString("sampler somefood:foosball");
        assertThat(md.getSamplerDefs().size(),is(1));
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName(),is("somefood"));
        assertThat(samplerDef.getEntityName(),is("foosball"));
        assertThat(samplerDef.getSamplerFunc(),is(nullValue()));
    }

    @Test
    public void testParseSamplerDefWithAliasAndFunction() {
        MetagenDef md = parseString("sampler somefood:foosball <- lizardgills");
        assertThat(md.getSamplerDefs().size(),is(1));
        SamplerDef samplerDef = md.getSamplerDefs().get(0);
        assertThat(samplerDef.getSamplerName(),is("somefood"));
        assertThat(samplerDef.getEntityName(),is("foosball"));
        assertThat(samplerDef.getSamplerFunc(),is("lizardgills"));
    }


    private MetagenDef parseString(String syntax) {
        ANTLRInputStream ais = new ANTLRInputStream(syntax);
        MetagenLexer lexer = new MetagenLexer(ais);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MetagenParser parser = new MetagenParser(tokens);
        GenContextDefListener modelBuilder = new GenContextDefListener();
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