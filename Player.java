
/**
 * This class is an abstract class for the players so that they can all be stored in one arrayList
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public abstract class Player{
	
	/** the board memory of player **/
	protected MemoryDeck boardMemory = null;
	
	/** the number of matches this player has **/
	protected int matches = 0;
	
	/** player ID **/
	private int playerID;
	
	/**
	 * creates Player object that has a board memory, sets matches to 0
	 * @param id ID of player
	 */
	public Player(int id) {
		setPlayerID(id);
		boardMemory = new MemoryDeck();
		matches = 0;
	}
	
	/**
	 * @return an int array of 4 integers representing the x and y coordinates of the two cards to be picked. 
	 */
	public abstract int[] takeTurn();
	
	/**
	 * @param row the row to get
	 * @param col the col to get
	 * @return a card at a specific point in memory
	 */
	public Card getBoardMemory(int row, int col) {
		return boardMemory.getPos(row, col);
	}
	
	/**
	 * sets a spot in the board memory
	 * @param row the row to set
	 * @param col the col to set
	 * @param card the card to 
	 */
	public void addBoardMemory(int row, int col, Card card) {
		this.boardMemory.setPos(row, col, card);
	}
	
	/**
	 * @return the number of matches this player has
	 */
	public int getMatches() {
		return matches;
	}
	
	/**
	 * increases matches got by 1, sets the status to matched in the memory board
	 * @param choice the positions of the cards
	 */
	public void gotMatch(int[] choice) {
		this.boardMemory.setGuessStatus(choice, 2);
		this.boardMemory.setGuessStatus(choice, 2);
		this.matches++;
	}
	
	/**
	 * @return the name
	 */
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * @param id the player ID to set
	 */
	public void setPlayerID(int id) {
		this.playerID = id;
	}

	/**
	 * returns the players view of the cards, OO for unknown,
	 * XX for matched, or the letter and suit of the card if known
	 */
	public String toString(){
		String str = "";
		for (int x = 0; x < 4; x++){
			str = str + "\r\n";
			for (int y = 0; y < 13; y++){
				if(getBoardMemory(x, y) != null)
					str = str + boardMemory.getPos(x, y).toString() + " ";
			}	
		}
		return str;
	}
}