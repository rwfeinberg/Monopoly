final
====

This project implements a player for multiple related games.

Names: Catherine Livingston, Wyatt Focht, Sylvie Mason, Muazzam Khan Noorpuri, Ryan Feinberg


### Timeline

Start Date: 10/23/20

Finish Date: 11/18/20

Hours Spent: 300 (approx. 60 X person)

### Pictures/Video

#### Selection Screen:
![monopolyselectscreen](https://user-images.githubusercontent.com/66392115/116021671-a3a62c80-a616-11eb-9cbb-1e4e08ef37d8.png)

#### Gameplay:
https://user-images.githubusercontent.com/66392115/116022054-6ee6a500-a617-11eb-85c6-b7c882f9be94.mp4

### Resources Used
1. https://docs.oracle.com/javase/tutorial/reflect/index.html, reflection
2. https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html, lambdas
3. thumbnail from https://www.youtube.com/watch?v=iyHe2Gd7_jA, image for Extreme Monopoly
4. https://ashleylewis.design/portfolio/ocean-beach-opoly/, image for Beach Monopoly
5. https://www.ebay.com/i/264925791542?chn=ps&norover=1&mkevt=1&mkrid=711-117182-37290-0&mkcid=2&itemid=264925791542&targetid=935431405413&device=c&mktype=pla&googleloc=9009748&poi=&campaignid=10829254281&mkgroupid=106351005733&rlsatarget=pla-935431405413&abcId=9300396&merchantid=113370070&gclid=Cj0KCQiAqdP9BRDVARIsAGSZ8AmsJ0i651W9XmRtY3Iw0oGBe0uYMnJnI2wZpo_WmVf_2iVQIna0rxcaAh4eEALw_wcB, image for Duke Monopoly
6. https://www.pinterest.com/pin/351914158386339428/, chance card
7. https://monopoly.fandom.com/wiki/Community_Chest, community chest card

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

1. English and Spanish options are both available
2. Cheat Keys
3. Functionality for reading zip files (for zip files that are zips of csv's), see CSVFactory
4. Ability to randomly generate random values (with certain invariants relating to logic, ex. Hotel increase must be more than house increase), see RandomRuleFactory
6. Took away functionality for buying houses/hotels in Dukeopoly, and Extremeopoly

Twists in Game Variations:
1. Took away functionality for buying houses/hotels in Dukeopoly, and Extremeopoly
2. Chance/community chest cards in Extreme only penalize the player
3. Added ability for players to swap positions in Extreme monopoly

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
