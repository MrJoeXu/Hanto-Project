/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studentzxu3.beta;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studentzxu3.HantoGameFactory;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class BetaHantoMasterTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}

	}
	
	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, BLUE);
	}
	
	// 1
	@Test	
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	

	// 2 
	@Test(expected = HantoException.class)
	public void  bluePlaceInitialButterflyAtNonOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
	}
	
	
	// 3
	@Test(expected = HantoException.class)
	public void bluePlaceInitialPieceNotButterflyOrSparrow() throws HantoException
	{
		game.makeMove(CRAB, null, makeCoordinate(0,0));
	}
	
	// 4
	@Test
	public void redPlaceInitialPieceAdjacentToOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, 1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(1, 1));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	// 5
	@Test(expected = HantoException.class)
	public void redPlaceInitialPieceNotAdjacentToOrigin() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	}
	
	
	// 6
	@Test(expected = HantoException.class)
	public void redPlacePieceNotButterflyOrSparrow() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(HORSE, null, makeCoordinate(1,0));
	}
	
	// 7
	@Test(expected = HantoException.class)
	public void bluePlacePieceAtOccupiedLoaction() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	// 8
	@Test(expected = HantoException.class)
	public void redPlacePieceAtOccupiedLoaction() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
	}
	
	// 9
	@Test(expected = HantoException.class)
	public void bluePlacePieceAtNonAdjacentLocation() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-3, 1));

	}
	
	// 10
	@Test
	public void bluePlacePieceAtAdjacentLocation() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		assertEquals(OK, mr);
	}
	
	
	// 11
	@Test(expected = HantoException.class)
	public void redPlacePieceAtNonAdjacentLocation() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, -2));

	}
	
	// 12
	@Test
	public void redPlacePieceAtAdjacentLocation() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1, -1));
		assertEquals(OK, mr);
	} 
	
	// 13
	@Test(expected = HantoException.class)
	public void redPlacesButterflyAtOrigin() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	} 
	
	// 14
	@Test(expected = HantoException.class)
	public void blueTriesToPlaceSecondButterfly() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1));
	}
	
	// 15
	@Test(expected = HantoException.class)
	public void redTriesToPlaceSecondButterfly() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1));

		game.makeMove(BUTTERFLY, null, makeCoordinate(-2, 2));
	}
	
	// 16
	@Test(expected = HantoException.class)
	public void blueDoesNotPlaceButterflyByFourthRound() throws HantoException {
		game.makeMove(SPARROW, null, makeCoordinate(0,0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		game.makeMove(SPARROW, null, makeCoordinate(1,-2));
		game.makeMove(SPARROW, null, makeCoordinate(0,-2));
		game.makeMove(SPARROW, null, makeCoordinate(-1,-1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
	} 
	
	// 17
	@Test(expected = HantoException.class)
	public void redDoesNotPlaceButterflyByFourthRound() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0));
		game.makeMove(SPARROW, null, makeCoordinate(-1,1));
		game.makeMove(SPARROW, null, makeCoordinate(0,1));
		game.makeMove(SPARROW, null, makeCoordinate(1,1));
		game.makeMove(SPARROW, null, makeCoordinate(2,0));
		game.makeMove(SPARROW, null, makeCoordinate(0,-1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
	}
	
	// 18
	@Test
	public void blueDoesPlaceButterflyAtFourthRound() throws HantoException {
		game.makeMove(SPARROW, null, makeCoordinate(0,0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,0));
		game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		game.makeMove(SPARROW, null, makeCoordinate(1,-2));
		game.makeMove(SPARROW, null, makeCoordinate(0,-2));
		game.makeMove(SPARROW, null, makeCoordinate(-1,-1));
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
		assertEquals(mr, OK);
	}
	
	// 19
	@Test
	public void redDoesPlaceButterflyAtFourthRound() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0));
		game.makeMove(SPARROW, null, makeCoordinate(1,0));
		game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		game.makeMove(SPARROW, null, makeCoordinate(1,-2));
		game.makeMove(SPARROW, null, makeCoordinate(0,-2));
		game.makeMove(SPARROW, null, makeCoordinate(-1,-1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -3));
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
		assertEquals(mr, OK);
	} 
	
	// 20
	@Test
	public void blueButterflyIsSorroundedAndRedWins() throws HantoException {
		game.makeMove(SPARROW, null, makeCoordinate(0,0));
		game.makeMove(SPARROW, null, makeCoordinate(1,0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,1));
		game.makeMove(SPARROW, null, makeCoordinate(0,2));
		game.makeMove(SPARROW, null, makeCoordinate(-1,2));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1,1));
		assertEquals(mr, RED_WINS);
		String board = game.getPrintableBoard();
		System.out.print(board);
	}
	
	
	// 21
	@Test
	public void redButterflyIsSorroundedAndBlueWins() throws HantoException {
		game.makeMove(SPARROW, null, makeCoordinate(0,0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1,1));
		game.makeMove(SPARROW, null, makeCoordinate(-1,0));
		game.makeMove(SPARROW, null, makeCoordinate(-2,1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-2,2));
		game.makeMove(SPARROW, null, makeCoordinate(-1,2));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0,1));
		assertEquals(mr, BLUE_WINS);
	}
	
	// 22
	@Test
	public void redAndBlueButterflyAreBothSurroundedAndDraw() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1,1));
		game.makeMove(SPARROW, null, makeCoordinate(-2,1));
		game.makeMove(SPARROW, null, makeCoordinate(0,-1));
		game.makeMove(SPARROW, null, makeCoordinate(-2,2));
		game.makeMove(SPARROW, null, makeCoordinate(1,-1));
		game.makeMove(SPARROW, null, makeCoordinate(-1,2));
		game.makeMove(SPARROW, null, makeCoordinate(1,0));
		game.makeMove(SPARROW, null, makeCoordinate(0,1));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1,0));
		assertEquals(mr, DRAW);
	}
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
	
}
