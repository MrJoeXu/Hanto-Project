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

/**
 * <<Fill this in>>
 * @version Mar 16, 2016
 */
public class BetaHantoGame implements HantoGame
{
	private HantoBoard board = new HantoBoard();
	//private boolean isFirstMove = true;
	private Integer numMoves = 1;

	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		HantoPlayerColor pieceColor = (numMoves % 2 == 0 ? RED : BLUE);
		
		if (numMoves == 1){
			if (to.getX() != 0 || to.getY() != 0) {
				throw new HantoException("You can only place your piece at (0,0) for your first move!");
			}
		}
		
		if (pieceType != BUTTERFLY && pieceType != SPARROW) {
			throw new HantoException("You can only place Butterfly or Sparrow!");
		}
		
		if (numMoves == 2) {
			HantoCoordinate original = new HantoCoordinateImpl(0, 0);
			if (!board.isAdjacent(original, to)) {
				throw new HantoException("You have to place your piece adjacent to (0,0)");
			}
		}
		
		HantoPiece newPiece = new HantoPieceImpl(pieceColor, pieceType);
		try {
			board.addNewPiece(to, newPiece);
		} catch (HantoException e){throw e;}

		
		numMoves++;
		return OK;
	}

	/*
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where)
	{
		HantoPiece targetPiece = board.getPiece(where);
		return targetPiece;
	}

	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
