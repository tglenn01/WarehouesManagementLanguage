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

## Post TA Feedback
* We decided to not abstract away for loops from the user
** Give the user ability to define a for loop as “Repeat Action For Each Item.”
* Allow user to set up their error handling with conditionals
** user can set up if-else statements


## User Study Task :
Try to bring the item to the front house to sell. If the shelf is empty, check the capacity of the shelf and order many more items and restock.

## Possible Instructions: 
* If Else;
* GoTo;
* PickUpItem;
* DropOffItem;
* OrderNumberOfItems();
* RepeatFor;
* Restock;
* CheckSizeOfShelf;
* CheckAvailability.

## Sample User command: 
GoTo(Warehouse)
	if CheckAvailable()
		PickUpItem
		GoTo(Fronthouse)
	else
		variableNumberOfItems = CheckSizeOfShelf
		OrderNumberOfiTems(Variable)
RepeatActionForEachItem(variableNumberOfItems , action)

## Basic Documentation:
goto (....) //options: warehouse or fronthouse
if warehouse: notify the users it’s already at location 
pickupitem()
return seg fault if empty
dropoffitem() // works only for front house
check_available()
//for if statement 
restockitem()
repeat(//action)
throw error if wrong input 

## Division of Labor:
Three people do the code
* Sugar, Jason, Trevor
One for video demo 
* Matt
One for user study
* Zibo



