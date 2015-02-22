package com.metawiring.syntax;

import com.metawiring.generated.MetagenLexer;
import com.metawiring.generated.MetagenParser;
import com.metawiring.types.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MetagenRetailDefParserTest {

    @Test
    public void testParseRetailSyntax() {
        MetagenerDSLParserResult metagenerDSLParserResult = MetagenerDSL.fromFile("retail.metagener");

        if (metagenerDSLParserResult.hasErrors()) {
            System.out.println(metagenerDSLParserResult.getErrorSummary());
        }
        assertThat(metagenerDSLParserResult.hasErrors(),is(false));
    }

}