### Introduction
We will be creating a game of Monopoly, and if time permits, we will try to create multiple
"themes" (i.e. Duke Monopoly, European Monopoly, etc.). The goal is to make the flexibility 
of this program lie in the ability to input any type of theme based on data in a CSV file. 
Up to a reasonable point, we plan to have flexibility in the number of players. The design 
will include a backend that models a game of Monopoly, a UI, and a controller that links the
two, as well as a sector that handles the data necessary to set up a themed game.  The majority
of the backend and controller will be closed, but the ability to add unique 
features or rules will be open.

### Overview
Backend: Game logic classes, Board element classes, Player classes, event classes, turn classes
The individual turn classes will implement the event and element classes to create actions for an 
individual turn. Game logic classess will contain the game logic and implement the turn classes 
for the various players. 

Controller: Controller classes
Controller will link the back end classes with the front end classes and run the game in Main.

Front End: Visual classes
These will combine to create the visual implementation of a Monopoly board for the user to view.

### Design Details
Backend: Player Class, plan.api.configuration.DataReader Class, Tile Class, Card abstract Class, Card Classes 
(Chance class, Community Chest), Bank Class, Game Class (overall game), Individual turn Class,
Buying Class, Negotiation Class

Controller:  Control Panel Class, Board Class, Main

Front end: Board View, Panel View, Tile View, Card View

### Example Games
1. Basic Monopoly
    * Classic board view
    * Regular Monopoly rules
2. Duke Monopoly
    * Duke locations on the board (i.e. Duke Chapel, Bryan Center, WU...)
    * New rules in cards: pay student loans, suspension, Bryan Center parking ticket
    * Dorms and study rooms instead of houses and hotels
3. European Monopoly
    * European countries/cities on the board, different player tokens
    * New rules in cards: visa expired (cannot move for 2 turns), deported (back to previous space), 
    if you enter EU, pass GO
    * Currency in EU instead of USD, currency exchange fee

### Design Considerations
* Assume that negotiations take place in person and if a trade is agreed upon, it can then 
be inputted into the game program.
* Players are not abstract
* Eliminate reliance on game class by implementing logic in object classes when appropriate
    * such as in Player, Property, etc.
* Create board using a combination of JavaFX elements and Monopoly images



=======
### Potential Classes

1. Player Class
2. plan.api.configuration.DataReader Class
3. Tile Class

    3.5. VisualTile (for frontend)
4. Card Classes (Chance class, Community Chest) 

    4.5 VisualCard (for frontend)
5. Bank Class
6. Game Class (overall game)
7. Controller Class