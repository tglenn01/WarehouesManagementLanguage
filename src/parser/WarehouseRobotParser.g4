parser grammar WarehouseRobotParser;
options { tokenVocab=WarehouseRobotLexer; }

program:           (runnable_nodes)* ;

// lines of codes that run themselves, aka not arugments and expresssions, aka methods
runnable_nodes:    call | structure | statement ;

// node types
argument:          order_varname | products_varname | variable_varname | PRODUCT | NUM ;
call:              create_order | create_products ;
structure:         loop | if | if_not ;
statement:         goto | pickup | dropoff | restock_order | fulfill | add;
expression:        check_order_availability | check_product_availability ;

// arguments
iterable:          order_varname | products_varname ;
order_varname:     CUSTOMER_ORDER ;
products_varname:  RETURN_PRODUCTS ;
variable_varname:  VARIABLE_NAME ;

// calls
create_order:      CREATE_ORDER order_varname (WITH NUM PRODUCT (COMMA NUM PRODUCT)*)? SEMICOLON ;
create_products:   CREATE_PRODUCTS products_varname SEMICOLON ;

// structures
loop:              EVERY variable_varname (IN_ORDER | IN_PRODUCTS) iterable LEFT_BRACE (runnable_nodes)* RIGHT_BRACE ;
if:                IF expression LEFT_BRACE (runnable_nodes)* RIGHT_BRACE ;
if_not:            IF_NOT expression LEFT_BRACE (runnable_nodes)* RIGHT_BRACE ;

// statements
goto:              GOTO (VARIABLE_PRODUCT | VARIABLE_FRONTHOUSE | variable_varname) SEMICOLON ;
pickup:            PICKUP NUM_VARIABLE (VARIABLE_PRODUCT | variable_varname) TO_PRODUCTS products_varname SEMICOLON ;
dropoff:           DROPOFF (VARIABLE_PRODUCT | variable_varname) FROM_PRODUCTS products_varname SEMICOLON ;
restock_order:     RESTOCK (PRODUCTS_PRODUCT | products_varname) WITH_ORDER order_varname SEMICOLON;
fulfill:           FULFILL order_varname WITH_PRODUCTS products_varname SEMICOLON ;
add:               ADD NUM PRODUCT TO_ORDER order_varname SEMICOLON ;

// expressions
check_order_availability: CHECK_ORDER_AVAILABILTIY order_varname ;
check_product_availability: CHECK_PRODUCTS_AVAILABILTIY NUM PRODUCT ;
