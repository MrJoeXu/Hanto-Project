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
	 * @param color color for move validator
	 * @param board 
	 * @return whether valid to make the move
	 * @throws HantoException
	 *
	 */
	 boolean canMove(HantoCoordinate from, HantoCoordinate to, HantoPlayerColor color, HantoBoard board) throws HantoException;
}
