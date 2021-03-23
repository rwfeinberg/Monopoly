## Description

The price was showing up as 0 and never set from the CSV file for railroads. 

## Expected Behavior

The correct price should display when the player lands on them.

## Current Behavior

The popup asks if you want to buy a railroad for price 0. I had written code to fix this before the deadline, but it was overridden in a merge.

## Steps to Reproduce

Provide detailed steps for reproducing the issue.

 1. create an instance of RuleFactory in Railroad class 
 2. create a method that sets the game type in Railroad
 3. create a method initialize in Railroad that creates a RuleFactory of gameType 
 4. assign the correct value to price in initialize method 
 5. create method initialize in RuleFactory that is called in the constructor and calls initialize on the specific tile. 

## Failure Logs

Include any relevant print statements or stack traces.

## Hypothesis for Fixing the Bug

Describe the test you think will verify this bug and the code fix you believe will solve this issue.

I am testing the initialize method of the Railroad class to ensure that the prices are set.