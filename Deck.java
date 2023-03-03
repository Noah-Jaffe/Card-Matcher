
import java.util.ArrayList;
import java.util.Collections;

/**
 * This is a Deck, it has 52 cards put on a 4x13 2d array of Cards representing a playing board
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public class Deck {
	/** number of matches left in the deck **/
	private int matchesLeft;
	
	/** the board of cards **/
	protected Card board[][];
	
	/**
	 * creates the shuffled deck, populates the board, sets matches left to 26
	 */
	public Deck(){
		//MUST BE 4x13 DECK
		board = new Card[4][13];
		
		//get a deck of cards
		ArrayList<Card> stack = getDeckOfCards();
		
		//shuffle deck
		Collections.shuffle(stack);
		
		//populate deck
		int i = 0;
		for (int x = 0; x < 4; x++){
			for (int y = 0; y < 13; y++){
				board[x][y] = stack.get(i);
				i++;
			}
		}
		matchesLeft = 26;
	}
	
	/**
	 * @return if there's more matches left to be found
	 */
	public boolean hasMatchesLeft() {
		return matchesLeft > 0;
	}

	/**
	 * @param row of the card
	 * @param col of the card
	 * @return the card in this position, or null if invalid position
	 */
	public Card getPos(int row, int col) {
		try{
		return board[row][col];
		}catch(Exception e){
			System.out.println("Error @ Deck.getPos(int,int): tried to access invalid position (" + row + ", " + col + ") on the board");
			return null;
		}
	}
	
	/**
	 * sets cards to matched status, decrease matches left
	 * @param choice array of int, first 2 are position one, third and fourth are position two
	 */
	public void matchCards(int[] choice) {
		board[choice[0]][choice[1]].setStatus(2); //set to status 2 = matched
		board[choice[2]][choice[3]].setStatus(2); //set to status 2 = matched
		matchesLeft--;
	}
	
	/**
	 * prints the deck
	 */
	public String toString(){
		String str = "";
		for (int x = 0; x < 4; x++){
			str = str + "\r\n";
			for (int y = 0; y < 13; y++){
				str = str + board[x][y].toString() + " ";
			}	
		}
		return str;
	}
	
	/**
	 * sets the positions given to certain status
	 * @param choice positions of cards
	 * @param status status to be set
	 */
	public void setGuessStatus(int[] choice, int status) {
		try{
			board[choice[0]][choice[1]].setStatus(status);
			board[choice[2]][choice[3]].setStatus(status);
		}catch(Exception e){
			System.out.println("Error @ Deck.setGuessStatus(int[], int): tried to access invalid position on the board");
		}
	}
	
	/**
	 * creates a card for each suit and number, stores it into an arrayList
	 * @return an ArrayList of 52 cards in order by suit then number
	 */
	private ArrayList<Card> getDeckOfCards() {
		ArrayList<Card> deckOfCards = new ArrayList<Card>(52);
		char suits[] = {'S','H','C','D'};
		char nums[] = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
		
		//add all the cards into the array list
		for (int x = 0; x < 4; x++){
			for (int y = 0; y < 13; y++){
				deckOfCards.add(new Card(suits[x], nums[y]));
			}
		}
		
		//returns 52 cards in order by suit then number
		return deckOfCards;
	}
}
