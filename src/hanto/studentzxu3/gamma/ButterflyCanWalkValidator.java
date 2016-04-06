/**
 * 
 */
package hanto.studentzxu3.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.HantoPlayerColor.RED;

import java.util.Queue;
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
public class ButterflyCanWalkValidator implements MoveValidatorStrategy {
	private boolean isValidMove;
	
	/**
	 * 
	 */
	public ButterflyCanWalkValidator() {
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
	 * @param pieceColor
	 * 			color of piece
	 * @param board
	 * @throws HantoException
	 *
	 */
	private void checkValidMove(HantoCoordinate from, HantoCoordinateImpl to, HantoPlayerColor pieceColor, HantoBoard board) throws HantoException {
		checkHasAdjacent(to, board);
		checkEmptyDestination(to, board);
		checkAdjacentColor(to, board, pieceColor);
		isValidMove = true;
		
	}
	
	/**
	 * check if an coordinate has adjacent around it. 
	 * @param to
	 * @param board
	 * @param color
	 * @throws HantoException
	 */
	private void checkHasAdjacent(final HantoCoordinateImpl to, final HantoBoard board) throws HantoException{
		boolean hasAdjacent = false;
		Set<HantoCoordinate> targets = board.getOccupiedCoordinates();

		if (board.getNumMoves() == 1) {
			if (to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("You can only place your piece at (0,0) for your first move!");
			}
		}	
		else {
			for (HantoCoordinate coord : targets) {
				int distance = to.getDistance(coord);
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
	 * Check whether piece is only adjacent to piece that has same color
	 * @param to
	 * @param board
	 * @param color
	 * @throws HantoException
	 */
	private void checkAdjacentColor(final HantoCoordinateImpl to, final HantoBoard board, final HantoPlayerColor color) throws HantoException {
		boolean invalidAdjacentColor = true;
		Queue<HantoCoordinate> adjacents = to.getAdjacent();
		
		if (board.getNumMoves() > 2) {
			for (HantoCoordinate coord : adjacents) {
				if (board.isCooedinateOccupied(coord)) {
					invalidAdjacentColor = (board.getPiece(coord).getColor() != color);
				}
			}
			if (invalidAdjacentColor) {
				throw new HantoException("You have to place your piece adjacent to existing pieces with same color!");
			}
			
		}
	}
	/**
	 * Check whether the destination of move is already occupied or not
	 * @param to
	 * @param board
	 * @throws HantoException
	 */
	private void checkEmptyDestination(final HantoCoordinateImpl to, final HantoBoard board) throws HantoException{
		if (board.isCooedinateOccupied(to)) {
			throw new HantoException("Uable to place piece at this location!");
		}
	}
}
