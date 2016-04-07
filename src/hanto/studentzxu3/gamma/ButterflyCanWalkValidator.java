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
		if (from == null) {
			checkAdjacentColor(to, board, pieceColor);
			checkButterflyMoveNum(BUTTERFLY, pieceColor, board);

		}
		else {
			checkSourcePieceExist(from, board);
			checkSourcePieceBelongToPlayer(from, pieceColor, board);
			checkMultipleHexOpening(from, to, board);
			checkWalkOnlyOneHex(from, to);
			checkContiguity(from, to, board);
		}

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
	
	private void checkMultipleHexOpening(final HantoCoordinate from, final HantoCoordinateImpl to, final HantoBoard board) throws HantoException {
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
	
	private void checkWalkOnlyOneHex(final HantoCoordinate from, final HantoCoordinateImpl to) throws HantoException {
		HantoCoordinateImpl src = new HantoCoordinateImpl(from);
		if (src.getDistance(to) != 1) {
			throw new HantoException("You can only walk one hex during each move!!!!");
		}
	}

	private void checkContiguity(final HantoCoordinate from, final HantoCoordinateImpl to, final HantoBoard board) throws HantoException {
		Set<HantoCoordinate> allHexList = board.getOccupiedCoordinates();
		//HantoCoordinate toTemp = new HantoCoordinate(to.getX(), to.getY())
		allHexList.remove(from);
		//allHexList.add(toTemp);

		for (HantoCoordinate hex : allHexList) {
			System.out.println("hex != from");
			HantoCoordinateImpl copyHex = new HantoCoordinateImpl(hex);
			if (!copyHex.hasAdjacencyInList(allHexList) && copyHex != from) {
				System.out.println("Hex not have AdjacencyInList");
				throw new HantoException("You have to keep the contiguity of the piece!");
			}
		}
		System.out.println("\n");
	}
}
