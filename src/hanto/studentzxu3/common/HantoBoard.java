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

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import hanto.common.*;

/**
 * @author JoeXu
 * An implementation of the board to keep track of piece
 */
public class HantoBoard {
	
	
	private Map<HantoCoordinate, HantoPiece> board = new Hashtable<HantoCoordinate, HantoPiece>();
	private Integer blueButterflyCount = 0;
	private Integer redButterflyCount = 0;
	private Integer numMoves = 1;
	
	
	/**
	 * Add piece to board. Map the coordinate with placed piece
	 * 
	 * @param where
	 * 		 	The destination coordinate of new piece
	 * @param what
	 * 			The type of new piece 
	 */
	public void addNewPiece(HantoCoordinate where, HantoPiece what) {
		HantoCoordinateImpl copyWhere = new HantoCoordinateImpl(where);
		updateButterflyCount(what);	
		board.put(copyWhere, what);
		numMoves++;
	}


		
	/**
	 * check if player placed has already placed Butterfly
	 * 
	 * @param pieceColor
	 * 			the color of player
	 * 
	 * @return whether the player had placed Butterfly already
	 * 
	 */
	public boolean hasButterfly(HantoPlayerColor pieceColor) {
		if (pieceColor == BLUE) {
			return (blueButterflyCount == 0) ? false : true;
		}
		else {
			return (redButterflyCount == 0) ? false : true;
		}
	}
	
	
	
	/**
	 * get the coordinate of piece on the board
	 *
	 * @param playerColor
	 * 			determine which side to check
	 * @return whether the butterfly is surrounded
	 */
	public boolean butterflyIsSurrounded(HantoPlayerColor playerColor) {
		Set<HantoCoordinate> keys = board.keySet();
		for (HantoCoordinate key : keys) {
			if (board.get(key).getType() == BUTTERFLY && board.get(key).getColor() == playerColor) {
				HantoCoordinate butterflyCoord= new HantoCoordinateImpl(key.getX(), key.getY());
				Queue<HantoCoordinate> adjacents = ((HantoCoordinateImpl) butterflyCoord).getAdjacent();
				for (HantoCoordinate a : adjacents) {
					if(!board.containsKey(a)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 *
	 * @return whether the coordinate indicated has been occupied
	 */
	public boolean isCooedinateOccupied(HantoCoordinate where) {
		HantoCoordinateImpl copyWhere = new HantoCoordinateImpl(where);
		return board.containsKey(copyWhere);
	}
	

	
	/**
	 * print board
	 *
	 * @return string 
	 */
	public String printBoard() {
		String boardString = "";
		for (HantoCoordinate key : board.keySet()) {
			boardString += "(" + key.getX() + "," + key.getY() + ") " + board.get(key).getColor() + " "
					+ board.get(key).getType().getPrintableName() + "\n";
		}
		return boardString;
	}
	
		
	
	
	/*
	 *-----------------------Getters-------------------------------- 
	 */
	
	
	/**
	 * @return number of blue butterfly on the board
	 * 
	 */
	public Integer getBlueButterflyCount() {
		return blueButterflyCount;
	}

	/**
	 * @return number of red butterfly on the board
	 * 
	 */
	public Integer getRedButterflyCount() {
		return redButterflyCount;
	}
	

	/**
	 * 
	 * @return number of moves that has been made so for
	 * 
	 */
	public Integer getNumMoves() {
		return numMoves;
	}


	/**
	 * get the coordinate of piece on the board
	 *
	 * @param where
	 * 			coordinate of target piece
	 * @return the requested piece
	 */
	public HantoPiece getPiece(HantoCoordinate where) {
		HantoCoordinateImpl copyWhere = new HantoCoordinateImpl(where);
		return board.get(copyWhere);
	}
	
	
	/**
	 * Get the set of coordinate that already been occupied on board
	 *
	 * @return Set of HantoCoordinate
	 */
	public Set<HantoCoordinate> getOccupiedCoordinates() {
		return board.keySet();
	}	
	
	/**
	 * Get the set of coordinate implementations that already been occupied on board
	 *
	 * @return Set of HantoCoordinateImpl
	 */
	public Queue<HantoCoordinateImpl> getOccupiedCoordinatesImpl() {
		
		Queue<HantoCoordinateImpl> newSet = new LinkedList<HantoCoordinateImpl>();
		for (HantoCoordinate i : board.keySet()) {
			HantoCoordinateImpl newi = new HantoCoordinateImpl(i);
			newSet.add(newi);
		}
		return newSet;
	}	
	
	
	
	
	/*
	 *-----------------------Helper Method-------------------------------- 
	 */
	private void updateButterflyCount(HantoPiece checkPiece) {
		if (checkPiece.getType() == HantoPieceType.BUTTERFLY) {
			if(checkPiece.getColor() == BLUE) {
				blueButterflyCount++;
			} 
			else {
				redButterflyCount++; 
			}
		}
	}
}