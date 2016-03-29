/**
 * 
 */
package hanto.studentzxu3.common;

import hanto.common.*;
/**
 * @author JoeXu
 * An implementation of the board to keep track of coordinates and distance
 */
public class HantoBoard {
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