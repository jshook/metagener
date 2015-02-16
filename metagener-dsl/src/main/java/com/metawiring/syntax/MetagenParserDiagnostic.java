package com.metawiring.syntax;

import org.antlr.v4.runtime.*;

import java.util.ArrayList;
import java.util.List;

public class MetagenParserDiagnostic extends BaseErrorListener {
    private List<String> diagnosticLog = new ArrayList<String>();
    private List<SyntaxError> syntaxErrors = new ArrayList<SyntaxError>();


    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        syntaxErrors.add(new SyntaxError(recognizer,offendingSymbol,line,charPositionInLine,msg,e));
        StringBuilder sb = new StringBuilder();
        sb.append("line ").append(line).append(":").append(charPositionInLine).append(" ").append(msg);
        underlineError(sb,recognizer,(Token)offendingSymbol, line, charPositionInLine);
        diagnosticLog.add(sb.toString());
    }

    private void underlineError(StringBuilder sb, Recognizer<?, ?> recognizer, Token offendingSymbol, int line, int charPositionInLine) {
        CommonTokenStream tokens = (CommonTokenStream) recognizer.getInputStream();
        String input = tokens.getTokenSource().getInputStream().toString();
        String[] lines = input.split("\n");
        String errorLine = lines[line -1];
        for (int i=0;i<charPositionInLine;i++) sb.append(" ");
        int start = offendingSymbol.getStartIndex();
        int stop = offendingSymbol.getStopIndex();
        if (start >=0 && stop>=0) {
            for (int i=start; i<=stop;i++) sb.append("^");
        }
        sb.append("\n");
    }

    public boolean hasErrors() {
        return (syntaxErrors.size()>0);
    }

    public String getErrorSummary() {
        return diagnosticLog.stream().reduce((t,u) -> t + u).get();
    }

    public List<String> getErrorLog() {
        return diagnosticLog;
    }

    public List<SyntaxError> getSyntaxErrors() {
        return syntaxErrors;
    }

    public static class SyntaxError {
        public Recognizer<?,?> recognizer;
        public Object offendingSymbol;
        public int line;
        public int charPositionInLine;
        public String message;
        public RecognitionException e;

        public SyntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String message, RecognitionException e) {
            this.recognizer = recognizer;
            this.offendingSymbol = offendingSymbol;
            this.line = line;
            this.charPositionInLine = charPositionInLine;
            this.message = message;
            this.e = e;
        }
    }

}
