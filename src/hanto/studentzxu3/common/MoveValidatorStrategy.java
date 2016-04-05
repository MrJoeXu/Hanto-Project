/**
 * 
 */
package hanto.studentzxu3.common;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;

/**
 * @author JoeXu
 *
 */
public interface MoveValidatorStrategy {
	/**
	 * @param from starting coordinate
	 * @param to destination coordinate
	 * @return whether valid to make the move
	 *
	 */
	public boolean canMove(HantoCoordinate from, HantoCoordinateImpl to, HantoPlayerColor color, HantoBoard board) throws HantoException;
}
