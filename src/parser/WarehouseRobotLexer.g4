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
ADD: 'add';

// expressions
CHECK_AVAILABILTIY: 'check availability of' -> mode(ORDER_MODE);

// individual tokens
WITH_ORDER: 'with order' -> mode(ORDER_MODE);
WITH_PRODUCTS: 'with products' -> mode(PRODUCTS_MODE);

WITH: 'with';
OF: 'of' ;

IN_ORDER: 'in order' -> mode(ORDER_MODE);
IN_PRODUCTS: 'in products' -> mode(PRODUCTS_MODE);

TO_ORDER: 'to order' -> mode(ORDER_MODE);
TO_PRODUCTS: 'to products' -> mode(PRODUCTS_MODE);

FRONTHOUSE: 'fronthouse';

LEFT_BRACE:          '{';
RIGHT_BRACE:         '}';
SEMICOLON:          ';';
COMMA:               ',';

PRODUCT: 'Pear' | 'Orange' | 'Watermelon' | 'Grape' | 'Peanut' | 'Apple' | 'Banana';
NUM: [0-9]+;

WHITE_SPACE:         [ \t]+ -> skip;
NEW_LINE:            ( '\r' '\n'?|'\n')-> skip;

mode ORDER_MODE;

ORDER_PRODUCT: PRODUCT -> mode(DEFAULT_MODE);
CUSTOMER_ORDER: [a-zA-Z_]+ [0-9]+ -> mode(DEFAULT_MODE);
NUM_ORDER: [0-9]+;
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



