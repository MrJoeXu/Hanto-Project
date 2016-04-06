/**
 * 
 */
package hanto.studentzxu3.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.HantoPlayerColor.RED;

import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentzxu3.common.HantoBoard;
import hanto.studentzxu3.common.HantoCoordinateImpl;
import hanto.studentzxu3.common.MoveValidatorStrategy;

/**
 * @author JoeXu
 *
 */
public class SparrowCanWalkValidator implements MoveValidatorStrategy {
	private boolean isValidMove;
	
	/**
	 * 
	 */
	public SparrowCanWalkValidator() {
		isValidMove = false;
	}
	
	@Override
	public boolean canMove(HantoCoordinate from, HantoCoordinateImpl to, HantoPlayerColor pieceColor, HantoBoard board) throws HantoException {
		try {
			checkValidMove(from, to, pieceColor, board);
		} catch (HantoException e) {
			throw new HantoException(e.getMessage());
		}
		return isValidMove;
	}
	
	/**
	 * check whether the movement is valid
	 * @param from
	 * 			the starting coordinate
	 * @param to
	 * 			the destination
	 * @param pieceType
	 * 			Type of the piece
	 * @param pieceColor
	 * 			Color of the piece
	 */
	private void checkValidMove(HantoCoordinate from, HantoCoordinateImpl to, HantoPlayerColor pieceColor, HantoBoard board) throws HantoException {
		checkHasAdjacent(to, board);
		isValidMove = true;
		
	}
	
	/**
	 * check if an coordinate has adjacent around it
	 * 
	 * @param A
	 * 			coordinate of hex 
	 * @return whether hex has adjacent
	 */
	private void checkHasAdjacent(final HantoCoordinateImpl to, final HantoBoard board) throws HantoException{
		boolean hasAdjacent = false;
		Set<HantoCoordinate> targets = board.getOccupiedCoordinates();

		if (board.getNumMoves() == 1){
			if (to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("You can only place your piece at (0,0) for your first move!");
			}
		}
		else {
			for (HantoCoordinate coord : targets) {
				int distance = to.getDistance(coord);
				System.out.println(distance);
				if (distance == 1) {
					hasAdjacent = true;
				}
			}
			if (!hasAdjacent) {
				throw new HantoException("You have to place your piece adjacent to existing pieces!");
			}
		}
	}
}
