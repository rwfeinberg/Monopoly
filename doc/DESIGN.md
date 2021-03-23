# Simulation Design Final
### Names

## Team Roles and Responsibilities

 * Team Member #1: Wyatt Focht: Configuration

 * Team Member #2: Catherine Livingston: Visualization

 * Team Member #3: Ryan Feinberg: Visualization

 * Team Member #4: Muazzam Noorpuri: Model

 * Team Member #5: Sylvie Mason: Model


## Design goals

#### What Features are Easy to Add

1. Configuration

* New themes for new game types
* New language options
* New types of cards for chance and commmunity chest
* New ways to generate game rules
* New ways to generate game tiles
* New ways to generate community chest and chance rules

2. Model
* New types of tiles 
* New types of community chest and chance cards

3. Visualization
* new buttons to add game functionality
     
## High-level Design

#### Core Classes

1. Configuration

* ButtonGameTypeFactory
* CardFactory
* CardReader
* CSVFactory
* CSVLoader
* RandomRuleFactory
* RuleFactory
* RuleReader
* TileFactory
* TileReader

2. Model
* Player
* Turn 
* Board 
* Game 
* Tile and subclasses 
* DeckOfCards
* Action and subclasses

3. Visualization
* Controller
* PlayerPanel
* PanelButton and subclasses
* Trade
* ViewComponents
* PieceView
* BackgroundHandler


 
## Assumptions that Affect the Design

#### Features Affected by Assumptions

1. Configuration

* Assumes all files are csv files or zips of csv files 
* Assumes certain formats for file name capitalization
* Assumes certain formats for content of data files that are read in

2. Model
* Assume that player will not click next turn before executing their turn 
* Assume the player will not execute a trade without selecting a player to trade with 

3. Visualization

* Assumes that files are CSVs or properties depending on the functionality needed

## Significant differences from Original Plan

1. Configuration

* None (if anything, added more features than originally planned: cheat keys, languages, random rules, etc...)

2. Model
* None 

3. Visualization

* Did not need a "Card View Class"
* Separated creation of PlayerPanel into more classes

## New Features HowTo

#### Easy to Add Features

1. Configuration

* Adding a new Game Type:

    * Name the "type" of your game, this name must be in the format where the first letter is capital while all the trailing letters are lowercase.  The example we will be using wil be named Xyz.

    * Navigate to data/gamespecific/allGameTypesButtons.csv.  Add game type and desired buttons (to change functionality) to this file.  Options include BuyHouse,SellHouse,Trade,Mortgage,Unmortgage, and SwapPlayers.

    * Create a directory named xyz within the data directory.  Populate this directory with three csv's for available chance cards, available community cards, and game tiles named XyzChance.csv, XyzCommmunity.csv, and XyzTiles.csv, respectively.  

    * Create a directory named xyz within the resources directory.  Populate this directory with 6 properties files.  2 files named XyzChanceEnglish.properties and XyzChanceSpanish.properties for the messages to be displayed when landing on a chance card.  2 files named XyzCommunityEnglish.properties and XyzCommunitySpanish.properties for the messages to be displayed when landing on a community card.  1 file named XyzGameRules.properties that specifies the desired values for each of the read-in game rules for this specific variation.  

    * Add various jpegs as appropriate to supplement images for special tile types (Go, Utilities, Jail, etc..)

    * Add game type and description to both gameTypesEnglish.csv and gameTypesSpanish.csv.  Add game type and default starting background in gameType.properties.

* Adding a New Language:

    * Navigate to resources
    * Within each directory that corresponds to an existing game type, add a new file following the same format as previous files.  For instance, with game type of Xyz and new language French, file names would be as following:
        * XyzChanceFrench.properties
        * XyzCommmunityFrench.properties
    * In the outermost directory of resources, create new translation csv files matching the same format as other ones that exist.  Examples include:
        * gameTypesFrench.csv
        * jailClassMessagesFrench.csv
        * pieceOptionsFrench
    * Navigate to resources/languageOptions.csv and add French as a new type in this list

* Adding More Types of Community Chest and Chance cards
    * Locate the chance and community csv's in data/Xyz and add the names of the new cards (must use class name letter capitalization scheme)
    * In whichever game type these new cards are added, provide popup messages within the chance and community chest properties files within the resources directory (make sure to add their translations as well for each of the other languages)

2. Model

* Add a new type of Tile 
    * Create a class that extends Tile within src/ooga/model and name it the same string as the type of Tile 
    * add the necessary code to execute the action associated with the tile in the landedUpon method 
    * add the strings necessary for the popups you want shown in the setPopUpStrings method 

* Adding More Types of Community Chest and Chance cards

    * Create a new class that extends Action within src/ooga/model/actions and name it the same string as was added to the community/chance csv's within the data directory (as described above)
    * Implement the functionality of this card within performAction utilizing new helper methods or the protected helper methods that are available from actions
    * Implement the popup string to face the user by filling out the toString for the subclass

3. Visualization

* Add a new button to the player's panel
    * Create a new class that extends PanelButton within src/ooga/visualization and name it with the string 
    * add whatever functionality wanted within
    * add the button to the CSV where button types are stored for each game type
    
#### Other Dream Features 

1. Configuration

* Implementing support for more types of files to be read (XML, TXT, etc...)
* Saving and loading games

2. Model

* implementing a way for a single player to play alone (start with properties owned by the bank, add more tiles that require you to pay)

3. Visualization

* different pieces depending on the game type chosen