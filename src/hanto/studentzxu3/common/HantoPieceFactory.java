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

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import hanto.common.HantoException;

/**
 * This is a singleton class that provides a factory to create an instance of any kind of HantoPiece
 * @author JoeXu
 */
public interface HantoPieceFactory {
	
	/**
	 * Factory method that returns the appropriately HantoPieceImpl with its validator
	 * @param pieceType
	 * @param pieceColor
	 * @return appropiate piece with corret validator as requested
	 * @throws HantoException
	*/
	 HantoPieceImpl makeHantoPiece(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException;
}
