package com.metawiring.syntax;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ErrorReportingParseRunner;
import org.parboiled.parserunners.ParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.DefaultValueStack;
import org.parboiled.support.ParsingResult;
import org.testng.annotations.Test;

public class MetagenerParserTest {

    @Test
    public void testParseEntity() {
        String entity="entity alpha";
        ParseRunner<Object> pr = createParseRunner();
        ParsingResult<Object> result = pr.run(entity);
        System.out.println(result);
    }

    public ParseRunner<Object> createParseRunner() {
        MetagenerParser parser = Parboiled.createParser(MetagenerParser.class);
        ParseRunner<Object> runner = new ReportingParseRunner<Object>(parser.Config()).withValueStack(new DefaultValueStack<Object>());
        return runner;
    }

}