# 1. Warehouse Robot Instruction Set
 
### 1) who you think would want to use your DSL 
Target users: Warehouse Operators who have no programming experience
### 2) what they would use it for
Operate a robot to move objects around warehouse/ front of house/ packing areas
### 3) why it would make sense for them to use it
A general-purpose language has many unnecessary details which would not make their job any easier. Abstracting the complexities would allow the operator to focus on the basics needed to operate the robot around the facility.

## Possible actions by Bot : 
* Pack;
* Move around;
* Provide inputs of in-stock items in the warehouse;
* Go figure out where the product is;
* Pick up the product; 
* Bring it to the packing station (bulk orders or small orders);
* Bringing to different locations (packing, outbound, error, etc).

## Display Ideas : 
* 2D map with bot being a dot, items being colored, shelves as defined regions on a cartesian plot
* Maintain a database-like structure that gets updated to reflect the user inputs and changes to stock/ inventory, etc.

## Post TA Feedback #1
* We decided to not abstract away for loops from the user
** Give the user ability to define a for loop as “Repeat Action For Each Item.”
* Allow user to set up their error handling with conditionals
** user can set up if-else statements

## Post TA Feedback #2
* Define shelves as item locations, customers can create shelves by one shelf per item rule. 
* For function restockItem, accepting more parameters. 
* The modified prototype, new and old version have been updated to github repo.
* Two user studies are done, one with the old prototype and one with the new prototype

Frontend : JavaFx/ Java2D
Backend : Java

## Implementation Plan :
* Work as a group to finalize command names, grammar and node structure
* Pair program to connect lexer/parser to AST design
* Display funcitonality on console/ JavaFX GUI

## Project Scope Update :
* Decided to be less GUI focused and add more complexity to the language with varibales, arrays, loops and conditionals

## User Study Task :
Try to bring the item to the front house to sell. If the shelf is empty, check the capacity of the shelf and order many more items and restock.

## Possible Instructions: 
* if
* ifNot
* goTo;
* pickUp;
* dropOff;
* restockOrder;
* create;
* fulfill;
* every product in;
* checkAvailability

## Sample User command: 
GoTo(Warehouse)
	if CheckAvailable()
		PickUpItem
		GoTo(Fronthouse)
	else
		variableNumberOfItems = CheckSizeOfShelf
		OrderNumberOfiTems(Variable)
RepeatActionForEachItem(variableNumberOfItems , action)

## Division of Labor:
Three people do the code
* Sugar, Jason, Trevor
One for video demo 
* Matt
One for user study
* Zibo



