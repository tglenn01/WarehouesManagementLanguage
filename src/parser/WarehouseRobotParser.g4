parser grammar WarehouseRobotParser;
options { tokenVocab=WarehouseRobotLexer; }

program:         (call | statement | expression)*;

argument:         order_varname | products_varname | variable_varname | PRODUCT | NUM;
call:             loop | if | if_not | create_order | create_products ;
statement:        goto | pickup | dropoff | restock_order | fulfill | add;
expression:       check_availiblity ;

// arguments
iterable:         order_varname | products_varname ;
order_varname:    CUSTOMER_ORDER ;
products_varname: RETURN_PRODUCTS ;
variable_varname: VARIABLE_NAME ;

// calls
loop:             EVERY variable_varname (IN_ORDER | IN_PRODUCTS) iterable LEFT_BRACE (statement | call)* RIGHT_BRACE ;
if:               IF expression LEFT_BRACE (statement | call)* RIGHT_BRACE ;
if_not:           IF_NOT expression LEFT_BRACE (statement | call)* RIGHT_BRACE ;
create_order:     CREATE_ORDER order_varname (WITH NUM PRODUCT (COMMA NUM PRODUCT)*)? SEMICOLON ;
create_products:  CREATE_PRODUCTS products_varname SEMICOLON ;

// statements
goto:             GOTO (VARIABLE_PRODUCT | VARIABLE_FRONTHOUSE | variable_varname) SEMICOLON ;
pickup:           PICKUP (VARIABLE_PRODUCT | variable_varname) TO_PRODUCTS products_varname SEMICOLON ;
dropoff:          DROPOFF (VARIABLE_PRODUCT | variable_varname) SEMICOLON ;
restock_order:    RESTOCK (PRODUCTS_PRODUCT | products_varname) WITH_ORDER order_varname SEMICOLON;
fulfill:          FULFILL order_varname WITH_PRODUCTS products_varname SEMICOLON ;
add:              ADD NUM PRODUCT TO_ORDER order_varname SEMICOLON ;

// expressions
check_availiblity:CHECK_AVAILABILTIY ((NUM_ORDER ORDER_PRODUCT) | order_varname) ;
