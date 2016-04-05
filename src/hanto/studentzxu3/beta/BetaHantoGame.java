/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studentzxu3.beta;

import hanto.common.*;
import hanto.studentzxu3.common.HantoBoard;
import hanto.studentzxu3.common.HantoCoordinateImpl;
import hanto.studentzxu3.common.HantoPieceImpl;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;

import java.util.Set;

/**
 *  The implementation of Beta Hanto.
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private final HantoPlayerColor colorFirstMoves;
	private HantoBoard board;
	private Integer numMoves;
	private boolean gameOver;
	
	
	/**
	 * Default constructor
	 * @param movesFirst color of the player who moves first.
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst)
	{
		colorFirstMoves =  movesFirst;
		board = new HantoBoard();
		numMoves = 1;
		gameOver = false;
	}

	
	
	/**
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		final HantoCoordinateImpl destination = new HantoCoordinateImpl(to);
		HantoPlayerColor pieceColor = updatePieceColor();
		
		if (gameOver) {
			throw new HantoException("Game is over. You can not make a move");
		}
		 
		checkValidMove(from, destination, pieceType, pieceColor);
		
		HantoPiece newPiece = new HantoPieceImpl(pieceColor, pieceType);
		board.addNewPiece(destination, newPiece);
		numMoves++;
		
		return evaluateGameState();
	}

	
	
	
	/**
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		HantoPiece targetPiece = board.getPiece(where);
		return targetPiece;
	}

	/**
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		return board.printBoard();
	}
	
	private MoveResult evaluateGameState () {
		if (board.butterflyIsSurrounded(BLUE) && board.butterflyIsSurrounded(RED)) {
			gameOver = true;
			return DRAW;
		}
		else if (board.butterflyIsSurrounded(BLUE)) {
			gameOver = true;
			return RED_WINS;
		}
		else if (board.butterflyIsSurrounded(RED)) {
			gameOver = true;
			return BLUE_WINS;
		}
		else if (numMoves > 12) {
			gameOver = true;
			return DRAW;
		}
		
		return OK;
		
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
	private void checkValidMove(final HantoCoordinate from, final HantoCoordinateImpl to, final HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException {
		checkValidMovementType(from);
		checkHasAdjacent(to);
		checkButterflyMovesByFourthRound(pieceType, pieceColor);
		checkEmptyDestination(to);
		checkPieceType(pieceType);
		checkButterflyMoveNum(pieceType, pieceColor);
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
	private void checkHasAdjacent(final HantoCoordinateImpl to) throws HantoException{
		boolean hasAdjacent = false;
		Set<HantoCoordinate> targets = board.getOccupiedCoordinate();
		if (numMoves == 1){
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
	private void checkButterflyMovesByFourthRound(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException{
		if (numMoves > 6 && pieceType != BUTTERFLY) {
			if (!board.hasButterfly(pieceColor)) {
				throw new HantoException("You have to place Butterfly by fourth round!");
			}
		}
	}
	
	
	/**
	 * Check whether the destination of move is already occupied or not
	 * @param 
	 * 
	 * @return exception
	 */
	private void checkEmptyDestination(final HantoCoordinateImpl to) throws HantoException{
		if (board.isCooedinateOccupied(to)) {
			throw new HantoException("Uable to place piece at this location!");
		}
	}
	
	/**
	 * Check whether the type of piece is valid in this move
	 * @param pieceType
	 * 
	 * @return exception
	 */
	private void checkPieceType(final HantoPieceType pieceType) throws HantoException{
		if (pieceType != BUTTERFLY && pieceType != SPARROW) {
			throw new HantoException("You can only place Butterfly or Sparrow!");
		}
	}
	
	
	/**
	 * Check whether the type of piece is valid in this move
	 * @param pieceType
	 * 
	 * @return exception
	 */
	private void checkButterflyMoveNum(final HantoPieceType pieceType, final HantoPlayerColor pieceColor) throws HantoException{
		boolean validButterflyNum = true;
		if (pieceType == BUTTERFLY && pieceColor == BLUE) {
			validButterflyNum = !(board.getBlueButterflyCount() > 0);
		}
		if (pieceType == BUTTERFLY && pieceColor == RED) {
			validButterflyNum = !(board.getRedButterflyCount() > 0);
		}
		if (!validButterflyNum)  throw new HantoException("You can only place one butterfly!");

	}
	
	/**
	 * Determine the which player moves based on number of moves so far and first player that moves
	 * 
	 * @return which player is playing this move 
	 */
	private HantoPlayerColor updatePieceColor() {
		if (colorFirstMoves == BLUE) {
			return (numMoves % 2 == 0 ? RED : BLUE);
		}
		else {
			return (numMoves % 2 == 0 ? BLUE : RED);
		}
	}

}
