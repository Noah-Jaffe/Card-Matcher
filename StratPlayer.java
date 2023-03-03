
import java.util.Arrays;

/**
 * This class is a player that uses an optimized picking strategy:
 * it uses more memory and may take more time to find a match, 
 * but overall should take less turns to complete the game
 * 
 * @author Noah Jaffe
 * 11/09/2017
 * Noah0m0jaffe@gmail.com
 */
public class StratPlayer extends Player {
	/** number of cards this player can remember **/
	private final int MEMORY_SIZE = 52;
	
	/** use this as well as the 2d array for the board so the speed of finding a match is much faster **/
	private MemoryCard stratMem[];
	
	/** the number of cards known in stratMem **/
	private int size = 0;

	/** 
	 * creates a new player with an optimized card picking strategy
	 * @param id ID of player
	 */
	public StratPlayer(int id){
		super(id);
		stratMem = new MemoryCard[MEMORY_SIZE];
	}

	/**
	 * sets a spot in the board memory and adds the card to the memory array
	 * @param row the row to set
	 * @param col the col to set
	 * @param card the card to set
	 */
	public void addBoardMemory(int row, int col, Card card) {
		super.addBoardMemory(row, col, card);

		MemoryCard mCard = new MemoryCard(card.getStatus());
		mCard.setCard(row, col, card);
		stratMem[size] = mCard;
		size++;
	}

	/**
	 * STRATEGY 1: if any cards in memory match, use those, if not, chose the first two that are unknown
	 * @return position of two cards that match or the next two unknowns
	 */
	public int[] takeTurn() {
		int[] positions = new int[4];
		//sort known cards
		Arrays.sort(stratMem, MemoryCard.MemoryCardComparator);

		//compare each pair for a match going down the list because it is already sorted
		for (int i = 0; i < size - 1; i++){
			if (stratMem[i].matches(stratMem[i+1])){
				//get positions
				positions[0] = stratMem[i].getRow();
				positions[1] = stratMem[i].getCol();
				positions[2] = stratMem[i + 1].getRow();
				positions[3] = stratMem[i + 1].getCol();
				//update status of cards to matched
				stratMem[i].setStatus(2);
				stratMem[i+1].setStatus(2);
				size = size - 2;
				//return positions
				return positions;
			}
		}

		//if it didn't find a match,
		//get the next two available cards so that it can store more of them in memory
		positions = getNextTwoOpenCards();
		return positions;
	}

	/**
	 * gets two random unmatched cards in the deck
	 * @return the two card locations of the next unmatched card in the deck, returns null if no unmatched cards
	 */
	protected int[] getNextTwoOpenCards() {
		int[] pos = new int[4];

		//get random locations
		pos[0] = (int) ((Math.random() * 100) % 4 );
		pos[1] = (int) ((Math.random() * 100) % 13 );
		pos[2] = (int) ((Math.random() * 100) % 4 );
		pos[3] = (int) ((Math.random() * 100) % 13 );

		int x = pos[0], y = pos[1], cardsChecked = 0, found = -1;

		//loop around the whole board until it hits the next unmatched card
		while (cardsChecked < 53){
			//if found an unmatched card, save the position and start with the next card
			if (getBoardMemory(x, y).getStatus() != 2){
				if (! (found == 1 && (x == pos[0] && y == pos[1])) ){
					pos[++found] = x;
					pos[++found] = y;
					x = pos[2];
					y = pos[3];
					cardsChecked = 0;
				}
				//return the new positions if both card positions have been found
				if (found == 3){
					return pos;
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
		System.out.println("Error @ StratPlayer.getNextOpenCard(): could not find any more unmatched cards\n" + boardMemory.toString());
		System.out.println(x + " " + y + " " + pos[0] + " " + pos[1] + " " + pos[2] + " " + pos [3]);
		for (x = 0; x < 4; x++){
			for (y = 0; y < 13; y++){
				System.out.print(boardMemory.getPos(x, y).getStatus() + "  ");
			}
			System.out.println();
		}
		return pos;
	}



	/*
	 * FOR SOME REASON THIS JUST DOSENT WORK, I DO SET STATUS TO 1 WHEN I CALL BOARD MEMORY, BUT THOSE ARE NOT SAVED, ONLY WHEN I SET BOARD STATUS TO 2 DOES IT SAVE IDK, I JUST WENT
	 * FOR CHOSING RANDOM INTS BC THAT IS THE SAME THEORETICAL O(n) TIME
	 * gets the next unknown card in the deck
	 * @param i row of starting card 
	 * @param j col of starting card
	 * @return the x and y coordinates of the next unmatched card in the deck, returns null if no unmatched cards
	 *

	protected int[] getNextTwoUnknownCards(int[] pos) {
		//TO DO: FIX HOW IT ALWAYS GUESSES {0 0 0 0} IF NO MATCH AVAILABLE:
		//TO DO: MAYBE CHANGE 2ND PART TO JUST GET RANDOM INT AND FIX FOR UNKNOWN CARD
		//System.out.println("NEXT TWO: " + boardMemory.toString());
		int[] ret = new int[4];
		int x = pos[0], y = pos[1], cardsChecked = 0, found = -1;
		//loop around the whole board until it hits the next unmatched card
		while (cardsChecked < 52){
			//if found an unknown card, save the position and start with the next card
			if (getBoardMemory(x, y).getStatus() == 0){
				ret[++found] = x;
				ret[++found] = y;
				boardMemory.setGuessStatus(ret, 1);
				//System.out.println("FOUND: X: " + x + " Y: " + y);
				//x = pos[2];
				//y = pos[3];
				//cardsChecked = 0;
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
		System.out.println("Error @ StratPlayer.getNextUnknownCard(int,int): could not find any more unknown cards\n" + boardMemory.toString());
		System.out.println(x + " " + y + " " + pos[0] + " " + pos[1] + " " + pos[2] + " " + pos [3]);
		for (x = 0; x < 4; x++){
			for (y = 0; y < 13; y++){
				System.out.print(boardMemory.getPos(x, y).getStatus() + "  ");
			}
			System.out.println();
		}
		return null;
	}

	 */
}
