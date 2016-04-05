/**
 * 
 */
package hanto.studentzxu3.common;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.*;

import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import hanto.common.*;

/**
 * @author JoeXu
 * An implementation of the board to keep track of piece
 */
public class HantoBoard {
	
	
	private Map<HantoCoordinate, HantoPiece> board = new Hashtable<HantoCoordinate, HantoPiece>();
	private Integer blueButterflyCount = 0;
	private Integer redButterflyCount = 0;
	
	
	/**
	 * Add piece to board. Map the coordinate with placed piece
	 * 
	 * @param where
	 * 		 	The destination coordinate of new piece
	 * @param what
	 * 			The type of new piece 
	 */
	public void addNewPiece(HantoCoordinate where, HantoPiece what) {
		HantoCoordinateImpl copyWhere = new HantoCoordinateImpl(where);
		updateButterflyCount(what);	
		board.put(copyWhere, what);
	}


	

	
	
	
	/**
	 * check if player placed has already placed Butterfly
	 * 
	 * @param pieceColor
	 * 			the color of player
	 * 
	 * @return whether the player had placed Butterfly already
	 * 
	 */
	public boolean hasButterfly(HantoPlayerColor pieceColor) {
		if (pieceColor == BLUE) {
			return (blueButterflyCount == 0) ? false : true;
		}
		else {
			return (redButterflyCount == 0) ? false : true;
		}
	}
	
	
	
	/**
	 * @return number of blue butterfly on the board
	 * 
	 */
	public Integer getBlueButterflyCount() {
		return blueButterflyCount;
	}

	
	/**
	 * @return number of red butterfly on the board
	 * 
	 */
	public Integer getRedButterflyCount() {
		return redButterflyCount;
	}


	/**
	 * get the coordinate of piece on the board
	 *
	 * @param where
	 * 			coordinate of target piece
	 * @return the requested piece
	 */
	public HantoPiece getPiece(HantoCoordinate where) {
		HantoCoordinateImpl copyWhere = new HantoCoordinateImpl(where);
		return board.get(copyWhere);
	}
	
	
	/**
	 * Get the set of coordinate that already been occupied on board
	 *
	 * @return Set of HantoCoordinate
	 */
	public Set<HantoCoordinate> getOccupiedCoordinate() {
		return board.keySet();
	}
	
	public boolean isCooedinateOccupied(HantoCoordinate where) {
		HantoCoordinateImpl copyWhere = new HantoCoordinateImpl(where);
		return board.containsKey(copyWhere);
	}
	
	/**
	 * get the coordinate of piece on the board
	 *
	 * @param playerColor
	 * 			determine which side to check
	 * @return whether the butterfly is surrounded
	 */
	public boolean butterflyIsSurrounded(HantoPlayerColor playerColor) {
		Set<HantoCoordinate> keys = board.keySet();
		for (HantoCoordinate key : keys) {
			if (board.get(key).getType() == BUTTERFLY && board.get(key).getColor() == playerColor) {
				HantoCoordinate butterflyCoord= new HantoCoordinateImpl(key.getX(), key.getY());
				Queue<HantoCoordinate> adjacents = ((HantoCoordinateImpl) butterflyCoord).getAdjacent();
				for (HantoCoordinate a : adjacents) {
					if(!board.containsKey(a)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	

	
	
	/**
	 * print board
	 *
	 * @return string 
	 */
	public String printBoard() {
		String boardString = "";
		for (HantoCoordinate key : board.keySet()) {
			boardString += "(" + key.getX() + "," + key.getY() + ") " + board.get(key).getColor() + " "
					+ board.get(key).getType().getPrintableName() + "\n";
		}
		return boardString;
	}
	

	

	
	/**
	 * Update the number of butterfly piece
	 * @param checkPiece
	 */
	private void updateButterflyCount(HantoPiece checkPiece) {
		if (checkPiece.getType() == HantoPieceType.BUTTERFLY) {
			if(checkPiece.getColor() == BLUE) {
				blueButterflyCount++;
			} 
			else {
				redButterflyCount++; 
			}
		}
	}
	
	
}