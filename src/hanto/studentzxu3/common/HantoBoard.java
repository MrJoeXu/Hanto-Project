/**
 * 
 */
package hanto.studentzxu3.common;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.*;

import java.util.Hashtable;
import java.util.Set;

import hanto.common.*;

/**
 * @author JoeXu
 * An implementation of the board to keep track of piece
 */
public class HantoBoard {
	
	
	private Hashtable<HantoCoordinate, HantoPiece> board = new Hashtable<HantoCoordinate, HantoPiece>();
	private Integer blueButterflyCount = 0;
	private Integer redButterflyCount = 0;
	
	
	/**
	 * Add piece to board. Map the coordinate with placed piece
	 * 
	 * @param HantoCoordinate where
	 * @param HantoPiece what
	 */
	public void addNewPiece(HantoCoordinate where, HantoPiece what) throws HantoException{

		
		HantoCoordinateImpl copyWhere = new HantoCoordinateImpl(where);
		if (board.containsKey(copyWhere)) {
			throw new HantoException("Uable to place piece at this location!");
		}
		
		if (what.getType() != BUTTERFLY && what.getType() != SPARROW) {
			throw new HantoException("You can only place Butterfly or Sparrow!");
		}

		updateButterflyCount(what);	
		if (blueButterflyCount > 1 || redButterflyCount > 1) {
			throw new HantoException("You can only place one butterfly!");
		}
		
		board.put(copyWhere, what);
		
	}


	
	/**
	 * check if two hex are adjacent to each other
	 * 
	 * @param HantoCoordinate coordinate of hex A
	 * @param HantoCoordintae coordinate of hex B
	 */
	public boolean hasAdjacent(HantoCoordinate A) {
		Set<HantoCoordinate> keys = board.keySet();
		for (HantoCoordinate key : keys) {
			int distance = calculateDistance(A, key);
			if (distance == 1) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * check if player placed has already placed Butterfly
	 * 
	 * @param HantoPlayerColor pieceColor
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
	 * get the coordinate of piece on the board
	 *
	 * @param HantoCoordinate where
	 */
	public HantoPiece getPiece(HantoCoordinate where) {
		HantoCoordinateImpl copyWhere = new HantoCoordinateImpl(where);
		return board.get(copyWhere);
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
	 * Calculate the distance between two coordinates
	 * 
	 * @param HantoCoordinate coordinate of source
	 * @param HantoCoordintae coordinate of destination
	 */
	private static int calculateDistance(HantoCoordinate from, HantoCoordinate to) {
		int dx = Math.abs(to.getX() - from.getX());
		int dy = Math.abs(to.getY() - from.getY());
		int distance = (dx + dy + Math.abs(dx - dy))/2;
		
		return distance;
	}
	
	/**
	 * Update the number of butterfly piece
	 * @param what
	 */
	private void updateButterflyCount(HantoPiece checkPiece) {
		if (checkPiece.getType() == HantoPieceType.BUTTERFLY) {
			if(checkPiece.getColor() == BLUE) {
				blueButterflyCount++;
			}
			else { redButterflyCount++; }
		}
	}
	
	
}