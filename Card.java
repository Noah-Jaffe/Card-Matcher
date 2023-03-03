
/**
 * This is a card, it has a suite {'S','H','C','D'},
 * number {'A','2','3','4','5','6','7','8','9','T','J','Q','K'},
 * and status (0 = face down, 1 = face up, 2 = matched)
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public class Card {
	/** CARDNUM OPTIONS: {'A','2','3','4','5','6','7','8','9','T','J','Q','K'} **/
	protected char cardNum;
	
	/** CARDSUITE OPTIONS: {'S','H','C','D'} **/
	protected char cardSuite;
	
	/** STATUS: 0 = face down, 1 = face up, 2 = matched **/
	protected int status;
	
	/**
	 * creates a card with default status of 0 (face down)
	 * @param suite suit of card
	 * @param num card number
	 */
	public Card(char suite, char num) {
		setSuite(suite);	
		setNumber(num);		
		setStatus(0);		
	}

	/**
	 * overloaded constructor only to be used by a subclass
	 * @param stat the status to be set
	 */
	protected Card(int stat){
		this.status = stat;
	}
	
	/**
	 * @return 'R' for Red suites 'B' for Black suites 'E' if the card has not been declared yet
	 */
	protected char getColor() {
		if (getSuite() == 'H' || getSuite() == 'D')
			return 'R';
		else if (getSuite() == 'S' || getSuite() == 'C')
			return 'B';
		return 'E';
	}

	/**
	 * checks to see if this card matches the card given,
	 * matching means same number and color
	 * @param card the card to check for matching
	 * @return if this card matches with the card given
	 */
	public boolean matches(Card card) {
		return card != null && this.getColor() == card.getColor() && this.getNumber() == card.getNumber();
	}
	/**
	 * @param num the card 'number' to set: {'A','2','3','4','5','6','7','8','9','T','J','Q','K'}
	 */
	public void setNumber(char num){
		this.cardNum = num;
	}
	
	/**
	 * @return the cards 'number': {'A','2','3','4','5','6','7','8','9','T','J','Q','K'}
	 */
	public char getNumber() {
		return cardNum;
	}
	
	/**
	 * @param suite the cards suite: {'S','H','C','D'}
	 */
	public void setSuite(char suite){
		this.cardSuite = suite;
	}
	
	/**
	 * @return the cards suite: {'S','H','C','D'}
	 */
	public char getSuite(){
		return cardSuite;
	}

	/**
	 * @return the status 0 = face down, 1 = face up, 2 = matched
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set, 0 = face down, 1 = face up, 2 = matched
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return "OO" if face down, "XX" if already matched, or the number and the suite
	 * or "ER" if there is some error
	 */
	public String toString(){
		if (getStatus() == 0)
			return "OO";
		else if (getStatus() == 1)
			return (getNumber() + "" + getSuite());
		else if (getStatus() == 2)
			return "XX";
		return "ER";
	}

}
