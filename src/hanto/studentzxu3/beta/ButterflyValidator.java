/**
 * 
 */
package hanto.studentzxu3.beta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
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
public class ButterflyValidator implements MoveValidatorStrategy {
	private boolean isValidMove;
	
	/**
	 * 
	 */
	public ButterflyValidator() {
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
		checkValidMovementType(from);
		checkHasAdjacent(to, board);
		checkEmptyDestination(to, board);
		checkButterflyMoveNum(BUTTERFLY, pieceColor, board);
		isValidMove = true;
		
	}
	
	
	/**
	 * check whether whether player is trying to place the piece or move it
	 * @param from
	 * 			the starting coordinate
	 * @return exception
	 */
	private void checkValidMovementType(final HantoCoordinate from) throws HantoException {
		if (from != null) {
			throw new HantoException("You can only place piece on board, don't move them!");
		}
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
	
	
	
	/**
	 * Check whether the destination of move is already occupied or not
	 * @param 
	 * 
	 * @return exception
	 */
	private void checkEmptyDestination(final HantoCoordinateImpl to, final HantoBoard board) throws HantoException{
		if (board.isCooedinateOccupied(to)) {
			throw new HantoException("Uable to place piece at this location!");
		}
	}
	
	
	/**
	 * Check whether this is second time tries to place Butterfly
	 * @param pieceType
	 * 
	 * @return exception
	 */
	private void checkButterflyMoveNum(final HantoPieceType pieceType, final HantoPlayerColor pieceColor, final HantoBoard board) throws HantoException{
		boolean validButterflyNum = true;
		if (pieceType == BUTTERFLY && pieceColor == BLUE) {
			validButterflyNum = !(board.getBlueButterflyCount() > 0);
		}
		if (pieceType == BUTTERFLY && pieceColor == RED) {
			validButterflyNum = !(board.getRedButterflyCount() > 0);
		}
		if (!validButterflyNum)  throw new HantoException("You can only place one butterfly!");

	}

}
