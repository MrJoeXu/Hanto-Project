/**
 * 
 */
package hanto.studentzxu3.common;

import java.util.Hashtable;
import hanto.common.*;

/**
 * @author JoeXu
 * An implementation of the board to keep track of coordinates and distance
 */
public class HantoBoard {
	
	
	private Hashtable<HantoCoordinate, HantoPiece> board = new Hashtable<HantoCoordinate, HantoPiece>();
	
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
		board.put(copyWhere, what);
	}
	
	/**
	 * check if two hex are adjacent to each other
	 * 
	 * @param HantoCoordinate coordinate of hex A
	 * @param HantoCoordintae coordinate of hex B
	 */
	public boolean isAdjacent(HantoCoordinate A, HantoCoordinate B) {
		int distance = calculateDistance(A, B);
		if (distance != 1) {
			return false;
		}
		return true;
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
}