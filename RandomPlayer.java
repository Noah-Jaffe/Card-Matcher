
/**
 * This class is a player that uses an random picking strategy:
 * this is a terrible strategy but it completes the assignment requirements
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public class RandomPlayer extends Player {

	/**
	 * creates a new player with a strategy of picking cards at random
	 * @param playerNum name of player
	 */
	public RandomPlayer(int playerNum) {
		super(playerNum);
	}

	/**
	 * STRATEGY 0: completely random choices, for speed of program I added 
	 * that if one of the cards chosen is already matched, it returns the next
	 * card down the board that is available to be picked
	 * @return positions to be chosen
	 */
	public int[] takeTurn(){
		int pos[] = new int[4];
		pos[0] = (int) ((Math.random() * 100) % 4 );
		pos[1] = (int) ((Math.random() * 100) % 13 );
		pos[2] = (int) ((Math.random() * 100) % 4 );
		pos[3] = (int) ((Math.random() * 100) % 13 );
		
		//if a location has a matched card, get the next available card
		if (getBoardMemory(pos[0], pos[1]).getStatus() == 2 || getBoardMemory(pos[2], pos[3]).getStatus() == 2){
			return getNextTwoOpenCards(pos);
		}
		return pos;
	}

	/**
	 * gets the next unmatched card in the deck
	 * @param pos two card locations
	 * @return the x and y coordinates of the next unmatched card in the deck, returns null if no unmatched cards
	 */
	protected int[] getNextTwoOpenCards(int[] pos) {
		int[] ret = new int[4];
		int x = pos[0], y = pos[1], cardsChecked = 0, found = -1;
		
		//loop around the whole board until it hits the next unmatched card
		while (cardsChecked < 52){
			//if found an unmatched card, save the position and start with the next card
			if (getBoardMemory(x, y).getStatus() != 2){
				//if its the same card as the first one, skip it.
				if (! (found == 1 && (x == pos[0] && y == pos[1])) ){
					ret[++found] = x;
					ret[++found] = y;
					x = pos[2];
					y = pos[3];
					cardsChecked = 0;
				}
				//return the new positions if both card positions have been found
				if (found == 3){
					return ret;
				}
			}
            
			cardsChecked++;
			
			//goto next card, if it reaches an end of row, go to next row and start at col 0
			y++;
			if(y == 13){
				y = 0;
				x++;
				if (x == 4){
					x = 0;
				}
			}
		}		
		//if it reaches this point, there must be an error
		System.out.println("Error @ RandomPlayer.getNextOpenCard(int,int): could not find any more unmatched cards\n" + boardMemory.toString());
		System.out.println(x + " " + y + " " + pos[0] + " " + pos[1] + " " + pos[2] + " " + pos [3]);
		for (x = 0; x < 4; x++){
			for (y = 0; y < 13; y++){
				System.out.print(boardMemory.getPos(x, y).getStatus() + "  ");
			}
			System.out.println();
		}
		return pos;
	}
	
	
}
