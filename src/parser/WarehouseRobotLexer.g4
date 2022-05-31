lexer grammar WarehouseRobotLexer;

// calls
EVERY: 'every' -> mode(VARIABLE_MODE);
IF: 'if';
IF_NOT: 'ifNot';
CREATE_ORDER: 'create order' -> mode(ORDER_MODE);
CREATE_PRODUCTS: 'create products' -> mode(PRODUCTS_MODE);

// statements
GOTO: 'goTo' -> mode(VARIABLE_MODE);
PICKUP: 'pickup' -> mode(VARIABLE_MODE);
DROPOFF: 'dropoff' -> mode(VARIABLE_MODE);
RESTOCK: 'restock' -> mode(PRODUCTS_MODE);
FULFILL: 'fulfill' -> mode(ORDER_MODE);

// expressions
CHECK_AVAILABILTIY: 'check availability with' -> mode(ORDER_MODE);

// individual tokens
WITH: 'with';
IN: 'in' -> mode(PRODUCTS_MODE);
TO: 'to' -> mode(PRODUCTS_MODE);
FRONTHOUSE: 'fronthouse';

LEFT_BRACE:          '{';
RIGHT_BRACE:         '}';
SEMICOLON:          ';';
COMMA:               ',';

PRODUCT: 'Pear' | 'Orange' | 'Watermelon' | 'Grape';
NUM: [0-9]+;

WHITE_SPACE:         [ \t]+ -> skip;
NEW_LINE:            ( '\r' '\n'?|'\n')-> skip;

mode ORDER_MODE;

CUSTOMER_ORDER: [a-zA-Z_]+ [0-9]+ -> mode(DEFAULT_MODE);
ORDER_WHITE_SPACE:         [ \t]+ -> skip;

mode PRODUCTS_MODE;

PRODUCTS_PRODUCT: PRODUCT -> mode(DEFAULT_MODE);
RETURN_PRODUCTS: [a-zA-Z_]+ -> mode(DEFAULT_MODE);
PRODUCTS_WHITE_SPACE:         [ \t]+ -> skip;

mode VARIABLE_MODE;

VARIABLE_PRODUCT: PRODUCT -> mode(DEFAULT_MODE);
VARIABLE_FRONTHOUSE: FRONTHOUSE -> mode(DEFAULT_MODE);
VARIABLE_NAME: [a-zA-Z_]+ -> mode(DEFAULT_MODE);
VARIABLE_WHITE_SPACE: [ \t]+ -> skip;



