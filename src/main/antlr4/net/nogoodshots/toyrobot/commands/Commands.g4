grammar Commands;

input: line+;

line: blank_line # BlankLine
    | comment # Com
    | action # Act
    ;

blank_line : blank;
blank : (WHITESPACE)* NL;

comment_line: comment;
comment : COMMENT;

//action_line: action;
action : (place_action | move_action | left_action | right_action | report_action);

place_action : PLACE NUMBER COMMA NUMBER COMMA DIRECTION;
move_action  : MOVE;
left_action  : LEFT;
right_action  : RIGHT;
report_action  : REPORT;

COMMENT : '#' ~[\r\n]*;

PLACE      : 'PLACE';
MOVE       : 'MOVE';
LEFT       : 'LEFT';
RIGHT      : 'RIGHT';
REPORT     : 'REPORT';
DIRECTION  : 'NORTH' | 'SOUTH' | 'EAST' | 'WEST';

NUMBER     : [0-9]+ ;
COMMA      : [,];
WHITESPACE : [ \t]+ -> skip ; // skip spaces, tabs
NL:  [\r\n] -> skip; // newlines