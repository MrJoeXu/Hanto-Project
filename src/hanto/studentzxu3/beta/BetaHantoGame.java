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
import hanto.studentzxu3.common.GameStateUpdaterStrategy;
import hanto.studentzxu3.common.HantoBoard;
import hanto.studentzxu3.common.HantoCoordinateImpl;
import hanto.studentzxu3.common.HantoPieceFactory;
import hanto.studentzxu3.common.HantoPieceImpl;

import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;


/**
 *  The implementation of Beta Hanto.
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private final HantoPlayerColor colorFirstMoves;
	private HantoBoard board;
	private boolean gameOver;
	private HantoPieceFactory pieceFactory;
	private GameStateUpdaterStrategy stateUpdater;
	
	
	/** 
	 * Default constructor
	 * @param movesFirst color of the player who moves first.
	 */
	public BetaHantoGame(HantoPlayerColor movesFirst)
	{
		colorFirstMoves =  movesFirst;
		board = new HantoBoard();
		gameOver = false;
		pieceFactory = new BetaPieceFactory();
		stateUpdater = new BetaGameStateUpdater();
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
		
		HantoPieceImpl newPiece = pieceFactory.makeHantoPiece(pieceType, pieceColor);

		if (newPiece.canMove(from, destination, pieceColor, board)) {
			board.addNewPiece(destination, newPiece);		
		}
		
		MoveResult result = stateUpdater.updateGameState(board);
		if (result != OK) gameOver=true; 
		return result;
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

	/*
	 * ------------------------ Helper Methods ----------------------------------------
	 */
	
	/**
	 * Determine the which player moves based on number of moves so far and first player that moves
	 * 
	 * @return which player is playing this move 
	 */
	private HantoPlayerColor updatePieceColor() {
		if (colorFirstMoves == BLUE) {
			return (board.getNumMoves() % 2 == 0 ? RED : BLUE);
		}
		else {
			return (board.getNumMoves() % 2 == 0 ? BLUE : RED);
		}
	}

}
