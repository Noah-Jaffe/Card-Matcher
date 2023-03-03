
import java.util.Comparator;

/**
 * This class extends Card, it allows you to set a location specific to this card
 * rather than it just being a card in a location, so that you can pull it out and 
 * do what you want to it without forgetting its location on the playing board
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public class MemoryCard extends Card {
	/** the row of this card on the playing board */
	private int row;
	
	/** the col of this card on the playing board */
	private int col;
	
	/**
	 * creates a card just with a status, by calling its super(). No name or suite is to be set.
	 * @param status status of the card
	 */
	public MemoryCard(int status){
		super(status);
	}
	
	/**
	 * sets this cards info with a face up status
	 * @param row row to be set
	 * @param col col to be set
	 * @param card card to copy data from
	 */
	public void setCard(int row, int col, Card card){
		super.setSuite(card.getSuite());
		super.setNumber(card.getNumber());
		super.setStatus(1);
		this.setRow(row);
		this.setCol(col);
	}
	
	/**
	 * checks to see if this card matches the card given,
	 * matching means same number and color, and card is known
	 * @param card the card to check for matching
	 * @return if this card matches with the card given and if card is known
	 */
	public boolean matches(Card card) {
		return card != null && this.getColor() == card.getColor() && this.getNumber() == card.getNumber() && getStatus() == 1;
	}
	
	/**
	 * @return the row on the board that this card came from
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the col on the board that this card came from
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * comparator class to compare cards
	 */
	public static Comparator<MemoryCard> MemoryCardComparator = new Comparator<MemoryCard>() {
		
		/**
		 * sorts in ASCENDING order based off the Cards' toString
		 */
		public int compare(MemoryCard c1, MemoryCard c2) {
			if (c1 == null || c2 == null){
				return 0;
			}
			
		   String card1 = c1.toString();
		   String card2= c2.toString();

		   //ascending order
		   return card1.compareTo(card2);

		   //descending order
		   //return card2.compareTo(card1);
	    }
	};
}
