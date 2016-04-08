/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentzxu3.common;

import hanto.common.*;

/**
 * Implementation of the HantoPiece.
 * @version Mar 2,2016
 */
public class HantoPieceImpl implements HantoPiece
{
	private final HantoPlayerColor color;
	private final HantoPieceType type;
	private final MoveValidatorStrategy validator;
	
	/**
	 * Deafault constructor
	 * @param color the piece color
	 * @param type the piece type
	 * @param validator validator for moves
	 */
	public HantoPieceImpl(HantoPlayerColor color, HantoPieceType type, MoveValidatorStrategy validator)
	{
		this.color = color;
		this.type = type;
		this.validator = validator;
	}
	/*
	 * @see hanto.common.HantoPiece#getColor()
	 */
	@Override
	public HantoPlayerColor getColor()
	{
		return color;
	}

	/*
	 * @see hanto.common.HantoPiece#getType()
	 */
	@Override
	public HantoPieceType getType()
	{
		return type;
	}
	
	/**
	 * @param from
	 * @param to
	 * @param pieceColor
	 * @param board
	 * @return boolean whether valid for move or not
	 * @throws HantoException
	 */
	public boolean canMove(final HantoCoordinate from, final HantoCoordinate to, HantoPlayerColor pieceColor, HantoBoard board) throws HantoException 
	{
		return validator.canMove(from, to, color, board);
	}
}
