JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Driver.java \
	Game.java \
	Player.java \
	Deck.java \
	Card.java \
	MemoryDeck.java \
	MemoryCard.java \
	RandomPlayer.java \
	StratPlayer.java \
	ForgetfulPlayer.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class


run:
	java Driver
	lua main.lua
