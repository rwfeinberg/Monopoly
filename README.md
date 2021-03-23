final
====

This project implements a player for multiple related games.

Names: Catherine Livingston, Wyatt Focht, Sylvie Mason, Muazzam Khan Noorpuri, Ryan Feinberg


### Timeline

Start Date: 10/23/20

Finish Date: 11/18/20

Hours Spent: 300 (approx. 60 X person)

### Primary Roles

#### Catherine Livingston
* Visualization

#### Wyatt Focht
* Configuration/Data                                                                                

#### Sylvie Mason:
* Backend

#### Muazzam Khan Noorpuri
* Backend

#### Ryan Feinberg
* Visualization


### Resources Used
1. https://docs.oracle.com/javase/tutorial/reflect/index.html, reflection
2. https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html, lambdas
3. thumbnail from https://www.youtube.com/watch?v=iyHe2Gd7_jA, image for Extreme Monopoly
4. https://ashleylewis.design/portfolio/ocean-beach-opoly/, image for Beach Monopoly
5. https://www.ebay.com/i/264925791542?chn=ps&norover=1&mkevt=1&mkrid=711-117182-37290-0&mkcid=2&itemid=264925791542&targetid=935431405413&device=c&mktype=pla&googleloc=9009748&poi=&campaignid=10829254281&mkgroupid=106351005733&rlsatarget=pla-935431405413&abcId=9300396&merchantid=113370070&gclid=Cj0KCQiAqdP9BRDVARIsAGSZ8AmsJ0i651W9XmRtY3Iw0oGBe0uYMnJnI2wZpo_WmVf_2iVQIna0rxcaAh4eEALw_wcB, image for Duke Monopoly
6. https://www.pinterest.com/pin/351914158386339428/, chance card
7. https://monopoly.fandom.com/wiki/Community_Chest, community chest card


### Running the Program

Main class: Main.java

Data files needed: 

*allGameTypesButtons.csv

1. For game of gameType Xxx
* Inside subdirectory in data, named xxx
1.XxxChance.csv
2.XxxCommunity.csv
3.XxxTiles.csv
* Inside subdirectory in resources, named xxx
1.XxxChanceEnglish.properties
2.XxxChanceSpanish.properties
3.XxxCommunityEnglish.properties
4.XxxCommunitySpanish.properties
* Within images subdirectory in resources
1.XxxButton.jpeg
2.Other various jpeg's if it is desired to overwrite the images for go, go to jail, jail, etc...
        



Features implemented:

1.Configuration (Wyatt)
* Ability to read both csv files and zip files of csv files
* Read in possible game types (for display) and corresponding buttons to display for each game type from data file
* Ability to read in possible chance/community cards to be used in a game given a game type
* Ability to read in game rules given a game type
* Ability to randomly generate certain rules randomly, rather than reading in from a file
* Ability to read in all tiles (and corresponding color, rent (if applicable), tile type) from file for a given game type

2.Model (Muazzam, Sylvie)

* all of the rules of monopoly are implemented
* Buying properties, railroads, and utilities    
* Paying rent to the owner of properties, railroads, and utilities if you land on them 
* Taxes have to be paid if you land on a Tax Tile 
* Mortgaging and unmortgaging properties
* Monopolies if you own all the properties of one color 
* Buying and selling houses and hotels if you have a monopoly
* Chance and Community Chest cards
* Trading money and properties/railroads/utilities between players
* Players are suspended in jail but can pay a fee to exit 
* If a player rolls doubles they can roll again
* Player removed from the game (loses) if they go bankrupt 
* Game ends when there is one player remaining
* All these functionalities exist on 4 different versions of the game 
* Switch players feature (players can choose to switch positions with any player on the board)



3. Visualization (Catherine, Ryan)
* Dynamic Board Visualization
* Interactive control panel for each player
* Dice animation
* Display for owned properties, balance, piece image
* Buttons for buying/selling real estate, Trading, Mortgaging/Unmortgaging properties, swap players positions
* Translations of all Strings between English and Spanish
* Pieces move around the board as turn is taken
* Controller to intialize backend and frontend game portions together
* Selection Screen at beginning of the game for greating game set up
* The ability to restart the game at any time


### Notes/Assumptions

Assumptions or Simplifications:

1.Configuration (Wyatt)
* For chance cards, community chest cards, tile csv, assumes each of these files are within the data directory under their own specified directory for a given game type
* Assumes allGameTypesButton.csv is within data/gamespecific directory

2. Model (Muazzam, Sylvie)
* Assume players will not buy more houses once they have one hotel
* Assume players will not attempt to get out of jail without sufficient funds


1. Visualization (Catherine, Ryan)
* Number of total tiles divisible by 4
* Players don't hit cancel when entering a name/piece choice
* Players don't hit the next turn button before executing their turn

Interesting data files:

Known Bugs:

1.Configuration (Wyatt)

2. Model (Muazzam, Sylvie)
* Small bug when a player lands on community chest or chance and a "advance to property" card is chosen. The 
popup types are switched between the chance popup type and property popup type.
* If a player has a hotel on a property, they can still click the Buy House button and nothing will happen

3. Visualization (Catherine, Ryan)
When piece is at tile right before go it moves to top left of screen

Extra credit:

1. English and Spanish options are both available
2. Cheat Keys
3. Functionality for reading zip files (for zip files that are zips of csv's), see CSVFactory
4. Ability to randomly generate random values (with certain invariants relating to logic, ex. Hotel increase must be more than house increase), see RandomRuleFactory
6. Took away functionality for buying houses/hotels in Dukeopoly, and Extremeopoly

Twists in Game Variations:
1. Took away functionality for buying houses/hotels in Dukeopoly, and Extremeopoly
2. Chance/community chest cards in Extreme only penalize the player
3. Added ability for players to swap positions in Extreme monopoly

### Impressions

Ryan, Catherine: My impression of this assignment was a relatively positive one. I thought it was very fun how we were able to choose our own game and it was left very open ended. I thought the deadlines were reasonable, and that this project was a really good experience for working as part of a large(r) team, doing individual work and then bringing it together with my teammates.

Sylvie, Muazzam: I feel like I learned so much executing a project of this size. As we added more and more features it became increasingly
clear how important design was and continued to be so that we wouldn't get lost in our own code.

Wyatt: Definitely learned a lot about working on a big project with a "big" team.  Will certainly be useful in the future.
### State Key for CSV Initialization of Tiles

Go: 0
Property: 1
Railroad: 2
Utility: 3
Tax: 4
Jail: 5
GoToJail: 6
Card (chance/community): 7
FreeParking: 8

### Cheat Keys

R: Moves player to nearest railroad

D: Decreases player wealth by 10
U: Moves player to nearest utility
V: Vacate current tile, moves player forward one tile
A: Add to player wealth by 10
L: Lap board to move player to Go , does nothing if already on Go
L (again): Lap board to move player to Go , does nothing if already on Go

### Error Testing
1. Bad language passed to CardFactory
2. Error in parsing ints from csv in CardFactory
3. Game type is missing a corresponding csv for Community cards
4. Error in parsing ints from properties file in RuleFactory
5. Bad game type being passed into RuleFactory
6. Wrong number of elements in line of csv 


