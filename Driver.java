
import java.io.PrintWriter;

/**
 * This program creates a card matching memory game and runs it 1-40 times
 * logging the game in an output file
 * Hardcoded filename and number of players.
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public class Driver {
	/** The output file **/
	private static String OUTPUT_FILE = "proj_output.txt";

	/** The number of players in the game, make sure the Game constructor creates at least this many players **/
	private static int NUM_PLAYERS = 2;

	/**
	  create output file
	  run the game a random number of times
	  @param args command line arguments
	 */
	public static final void main(String[] args) {
		System.out.println("Welcome to Noah Jaffe's semester project for CMSC 331.");

		//create or clear output file
		setUpOutputFile();

		//get random number of times to simulate game (1 thru 40)
		int randomNum = (int)(Math.random() * 100) % 40 + 1;

		//TESTING
		//int[][] testArr = new int[randomNum][6];

		//run games
		for (int i = 0; i < randomNum; i++){
			//create new game and run game
			Game matchGame = new Game(OUTPUT_FILE, NUM_PLAYERS);
			matchGame.playGame();

			//TESTING
			//testArr[i] = matchGame.getTestArr();
		}

		//TESTING - do not uncomment this block
		/*
		 * The total number of games played
		 * The average number of matches found by player 1 and player 2
		 * The game that took the most guesses (just the number of guesses is fine)
		 * The game that took the least guesses (just the number of guesses is fine)
		 * The average number of guesses per game
		 * The longest streak any player went on, that is consecutive correct guesses in a single turn
		 * The player that won the most
		 * {numGuesses, biggestStreak, winner1, winner2/-1, matchesP1, matchesP2}
		 */

		//START TESTING OUTPUT
		/*
		System.out.println("GAMES PLAYED: " + randomNum);
		double totMatch1 = 0;double totMatch2 = 0;
		for(int i = 0; i<randomNum; i++){
			totMatch1 += testArr[i][4];
			totMatch2 += testArr[i][5];
		}
		System.out.println("Player1 HAD AN AVERAGE OF " + (totMatch1/(double) randomNum) + " MATCHES!");
		System.out.println("Player2 HAD AN AVERAGE OF " + (totMatch2/(double) randomNum) + " MATCHES!");
		int mostGuessIndex = 0;
		int leastGuessIndex = 0;
		double totalGuesses = 0;int longestStreakIndex = 0;
		int[] wins = new int[2];
		for(int i = 0; i<randomNum; i++){
			totalGuesses += testArr[i][0];
			if (testArr[i][1] > longestStreakIndex)
				longestStreakIndex = i;
			if (testArr[i][0] > testArr[mostGuessIndex][0])
				mostGuessIndex = i;
			if (testArr[i][0] < testArr[leastGuessIndex][0])
				leastGuessIndex = i;
			wins[0] += testArr[i][2];
			wins[1] += testArr[i][3];

		}
		System.out.println("MOST GUESSES IN GAME# " + (mostGuessIndex+1) + " WITH " + testArr[mostGuessIndex][0] + " GUESSES!");
		System.out.println("LEAST GUESSES IN GAME# " + (leastGuessIndex+1) + " WITH " + testArr[leastGuessIndex][0] + " GUESSES!");
		System.out.println("AVERAGE GUESSES PER GAME: " + (totalGuesses / (double) randomNum));
		System.out.println("LONGEST STREAK IN GAME# " + (longestStreakIndex+1)+ " WITH " + testArr[longestStreakIndex][1] + " MATCHES IN A ROW!");
		if (wins[0] > wins[1])
			System.out.print("Player1 WON THE MOST GAMES WITH " + wins[0] + " WINS");
		else if (wins[1] > wins[0])
			System.out.print("Player2 WON THE MOST GAMES WITH " + wins[1] + " WINS");
		else if (wins[0] == wins[1])
			System.out.println("Player1 AND Player2 TIED WITH " + wins[0] + " WINS");
		 */ //END TESTING OUTPUT

		System.out.println("Finished card matching simulator. Proceed to part 2 'main.lua'. ");
	}

	/**
	 * creates and clears the output file
	 */
	private static void setUpOutputFile() {
		try
		{
			PrintWriter writer = new PrintWriter(OUTPUT_FILE);
			writer.print("");
			writer.close();

			//successful output file setup prints the file name
			System.out.println("The output file is: " + OUTPUT_FILE);
		}
		catch(Exception e)
		{
			System.out.println("ERROR @ Driver.setUpOutputFile(String): failed to create or clear output file");
		}
	}

}
