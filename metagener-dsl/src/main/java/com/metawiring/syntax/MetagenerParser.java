package com.metawiring.syntax;

import org.parboiled.BaseParser;
import org.parboiled.Context;
import org.parboiled.Rule;
import org.parboiled.annotations.*;
import org.parboiled.matchers.ZeroOrMoreMatcher;

@BuildParseTree
public class MetagenerParser extends BaseParser<Object> {

    public Rule Config() {
        return Sequence(
            ZeroOrMore(
                FirstOf(
                        EntityDefinition(),
                        SamplerDefinition()
                )
            ),
                EOI
        );
    }

    public Rule EntityDefinition() {
        return Sequence(
                ENTITY, ZeroOrMore(FieldDefinition())
        );
    }

    public Rule FieldDefinition() {
        return Sequence(
            FIELD, Identifier().label("fieldname")
        );
    }

    public Rule SamplerDefinition() {
        return Sequence(
            SAMPLER, Identifier().label("samplername"), DoubleQuotedValue().label("function"), NewLine()
        );
    }

    public Rule NamedParameter() {
        return Sequence(
                Identifier(),
                COLON,
                DoubleQuotedValue().label("value"),
                Spacing()
        );
    }

    public Rule IdentifierPart() {
        return Sequence(
                Letter(), ZeroOrMore(LetterOrNumber())
        );
    }

    @SuppressSubnodes
    public Rule Identifier() {
        return Sequence(
                IdentifierPart(),
                ZeroOrMore('.', IdentifierPart())
        );
    }

    @SuppressSubnodes
    public Rule Name() {
        return Sequence(
                Letter(), ZeroOrMore(AlphaNumDash()), Spacing()
        );
    }

    @SuppressSubnodes
    public Rule LiteralValue() {
        return OneOrMore(LetterOrNumber()
                //,push(new DASTLiteralValue().setText(matchOrDefault("")))
        );
    }

    @SuppressSubnodes
    public Rule DoubleQuotedValue() {
        return Sequence(
                '"',
                OneOrMore(TestNot('"'), ANY),
                '"'
                //,push(new DASTDoubleQuotedValue().setText(matchOrDefault("")))
        );
    }

    @SuppressSubnodes
    public Rule SingleQuotedValue() {
        return Sequence(
                '\'',
                OneOrMore(TestNot('\''), NotNewLine(), ANY),
                '\''
                //,push(new DASTSingleQuotedValue().setText(matchOrDefault("")))
        );
    }

    @SuppressSubnodes
    public Rule EscapedCharacter() {
        return Sequence(
                '\\', FirstOf('r', 'n', 't', '\'')
        );
    }

    public Rule Letter() {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'));
    }

    public Rule LetterOrNumber() {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), CharRange('0', '9'));
    }

    public Rule AlphaNumDash() {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), CharRange('0', '9'), '-');
    }

    public Rule AlphaNumDashDot() {
        return FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'), CharRange('0', '9'), '-', '.');
    }

    @SuppressNode
    public Rule NewLine() {
        return FirstOf('\n', Sequence('\r', Optional('\n')));
    }

    @SuppressNode
    public Rule NotNewLine() {
        return TestNot(NewLine());
    }

    final Rule RWING = Terminal("}");
    final Rule LWING = Terminal("{");
    final Rule COLON = Terminal(":");
    final Rule LPAREN = Terminal("(");
    final Rule RPAREN = Terminal(")");
    final Rule COMMA = Terminal(",");
    final Rule DOT = Terminal(".");

    final Rule ENTITY = Terminal("entity");
    final Rule FIELD = Terminal("field");
    final Rule SAMPLER = Terminal("sampler");

    final Rule SEMI = Terminal(";");
    final Rule TILDE = Terminal("~");
    final Rule IS_EQUAL_TO = Terminal("==");
    final Rule ASSIGNMENT = Terminal("=");
    final Rule GENERATOR_BLOCK = Terminal("generator");
    final Rule TEMPLATE_BLOCK = Terminal("template");
    final Rule FLOW_BLOCK = Terminal("flow");
    final Rule LIBRARY = Terminal("library");
    final Rule JS_BLOCK = Terminal("js");
    final Rule GROUP_BLOCK = Terminal("group");

    @SuppressNode
    Rule Spacing() {
        return ZeroOrMore(FirstOf(

                // whitespace
                OneOrMore(AnyOf(" \t\r\n\f").label("Whitespace")),

                // traditional comment
                Sequence("/*", ZeroOrMore(TestNot("*/"), ANY), "*/"),

                // end of line comment
                Sequence(
                        "//",
                        ZeroOrMore(TestNot(AnyOf("\r\n")), ANY),
                        FirstOf("\r\n", '\r', '\n', EOI)
                )
        ));
    }

    @SuppressNode
    @DontLabel
    Rule Terminal(String string) {
        return Sequence(string, Spacing()).label('\'' + string + '\'');
    }

    boolean debug(Context context) {
        return true; // set permanent breakpoint here
    }

}
