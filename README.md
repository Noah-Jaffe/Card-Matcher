# project-Jaffen1-umbc
Welcome to my project, part 1 was done in Java, and part 2 in Lua.
The main class for part 1 is "Driver.java", and "main.lua" for part 2.
 
Simple way to run parts 1 and 2 by using the makefile:
1. Compile all classes 
2. run part 1 and part 2

```
make
make run
```

Manual setup for parts 1 and 2:
1. Compile the following Java files: 
Driver.java Game.java Deck.java Card.java Player.java RandomPlayer.java StratPlayer.java ForgetfulPlayer.java MemoryDeck.java MemoryCard.java
2. To run part 1, run Driver
3. To run part 2, run main.lua

```
javac Driver.java Game.java Deck.java Card.java Player.java RandomPlayer.java StratPlayer.java ForgetfulPlayer.java MemoryDeck.java MemoryCard.java
java Driver
lua main.lua
```

To cleanup the folder run the make clean

```
make clean
```

------------------------------------------------------------
Info to know when reviewing the code:
------------------------------------------------------------

Running Driver.java creates proj_output.txt, which contains the games log of the latest run. 

**I highly recommend importing the code from part 1 into an IDE for review.**

There is full javadoc documentation for part one in the [doc](/doc) directory. I suggest starting with doc/package-summary.html to view all files in part 1. *You will need to download and open these files in a web browser to read them properly.*

To test the code to make sure part 1 and 2 actually produce the same results you can un-comment the lines following "//TESTING" in Game.java and Driver.java, the expected output will show up in the console when run. 

Information to know about hard coded variables in classes: 

- DRIVER.JAVA:

  - NUM_PLAYERS is the number of players in the game. Currently set to 2.

  - OUTPUT_FILE is the output file. Currently set to "proj_output.txt"
  
- GAME.JAVA:

  - NUM_PLAYER_TYPES is the number of unique strategies a player can use. Currently 3: random, strat, and forgetful.

- StratPlayer.java:

  - MEMORY_SIZE is the maximum number of cards the player can keep in its memory. Currently set to 52.

- ForgetfulPlayer.java:

  - MEMORY_SIZE is the number of cards this player can remember. Currently set to 10.
  
*Ties do not count as a win for any players, however, if you uncomment the else if block for "Tie:" (lines 81-102 of CardGameAnalyzer.lua) it will count ties as a win for both players.

*If you try to have more players than strategies available it will reuse a strategy in the same game. Will not be an issue for 2 player games.
