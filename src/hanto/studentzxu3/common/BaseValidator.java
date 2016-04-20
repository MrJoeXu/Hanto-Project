/**
 * 
 */
package hanto.studentzxu3.common;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;

import java.util.Queue;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author JoeXu
 *
 */
public abstract class BaseValidator implements MoveValidatorStrategy {
	protected boolean isValidMove;
	protected boolean canWalk;
	protected HantoPieceType  type;
	
	/**
	 * 
	 */
	public BaseValidator() {
		isValidMove = false;
	}
	
	public boolean canMove(HantoCoordinate from, HantoCoordinate to, HantoPlayerColor pieceColor, HantoBoard board) throws HantoException {
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
	private void checkValidMove(HantoCoordinate from, HantoCoordinate to, HantoPlayerColor pieceColor, HantoBoard board) throws HantoException {
		checkEmptyDestination(to, board);
		checkButterflyPlacedByFourthRound(pieceColor, board);
		checkDestHasAdjacent(to, board);
		if (from == null) {
			if(type == BUTTERFLY) checkButterflyNum(pieceColor, board);
			if (type == SPARROW) checkSparrowNum(pieceColor, board);
		}

		
		if (canWalk) {
			if(from == null) checkAdjacentColor(to, board, pieceColor);
			else {
				checkSourcePieceExist(from, board);
				checkSourcePieceBelongToPlayer(from, pieceColor, board);
				checkMultipleHexOpening(from, to, board);
				checkWalkOnlyOneHex(from, to);
				checkContiguity(from, to, board);
				checkPieceTypeMatch(from, to, type, board);	
				if (type == SPARROW) checkHasButterflyBeforeWalk(pieceColor, board);
			}
		} 
		else{
			checkValidMovementType(from);
		}
		
		isValidMove = true;
		
	}
	
	
	
	
	
	/**
	 * Check whether the destination of move is already occupied or not
	 * @param 
	 * 
	 * @return exception
	 */
	private void checkEmptyDestination(final HantoCoordinate dest, final HantoBoard board) throws HantoException{
		HantoCoordinateImpl to = new HantoCoordinateImpl(dest);
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
	private void checkButterflyNum(final HantoPlayerColor pieceColor, final HantoBoard board) throws HantoException{
		boolean validButterflyNum = true;
		if (pieceColor == BLUE) {
			validButterflyNum = !(board.getBlueButterflyCount() > 0);
		}
		if (pieceColor == RED) {
			validButterflyNum = !(board.getRedButterflyCount() > 0);
		}
		if (!validButterflyNum)  throw new HantoException("You can only place one butterfly!");
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
	private void checkDestHasAdjacent(final HantoCoordinate dest, final HantoBoard board) throws HantoException{
		boolean hasAdjacent = false;
		HantoCoordinateImpl to = new HantoCoordinateImpl(dest);

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
	 * Check whether Butterfly piece has been placed yet by fourth round
	 * 
	 * @return boolean 
	 */
	private void checkButterflyPlacedByFourthRound(HantoPlayerColor pieceColor, final HantoBoard board) throws HantoException{
		if (board.getNumMoves() > 6) {
			if (!board.hasButterfly(pieceColor)) {
				throw new HantoException("You have to place Butterfly by fourth round!");
			}
		}
	}
	
	
	
	private void checkSparrowNum(final HantoPlayerColor pieceColor, final HantoBoard board) throws HantoException {
		boolean validButterflyNum = true;
		if (pieceColor == BLUE) {
			validButterflyNum = !(board.getBlueSparrowCount() >= 5);
		}
		if (pieceColor == RED) {
			validButterflyNum = !(board.getRedSparrowCount() >= 5);
		}
		if (!validButterflyNum)  throw new HantoException("You can only place 6 sparrow!");
	}
	
	
	/**
	 * Check whether piece is only adjacent to piece that has same color with exception of when moving a piece
	 * @param to
	 * @param board
	 * @param color
	 * @throws HantoException
	 */
	private void checkAdjacentColor(final HantoCoordinate dest, final HantoBoard board, final HantoPlayerColor color) throws HantoException {
		boolean invalidAdjacentColor = true;
		HantoCoordinateImpl to = new HantoCoordinateImpl(dest);
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
	
	private void checkSourcePieceExist(final HantoCoordinate from, final HantoBoard board) throws HantoException {
		if (!board.isCooedinateOccupied(from)) {
			throw new HantoException("You are trying to move a piece that doesn't exsit!");
		}
	}
	
	private void checkSourcePieceBelongToPlayer(final HantoCoordinate from, final HantoPlayerColor playerColor, final HantoBoard board) throws HantoException {
		HantoPlayerColor pieceColor = board.getPiece(from).getColor();
		if (pieceColor != playerColor) {
			throw new HantoException("You cannot move other player's piece!!!");
		}
	}
	
	private void checkHasButterflyBeforeWalk(HantoPlayerColor pieceColor, final HantoBoard board) throws HantoException {
		if (!board.hasButterfly(pieceColor)) {
			throw new HantoException("You have to place butterfly piece before make a walk movement");
		}
	}
	
	private void checkMultipleHexOpening(final HantoCoordinate from, final HantoCoordinate dest, final HantoBoard board) throws HantoException {
		HantoCoordinateImpl to = new HantoCoordinateImpl(dest);
		HantoCoordinateImpl src = new HantoCoordinateImpl(from);
		int openHexCount = 1;
		Queue<HantoCoordinate> srcAdjacents = src.getAdjacent();
		Queue<HantoCoordinate> destinationAdjacents = to.getAdjacent();
		
		for (HantoCoordinate p : srcAdjacents) {
			if (destinationAdjacents.contains(p) && !board.isCooedinateOccupied(p)) openHexCount++;
		}
		
		if (openHexCount < 2) {
			throw new HantoException("You cannot Move Through Single Opening Hex");
		}
	}
	
	
	private void checkWalkOnlyOneHex(final HantoCoordinate from, final HantoCoordinate dest) throws HantoException {
		HantoCoordinateImpl to = new HantoCoordinateImpl(dest);

		HantoCoordinateImpl src = new HantoCoordinateImpl(from);
		if (src.getDistance(to) != 1) {
			throw new HantoException("You can only walk one hex during each move!!!!");
		}
	}
	

	private void checkContiguity(final HantoCoordinate from, final HantoCoordinate dest, final HantoBoard board) throws HantoException {
		HantoCoordinateImpl to = new HantoCoordinateImpl(dest);
		HantoCoordinateImpl src = new HantoCoordinateImpl(from);
		
		Queue<HantoCoordinateImpl> allHexList = board.getOccupiedCoordinatesImpl();
		allHexList.remove(src);
		allHexList.add(to);

		for (HantoCoordinate hex : allHexList) {
			HantoCoordinateImpl copyHex = new HantoCoordinateImpl(hex);
			if (!copyHex.hasAdjacencyInList(allHexList)) {
				throw new HantoException("You have to keep the contiguity of the piece!");
			}
		}
	}
	
	/**
	 * @param from
	 * @param pieceType
	 * @param board
	 * @throws HantoException
	 */
	public void checkPieceTypeMatch(final HantoCoordinate from, final HantoCoordinate to, final HantoPieceType pieceType, final HantoBoard board) throws HantoException {
		if (board.getPiece(from).getType() != pieceType) {
			throw new HantoException("You have to match the piece type of actual piece!");
		}

	}

}
