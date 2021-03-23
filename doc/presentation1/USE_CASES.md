# Use Cases for each Person's Responsibilities (see MANAGEMENT_PLAN for assignments)


Possible cases list: 

Choose starting piece, initialize money, reading csv of property names, creating/initializing player, creating bank, landing on/passing Go, initiliazing a property at the beginning, dealing with jail (staying in/escaping), rolling dice, tracking dice rolls (checking for doubles/3 doubles in a row...), buying house/hotel, doing a trade, buying a property, ending turn, managing properties (viewing their info/mortgaging), going to jail, landing on chance/community chest, moving to a property from a card, using get out of jail free card, paying rent to another player, paying bank, getting money from bank, handling income tax tile, winning condition (only player left), losing condition (bankrupt)...


### Wyatt

This class's purpose or value is to read/manage files/data:
```java
 public class plan.api.configuration.DataReader {
     public int getStartingBalance();
     public int getLosingBalance();
     public int getMoneyGainedPassingGo();
     public int getMaxTurnsSpentInJail();
     public List<String> getTileNames();
     public int getHouseMultiplier();
     public int getHotelMultiplier();
 }
```
The purpose of each of the five previously-defined methods is to allow the game to be as dynamic as 
possible in the sense that the established rules of the game (starting balance, losing balance, price of
propery, price of hotel, turns spent in jail, money gained from passing go, etc...) can all easily be changed
by altering values in a csv file
1.  getTileNames()

This method can be called by the controller to retrieve the proper names for each of the tiles on
the game board.  This list will be defined by reading a csv file that contains all names of every tile, in order.

2. getStartingBalance()

This method can be called by the controller to retrieve the amount of money that each player should start
with at the beginning of the game.  This value will be read from a csv file, and passed from the controller
elsewhere in the game.

3. getLosingBalance()

This method will return an int value that represents what number a player's bank account must reach 
in order to qualify them as losing the game.  Typically, this value is 0, but this method is included
in order to allow an easy change in the rules of the game based on values in a csv file. 

4. getMoneyGainedPassingGo()

The int value returned by this method will be read from a csv value, elsewhere in the plan.api.configuration.DataReader class,
and will represent how much money an individual should get from passing the Go tile.

5. getHouseMultiplier() & getHotelMultiplier()

These methods will return values to the controller (or wherever else they are needed) to indicate
the factor by which a property's price will increase by each subsequent house or hotel.  By allowing
these values to be read from a csv data file, the rules of the game are, once again, easily kept dynamic.


### Catherine

1. Case 1: Add information about the property to board tile

Actors/Collaborators: VisualTile, PropertiesReader

Flow: When the board is being set up and tiles initialized at the beginning of the game, the method setTileInformation() 
can be called to gather the information about the tile to display on the square. 

2. Case 2: Display the amount of money a player has during their turn

Actors/Collaborators: Player, PlayerPanel

Flow: When a player's turn occurs, their player panel is loaded to the right half of the display window. 
The updateMoney() function is called to display the current integer value after turns have been taken. 

3. Case 3: Display action buttons for a player during their turn

Actors/Collaborators: Player, PlayerPanel, Card

Flow: During the players turn, the createButton() function of PlayerPanel will create the buttons that 
will allow a player to do perform trades, buy houses, and end turn.

4. Case 4: Buying a house on a property

Actors/Collaborators: Player, PlayerPanel, Card, VisualTile

Flow: Player's turn comes around, Player's PlayerPlanel is displayed, and button to Buy a House is available. 
The button gives the action to select a card, and when the card/property is selected, the VisualTile 
is updated with addHouse(), so other players can see the change to the property as well. 

5. Case 5: Rolling the Dice

Actors/Collaborators: Player, PlayerPanel

Flow: When the PlayerPanel is initiated, it creates the button to roll the dice with createButton().
Then, when it becomes the player's turn, the PlayerPanel will display itself on the left side of the screen. 
The button is clicked, and PlayerPanel displays the result and piece is moved on the board.


### Ryan

1. Case 1: Clicking on Property in A Player's Dropdown menu.

Actor(s)/Collaborator(s): VisualCard, Player, PlayerPanel

Flow: When a dropdown menu is clicked on, The dropdown menu instance variable is accessed inside the current Player's 
class. Specifically, the menu displays a list of the properties owned. Then, if a property is clicked on in this list,
the action that was set to the onClick of that list item will be activated, which will be the displayCard() method
of VisualCard (this is possible, because each list represents a VisualCard/Card combo). The displayCard() method
will create a pop-up displaying all the card's relevant information.

2. Case 2: Clicking End Turn.

Actor(s)/Collaborator(s): PlayerPanel, Player, Game(?)

Flow: When the endTurn button of a given Player's PlayerPanel is clicked, the action assigned to the end turn button will be
activated. Specifically, this action will remove the current Player's PlayerPanel from the root, and add
the next Player's PlayerPanel in its place (The "next" player is defined as the player after the current in the linkedList/data structure.).

3. Case 3: Clicking the sell a house button.

Actor(s)/Collaborator(s): PlayerPanel, Player

Flow: When the sell house button in the current Player's PlayerPanel is clicked, a dialog will appear
that allows the player to choose a house to sell. When a house is selected, the appropriate amount is removed 
from their money balance and the house counter for the appropriate property is decremented.

4. Case 4: Updating the money balance of a player.

Actor(s)/Collaborator(s): Player, PlayerPanel

Flow: If the balance of a Player ever changes, the method which induced the change will also call PlayerPanel.updateMoney()
with the appropriate value as a parameter. This method will solely update the visual counter of the Player's balance
on the PlayerPanel.

5. Case 5: Choosing starting pieces.

Actor(s)/Collaborator(s): Player, Game(?), Controller (?)

Flow: After the amount of players has been chosen through Controller, a dialog should appear that
iterates through every player (by their inputted name) and provides them with a list of pieces. When a player chooses 
a piece, that is stored in their instance, and the piece is removed from the list of possible pieces for the next Player.


### Muazzam

1. Case 1: Player Rolling Dice

Actor(s)/Collaborator(s): Player, Dice, Turn

Flow: Random int selected from 1-6 and assigned to the Player current turn. The 'turn' class will call the method from Dice and allocate the value returned to the Player


2. Case 2: Moving Player

Actor(s)/Collaborator(s): Player, Board, Turn

Flow: Turn will get dice value 'x' from Player and move the Player 'x' number of spaces on the board. Board/Game class will store the Player's new location.

3. Case 3: Buying Property

Actor(s)/Collaborator(s): Player, Board, Property classes, Bank Class

Flow: If Player's wealth is sufficient, the Player can obtain new property in exchange for giving money to bank. The current property is retrieved from the player's location on the board and wealth int state is reduced by the assigned price and added to bank. Property added to property 'set'.

4. Case 4: Buying Hotels/houses

Actor(s)/Collaborator(s): Player, Board, Property classes, Bank Class, House, Hotels

Flow: If player has sufficient wealth, and owns the monopoly(all properties of same color), they can choose to buy real estate on these properties. Based upon the user's choice of property and real estate, the player's wealth will be reduced by the total price. This real estate will then be added to their set of real estate.

5. Case 5: Player passing Go

Actor(s)/Collaborator(s): Player, Turn, Board

Flow: If player position equals or moves past the GO space on the board, add 200 to the player's wealth


### Sylvie

1. Case 1: Player lands on property owned by other player (assume has enough funds to pay "rent")

Actor(s)/Collaborator(s): Player, Board, Property

Flow: Property is retrieved from player's location on the board. If property is owned by another player, rent fee
is retrieved from property information. Corresponding fee is subtracted from wealth of the player who's turn it is,
and that fee is added to the wealth of the player who owns the property.

2. Case 2: Player receives "Go to jail" card

Actor(s)/Collaborator(s): Player, Board, Card

Flow: If player receives go to jail card, their location is updated to the location on the board where jail is. 
That player's number of suspended turns is set to 3, and unless they roll doubles all turn actions will be 
suspended.

3. Case 3: Players selects avatar

Actor(s)/Collaborator(s): Player, Board

Flow: Loops through each player and player selects from a list of optional avatars and their input is taken in. 
That avatar is assigned in the Player class and then the location is set to Go space on Board.

4. Case 4: Property is mortgaged (assume contains no houses or hotels)

Actor(s)/Collaborator(s): Player, Property, Board

Flow: Property is removed from property set and added to mortgaged property set. On board, it is added to mortgaged 
property set. Player's wealth is increased by half the property value, and bank wealth is decreased by half the
property value.

5. Case 5: Player lands on chance 

Actor(s)/Collaborator(s): Player, Turn, Board, Card

Flow: If player's location is equal to the chance space on the board, the next card is retreived from the chance 
queue and the action is assigned to the player's turn. 
