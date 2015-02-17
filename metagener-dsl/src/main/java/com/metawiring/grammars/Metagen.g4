grammar Metagen;
// https://www.youtube.com/watch?v=eW4WFgRtFeY

gencontextdef : ( entitydef | samplerdef)* ;

entitydef : 'entity' entityName
    ( 'pop' '=' popSize )?
    (fielddef | funcdef)*;
entityName : id;
popSize : NUMBER;
fielddef: 'field' fieldName COLON fieldType (STORELEFT composedFuncSpec)? ;
fieldType : id;
fieldName : id ;

funcdef: 'func' funcName composedFuncSpec;
funcName : id;

samplerdef : 'sampler' samplerName (':' samplerEntity)? (STORELEFT samplerFunc)? ;
samplerEntity: id;
samplerName : id;
samplerFunc : composedFuncSpec ;

composedFuncSpec : composedFuncPart (';' composedFuncPart)* ';'? ;
composedFuncPart :  funcPartName ( '(' funcArgs ')' )? | funcPartName '(' ')' | funcPartName ;
funcArgs : assignment (',' assignment)* | value (',' value)* ;
assignment: parameter '=' value ;
funcPartName : id ;
parameter : id ;
value : stringTemplate | numericValue | nonCommaOrParen | stringValue;
numericValue : NUMBER ;
stringValue : SQUOTE ~SQUOTE*? SQUOTE ;
//stringTemplate : DOUBLE_QUOTED ;
stringTemplate : '"' templateSection*? '"' ;
templateSection : templatePreamble templateVarname ;
templatePreamble : ~('"'|'${')*? ;
templateVarname : '' | '${' id '}';
nonComma : ((~(','))|('.'|'-'|'/'))+? ;
nonCommaOrParen : ((~(','|')'))|('.'|'-'|'/'))+? ;
//nonComma : .+? ;
id : 'entity' | 'sampler' | ID;

//DOUBLE_QUOTED: '"' ~'"'*? '"';
LINE_COMMENT: '//' (~'\n')* NEWLINE -> skip ;
COMMENT: '/*' .*? '*/' -> skip;
WS : [ \t\n]+ -> skip ;
NEWLINE: '\r' ? '\n';
ID:  [a-zA-Z] [0-9a-zA-Z_-]* ;
NUMBER: [0-9]+ ( '.' [0-9]+ )? ;
STORELEFT: '<-';
COLON: ':';
SQUOTE: '\'';
DQUOTE: '"';


