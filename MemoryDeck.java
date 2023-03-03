
/**
 * this class extends Deck, it overrides the default constructor so that it creates 
 * a board of face down cards. the cards have no suites or numbers until set
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public class MemoryDeck extends Deck {

	/**
	 * creates an mock deck for player memory
	 */
	public MemoryDeck() {
		//MUST BE 4x13 DECK
		super.board = new Card[4][13];
		
		//set each card to empty unknowns
		for (int x = 0; x < 4; x++){
			for (int y = 0; y < 13; y++){
				board[x][y] = new Card(0);
			}
		}
	}
	
	/**
	 * sets a location to a card
	 * @param row row to be set
	 * @param col col to be set
	 * @param card the card to set in this location
	 */
	public void setPos(int row, int col, Card card) {
		try{
		  board[row][col] = card;
		}catch(Exception e){
			System.out.println("Error @ MemoryDeck.getPos(int,int,card): tried to access invalid position on the board");
		}
	}
}
