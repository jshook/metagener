package com.metawiring.syntax;

import com.metawiring.generated.MetagenLexer;
import com.metawiring.generated.MetagenListener;
import com.metawiring.generated.MetagenParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.CharBuffer;

public class MetagenDefListenerTest {

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

        modelBuilder.getGenContextDef();

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