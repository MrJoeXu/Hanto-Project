/**
 * 
 */
package hanto.studentzxu3.common;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.*;

import java.util.Hashtable;
import java.util.LinkedList;
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
	 * @throws HantoException
	 *             if there are any problems in adding the piece (such as place a piece at an occupied location)
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
	 * check if an coordinate has adjacent around it
	 * 
	 * @param A
	 * 			coordinate of hex 
	 * @return whether hex has adjacent
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
				Queue<HantoCoordinate> adjacents = getAdjacent(butterflyCoord);
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
	 * get a list of coordinate that adjacent to startCoor
	 * @param startCoor
	 * 		the origin coordinate
	 * @return string 
	 */
	private Queue<HantoCoordinate> getAdjacent(HantoCoordinate startCoor) {
		Queue<HantoCoordinate> adjacents = new LinkedList<HantoCoordinate>();
		int x = startCoor.getX();
		int y = startCoor.getY();
		
		adjacents.add(new HantoCoordinateImpl(x,y+1));
		adjacents.add(new HantoCoordinateImpl(x,y-1));
		adjacents.add(new HantoCoordinateImpl(x+1,y));
		adjacents.add(new HantoCoordinateImpl(x-1,y));
		adjacents.add(new HantoCoordinateImpl(x-1,y+1));
		adjacents.add(new HantoCoordinateImpl(x+1,y-1));
		
		return adjacents;
	}
	
	/**
	 * Calculate the distance between two coordinates
	 * 
	 * @param from
	 * @param to
	 */
	private static int calculateDistance(HantoCoordinate from, HantoCoordinate to) {
		int dx = Math.abs(to.getX() - from.getX());
		int dy = Math.abs(to.getY() - from.getY());
		int distance = (dx + dy + Math.abs(dx - dy))/2;
		
		return distance;
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