grammar Metagen;
// https://www.youtube.com/watch?v=eW4WFgRtFeY

gencontextdef : ( entitydef | samplerdef)* ;

entitydef : 'entity' entityName
    ( 'pop' '=' popSize )?
    (fielddef | funcdef)*;
entityName : id;
popSize : NUMBER;
fielddef: 'field' fieldName fieldType ('func' '=' composedFuncSpec)? ;
fieldType : id;
fieldName : id ;

funcdef: 'func' funcName composedFuncSpec;
funcName : id;

samplerdef : 'sampler' samplerName (':' samplerEntity)? ('func' '=' samplerFunc)? ;
samplerEntity: id;
samplerName : id;
samplerFunc : composedFuncSpec ;

composedFuncSpec : composedFuncPart (';' composedFuncPart)* ';'? ;
composedFuncPart :  funcPartName ( '(' funcArgs ')' )? | funcPartName '(' ')' | funcPartName ;
funcArgs : assignment (',' assignment)* | value ;
assignment: parameter '=' value ;
funcPartName : id ;
parameter : id ;
value : numericValue | stringValue | stringTemplate | nonComma ;
numericValue : NUMBER ;
stringValue : '\'' ~'\'' '\'' ;
stringTemplate : '"' templateSection+ '"' ;
templateSection : templatePreamble templateVarname ;
templatePreamble : ~('"'|'${')* ;
templateVarname : '' | '${' id '}';
nonComma : ((~(','))|('.'|'-'))+? ;
//nonComma : .+? ;
id : 'entity' | 'sampler' | ID;

//DOUBLE_QUOTED: '"' ~'"'* '"';
LINE_COMMENT: '//' (~'\n')* NEWLINE -> skip ;
ID:  [a-zA-Z] [0-9a-zA-Z_-]* ;
NUMBER: [0-9]+ ( '.' [0-9]+ )? ;
WS : [ \t\n]+ -> skip ;
NEWLINE: '\r' ? '\n';

//LINE_COMMENT: '//' ​.*? ​ '\r' ​? ​ '\n' ​ -> skip ;
//COMMENT: ​ '/*' ​ .*? ​ '*/' ​ -> skip ; ​ // Match "/*" stuff "*/" ​

