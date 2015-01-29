package com.metawiring.syntax;

import com.datastax.powertools.ripsaw.simulation.parser.RipSawParser;
import org.parboiled.Parboiled;
import org.parboiled.common.StringBuilderSink;
import org.parboiled.errors.ErrorUtils;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.DefaultValueStack;
import org.parboiled.support.ParseTreeUtils;
import org.parboiled.support.ParsingResult;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Test(groups = {"parser"}, sequential = true, singleThreaded = true)
public class TestRipSawParser {

    //@Test
    public void testAssignmentParser() {
        testParserInstance("assignment.ripsaw");
    }

    //@Test
    public void testBlockParser() {
        testParserInstance("blocks.ripsaw");
    }

    @Test
    public void testFullSyntax() {
        testParserInstance("full_syntax.ripsaw");
    }

    @Test
    public void testCallSyntax() {
        testParserInstance("call.ripsaw");
    }


    private void testParserInstance(String inputFile) {
        char[] input = readFile(inputFile);
        StringBuilderSink tracelog;
        tracelog = new StringBuilderSink();
        RipSawParser parser = Parboiled.createParser(RipSawParser.class);

        ParseRunner<Object> r;
//        r = (ReportingParseRunner<DASTBaseNode>) new ReportingParseRunner<DASTBaseNode>(parser.TestSet()).withValueStack(new DefaultValueStack<DASTBaseNode>());
        r = (ReportingParseRunner<Object>) new ReportingParseRunner<Object>(parser.TestSet()).withValueStack(new DefaultValueStack<Object>());
//        ReportingParseRunner<DASTNode> r = (ReportingParseRunner<DASTNode>) new ReportingParseRunner<DASTNode>(parser.TestSet()).withValueStack(new DefaultValueStack<DASTNode>());
//        r = new TracingParseRunner<Object>(parser.TestSet()).withLog(tracelog);
//        ErrorReportingParseRunner<Object> r = new ErrorReportingParseRunner<Object>(parser.TestSet(),0);
//        RecoveringParseRunner<Object> r = new RecoveringParseRunner<Object>(parser.TestSet());

        ParsingResult parsingResult;
        parsingResult = r.run(input);

        Object result = parsingResult.resultValue;
        System.out.println(parsingResult.matched);
        System.out.println("NODE TREE\n" + ParseTreeUtils.printNodeTree(parsingResult));

        if (parsingResult.hasErrors()) {
            System.out.println("ERROR parsing " + inputFile);
            System.out.println(ErrorUtils.printParseErrors(parsingResult));
            System.out.println("VALUESTACK\n" + parsingResult.valueStack);
            System.out.println("BUFFER\n" + parsingResult.inputBuffer);

            if (tracelog.toString().length() > 0) {

                System.out.println(tracelog);
            }
//            for(Object pe : parsingResult.parseErrors) {
//                ErrorUtils.printParseError((ParseError)pe);
//                System.out.println(((ParseError)pe).getErrorMessage());
//            }
        }
        System.out.flush();
        assertThat("embed.ripsaw should parse", parsingResult.matched, is(true));

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
