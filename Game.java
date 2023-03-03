
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Game simulates a card matching game, it stores the types of players in an ArrayList
 * see JavaDoc of playGame() for how game is simulated
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public class Game {
	/**	the ArrayList of players, you may store any class that inherits from Player here (more Players with custom strategies) **/
	private ArrayList<Player> playerList;

	/**	the main game board **/
	private Deck deck;

	/**	the output file name **/
	private String outputFile;

	/**	the number of players in the game **/
	private int numPlayers;

	/**	one instance of {@link BufferedWriter} that writes to the output file -  to greatly improve runtime **/
	private BufferedWriter buffWrite;

	/** the number of player types there are. Currently 3: random, forgetful, and strat */
	private int NUM_PLAYER_TYPES = 3;
	
	
	//TESTING - do not uncomment this block
	/* array to save stats to be tested by part2
	 * The number of matches found by player 1 and player 2
	 * The number of guesses per game
	 * The longest streak any player went on, that is consecutive correct guesses in a single turn
	 * The player that won
	 * {numGuesses, biggestStreak, winner1, winner2/-1, matchesP1, matchesP2}
	 **/
	//TESTING
	//int[] testArr = {0,0,0,0,0,0};
    
	/**
	 * creates the players of the game and the deck to be played with
	 * first @param players will be used to play the game
	 * @param outFile name of output file to write to
	 * @param players number of players, must be greater than 1
	 */
	public Game(String outFile, int players) {
		//setup output file and writer
		outputFile = outFile;
		try {
			buffWrite = new BufferedWriter(new FileWriter(outputFile, true)); 
		}catch(Exception e){
			System.out.println("FATAL ERROR @ Game.Game(String, int): failed to create BufferedWriter");
		}

		//create players with sequential names 
		playerList = new ArrayList<Player>();
		numPlayers = players;
		int playerNum = 1;
		
		//create a list of available strats so we dont use the same strat twice in one game
		ArrayList<Integer> availableStrats = new ArrayList<Integer>();
		for (int i = 0; i < NUM_PLAYER_TYPES; i++){
			availableStrats.add(i);
		}

		//add the players
		for (int i = 0; i < numPlayers; i++){
			int rand = availableStrats.get((int) (Math.random() * 100) % availableStrats.size());
			if (availableStrats.remove(new Integer(rand)) == false){
				System.out.println("Used up all available player strategies for this game. Using duplicate strategy.");
				rand = (int) (Math.random() * 100) % NUM_PLAYER_TYPES;
			}
			switch(rand){
			case 1:
				playerList.add(new ForgetfulPlayer(playerNum++));
				break;
			case 2: 
				playerList.add(new StratPlayer(playerNum++));
				break;
			case 0:
			default:
				playerList.add(new RandomPlayer(playerNum++));
				break;
			}
		}
		//create deck
		deck = new Deck();
	}
	
	/**
	 * while the deck still has matches in it, keep letting players take their turn,
	 * if a player makes a match then he gets to go again.
	 */
	public void playGame() {
		printToFile("START GAME:\r\n");
		int playerTurn = 0;
		while (deck.hasMatchesLeft()){
			boolean goAgain = false;
			int choice[];
			
			//TESTING
			//int streak = 0;

			do{
				//get acceptable choice of cards
				do{
					choice = playerList.get(playerTurn).takeTurn();
				}while(isInvalidChoice(choice));

				//TESTING
				//testArr[0]++;

				printToFile("Player" + playerList.get(playerTurn).getPlayerID() + "'s turn.\r\n");

				//flip guess up (1 for up) and print to file
				deck.setGuessStatus(choice, 1);									
				printToFile("BOARD AFTER GUESS:" + deck.toString() + "\r\n");

				//if it is a match, let them go again, notify players of match, 
				//print to file what was matched, set cards in deck to matched status
				if (deck.getPos(choice[0],choice[1]).matches(deck.getPos(choice[2],choice[3]))){
					printToFile("Congrats! Player" + playerList.get(playerTurn).getPlayerID() + " matched the cards " + 
							deck.getPos(choice[0],choice[1]).toString() + " and " + 
							deck.getPos(choice[2],choice[3]).toString() + "!\r\n");

					deck.matchCards(choice);

					for (int i = 0; i < numPlayers; i++){
						playerList.get(i).addBoardMemory(choice[0], choice[1], deck.getPos(choice[0],choice[1]));
						playerList.get(i).addBoardMemory(choice[2],choice[3], deck.getPos(choice[2],choice[3]));
					}

					playerList.get(playerTurn).gotMatch(choice);

					goAgain = true;

					//TESTING
					/*
					testArr[4 + playerTurn]++;
					streak++;
					if (streak > testArr[1])
						testArr[1]= streak;
				    */ //END TESTING BLOCK
					
				}else{
					//if it wasnt a match, show players the face up cards then turn the cards face down
					for (int i = 0; i < numPlayers; i++){
						playerList.get(i).addBoardMemory(choice[0], choice[1], deck.getPos(choice[0],choice[1]));
						playerList.get(i).addBoardMemory(choice[2],choice[3], deck.getPos(choice[2],choice[3]));
					}

					deck.setGuessStatus(choice, 0);		//0 for flip down
					goAgain = false;

					//TESTING
					//streak = 0;
				}

			}while(goAgain && deck.hasMatchesLeft());

			playerTurn = (playerTurn + 1 ) % numPlayers; 	//mod numPlayers to wrap around
		}

		printToFile(getWinner());
		try {
			buffWrite.close();
		} catch (IOException e) {
			System.out.println("ERROR @ Game.playGame(): failed to close buffered writer");
		}
	}

	/**
	 * @param choice the positions of the two cards
	 * @return if the chosen positions have been matched already or if they are the same positions
	 */
	private boolean isInvalidChoice(int[] choice) {
		boolean isMatchedPos = deck.getPos(choice[0],choice[1]).getStatus() == 2 || deck.getPos(choice[2],choice[3]).getStatus() == 2;
		boolean isSamePos = choice[0] == choice[2] && choice[1] == choice[3];
		return isMatchedPos || isSamePos;
	}

	/**
	 * appends text to file
	 * @param str the text to append
	 */
	private void printToFile(String str) {
		try{
			buffWrite.write(str);
		}
		catch(Exception e)
		{
			System.out.println("ERROR @ Game.printToFile(String): failed to write string to output file");
		}
	}

	/**
	 * gets the win string
	 * @return "GAME OVER\r\nPlayerX won the game with N matches!" 
	 * 		or "GAME OVER:\r\nPlayerX and PlayerX... tied the game with N matches!"
	 */
	private String getWinner() {
		String str = "";

		//sort playerList by number of matches
		Collections.sort(playerList, WinnerComparator);

		//count number of players with the winning number of matches
		int winners = 0;
		for (int i = 0; i < numPlayers; i++){
			if (playerList.get(i).getMatches() == playerList.get(0).getMatches()){
				winners++;
			}
		}

		//if more than one winner then print who tied and how many matches
		//else if only one winner then print the winner
		//else if no winner print error
		if (winners > 1){
			//TESTING
			//testArr[2] = 1; testArr[3] = 1;
			
			str = str + "TIE: ";

			for (int i = 0; i < winners; i ++){
				//add the Player<ID>
				str = str + "Player" + playerList.get(i).getPlayerID();

				//add " and " if its not the last winner (for grammar) 
				str = str + ((i < winners - 1) ? " and " : "");
			}

			str = str + " tied the game with " + playerList.get(0).getMatches() + " matches!"; 

		}else if (winners == 1){
			//TESTING
			//testArr[1 + playerList.get(0).getPlayerID()] = 1;
			
			str = str + "WINNER: " + "Player" + playerList.get(0).getPlayerID() +  " won the game with " + playerList.get(0).getMatches() + " matches!";

		}else {

			str = str + "ERROR IN CALCULATING WINNER";
		}

		str = str + "\r\n";
		return str;
	}

	/**
	 * comparator class to compare player matches
	 */
	public static Comparator<Player> WinnerComparator = new Comparator<Player>() {

		/** compare two players by number of matches in DESCENDING order */
		public int compare(Player p1, Player p2) {
			//Descending order
			return p2.getMatches() - p1.getMatches();

			//Ascending order
			//return p1.getMatches() - p2.getMatches();
		}
	};
	
	
	/**
	 * returns data from this game so it can be tested
	 * @return
	 */
	//TESTING
	/*
	public int[] getTestArr(){
		return testArr;
	}
	*/
	
}
