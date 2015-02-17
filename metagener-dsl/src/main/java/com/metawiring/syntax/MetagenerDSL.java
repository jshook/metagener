package com.metawiring.syntax;

import com.metawiring.generated.MetagenLexer;
import com.metawiring.generated.MetagenParser;
import com.metawiring.types.MetagenDef;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.CharBuffer;

public class MetagenerDSL {
    private static Logger logger = LoggerFactory.getLogger(MetagenerDSL.class);

    public static MetagenerDSLParserResult fromSyntax(String defdata) {
        ANTLRInputStream ais = new ANTLRInputStream(defdata);
        return fromANTLRStream(ais);
    }

    public static MetagenerDSLParserResult fromFile(String filename) {
        char[] filedata = readFile(filename);
        ANTLRInputStream ais = new ANTLRInputStream(filedata, filedata.length);
        return fromANTLRStream(ais);
    }

    public static String toSyntax(MetagenDef metagenDef) {
        throw new RuntimeException("This is not ready yet.");
//        return MetagenDefStringer.toSyntax(metagenDef);

    }

    private static MetagenerDSLParserResult fromANTLRStream(ANTLRInputStream inputstream) {
        try {
            MetagenLexer lexer = new MetagenLexer(inputstream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            MetagenParser parser = new MetagenParser(tokens);
            MetagenerDSLModelBuilder modelBuilder = new MetagenerDSLModelBuilder();
            MetagenerDSLErrorHandler errorHandler = new MetagenerDSLErrorHandler();
            parser.addParseListener(modelBuilder);
            parser.addErrorListener(errorHandler);
            MetagenParser.GencontextdefContext parseTree = parser.gencontextdef();
            //System.out.println(parseTree.toStringTree(parser));
            return new MetagenerDSLParserResult(modelBuilder.getGenContextDef(),errorHandler);
        } catch (Exception e) {
            logger.error("Fatal error while trying to parse Metagener defs:" + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    private static char[] readFile(String filename) {
        InputStream resourceAsStream= null;
        try {
            resourceAsStream = new FileInputStream(filename);
        } catch (Exception ignored) {
        }

        if (resourceAsStream==null) {
            resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
        }

        if (resourceAsStream==null) {
            throw new RuntimeException("Unable to find file in filesystem or in class path:" + filename);
        }

        BufferedReader sr = new BufferedReader(new InputStreamReader(resourceAsStream));
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
