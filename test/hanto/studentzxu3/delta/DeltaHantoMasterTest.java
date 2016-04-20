package hanto.studentzxu3.delta;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.studentzxu3.HantoGameFactory;

/**
 * Test cases for Gamma Hanto.
 */
public class DeltaHantoMasterTest {
	
	class MoveData {
		final HantoPieceType type;
		final HantoCoordinate from, to;
		
		private MoveData(HantoPieceType type, HantoCoordinate from, HantoCoordinate to) 
		{
			this.type = type;
			this.from = from;
			this.to = to;
		}
	}
	
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
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, BLUE);
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
	@Test	
	public void bluePlacesInitialSparrowAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	// 3
	@Test	
	public void bluePlacesInitialCrabAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(CRAB, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(CRAB, p.getType());
	}
	
	// 4
	@Test(expected = HantoException.class)
	public void bluePlaceInitialPieceNotButterflyOrSparrowOrCrab() throws HantoException
	{
		game.makeMove(HORSE, null, makeCoordinate(0,0));
	}
	
	// 5
	@Test	
	public void redPlacesInitialSparrowAtOrigin() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);	// RedFirst
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	// 6
	@Test(expected = HantoException.class)
	public void  bluePlaceInitialButterflyAtNonOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
	}
	
	// 7
	@Test(expected = HantoException.class)	
	public void validFirstMoveNonAdjacentHexSecondButterflyMove() throws HantoException
	{
		game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	// 8
	@Test(expected = HantoException.class)	
	public void validFirstMoveNonAdjacentHexSecondCrabMove() throws HantoException
	{
		game.makeMove(SPARROW,  null, makeCoordinate(0, 0));
		game.makeMove(CRAB, null, makeCoordinate(-1, -1));
	}
	
	// 9
	@Test(expected = HantoException.class)	
	public void blueTriesToPlacePieceOnOccupiedHex() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(CRAB, null, makeCoordinate(0, 0));
	}
	
	// 10
	@Test(expected = HantoException.class)
	public void blueAttemptsToPlaceTwoButterflies() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
	}
	
	// 11
	@Test
	public void redPlacesButterflyNextToBlueButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, new TestHantoCoordinate(0, 1));
		final HantoPiece p = game.getPieceAt(new TestHantoCoordinate(0, 1));
		assertEquals(BUTTERFLY, p.getType());
		assertEquals(RED, p.getColor());
	}
	
	
	// 12
	@Test(expected = HantoException.class)
	public void blueDoesNotPlaceButterflyByFourthMove() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
		game.makeMove(CRAB, null, makeCoordinate(2, 0));	// Move 4
	}
	
	// 13
	@Test(expected = HantoException.class)
	public void redDoesNotPlaceButterflyByFourthMove() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));	// Move 1
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));	// Move 2
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 1));	// Move 3
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0));	// Move 4
		game.makeMove(CRAB, null, makeCoordinate(-3, 0));
	}

	// 14
	@Test(expected=HantoException.class)
	public void tooManySparrows() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(SPARROW, 0, 3),
				md(SPARROW, 0, -3), md(SPARROW, 0, 4),
				md(SPARROW, 0, -4), md(SPARROW, 0, 5),
				md(SPARROW, 0, -5));
	}
	
	
	// 15
	@Test(expected=HantoException.class)
	public void tooManyCrabs() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(CRAB, 0, -2), md(CRAB, 0, 3),
				md(CRAB, 0, -3), md(CRAB, 0, 4),
				md(CRAB, 0, -4), md(CRAB, 0, 5),
				md(CRAB, 0, -5));
	}
	
	// 16
	@Test
	public void blueResign() throws HantoException{
		MoveResult result = game.makeMove(null, null, null);
		assertEquals(result, MoveResult.RED_WINS);
	}

	// 17
	@Test
	public void redResign() throws HantoException{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		MoveResult result = game.makeMove(null, null, null);
		assertEquals(result, MoveResult.BLUE_WINS);
	}
	
	
	
	// 25
	@Test	
	public void redTriesToMoveButterflyAdjacentToBlue() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Move 2
		final MoveResult mr = game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, 1)); // Move 2
		assertEquals(mr, OK);
	}
	
	// 26
	@Test(expected = HantoException.class)	
	public void blueTriesToMoveRedButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, makeCoordinate(1, 0), makeCoordinate(0, -1)); // Move 2
	} 
	
	// 27
	@Test(expected = HantoException.class)	
	public void redTriesToMoveBlueSparrow() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, -1));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1)); // Move 2
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, -1)); 
	}
	
	// 28
	@Test(expected = HantoException.class)	
	public void blueTriesToMoveSparrowBeforePlaceButterfly() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Move 1
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(1, -1)); // Move 2
	}
	
	// 29
	@Test(expected = HantoException.class)	
	public void redTriesToMoveSparrowBeforePlaceButterfly() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Move 1
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1)); // Move 2
	}
	
	// 30
	@Test(expected = HantoException.class)	
	public void blueTriesToMoveSparrowThroughSingleHexOpening() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, -2));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(2, -1));
		game.makeMove(SPARROW, makeCoordinate(0, 0), makeCoordinate(-1, 1));
	}	
	
	// 31
	@Test(expected = HantoException.class)	
	public void redTriesToMoveButterflyThroughSingleHexOpening() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1,0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, makeCoordinate(-1, 0), makeCoordinate(-1, 1));
	}
	
	// 34
	@Test(expected = HantoException.class)	
	public void playerTriesToButterflyPieceViolatingContiguity() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, -1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 1));
	}
	
	// 35
	@Test(expected = HantoException.class)	
	public void playerTriesToMoveSparrowViolatingContiguity() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, -1));
		game.makeMove(SPARROW, makeCoordinate(1, 0), makeCoordinate(1, 1));
	}
	
	// 32
	@Test(expected = HantoException.class)	
	public void blueTriesToMoveButterflyTwoHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0,1));
		game.makeMove(BUTTERFLY, makeCoordinate(0, 0), makeCoordinate(-1, 2));
	}
	
	// 33
	@Test	
	public void SparrowValidlyFlyTwoHex() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(1,-1));
		game.makeMove(SPARROW, null, makeCoordinate(0,1));
		game.makeMove(SPARROW, null, makeCoordinate(2,-1));
		game.makeMove(SPARROW, null, makeCoordinate(0,2));
		game.makeMove(SPARROW, makeCoordinate(2, -1), makeCoordinate(1, 1));
	}
	
	

	/**
	 * valid sparrow fly
	 * @throws HantoException
	 */
	@Test
	public void SparrowValidlyFlyThreeHex() throws HantoException {
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		game.makeMove(SPARROW, null, makeCoordinate(0, -1));
		game.makeMove(CRAB, null, makeCoordinate(-1, 2));
		game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(0, 2));
	}
	
	
	
	// 39
	@Test
	public void RedMovePieceblueButterflyIsSorroundedAndRedWins() throws HantoException {
        game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
        game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
        game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
        game.makeMove(SPARROW, null, makeCoordinate(1, 1));
        game.makeMove(SPARROW, null, makeCoordinate(-2, 1));
        game.makeMove(SPARROW, null, makeCoordinate(2, -1));
        game.makeMove(SPARROW, null, makeCoordinate(0, -1));
        game.makeMove(SPARROW, makeCoordinate(1, 1), makeCoordinate(0, 1));
        game.makeMove(SPARROW, makeCoordinate(-2, 1), makeCoordinate(-1, 0));
        MoveResult mr = game.makeMove(SPARROW, makeCoordinate(2, -1), makeCoordinate(1, -1));
        assertEquals(mr, RED_WINS);
	}
	
	
	// 40
	@Test
	public void BlueMovePieceReddButterflyIsSorroundedAndBlueWins() throws HantoException {
        // Turn 1
        game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
        game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
        game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
        game.makeMove(SPARROW, null, makeCoordinate(1, 1));
        game.makeMove(SPARROW, null, makeCoordinate(0, -1));
        game.makeMove(SPARROW, null, makeCoordinate(2, -1));
        game.makeMove(SPARROW, makeCoordinate(0, -1), makeCoordinate(1, -1));
        game.makeMove(SPARROW, null, makeCoordinate(2, 0));
        MoveResult mr = game.makeMove(SPARROW, makeCoordinate(-1, 1), makeCoordinate(0, 1));
        assertEquals(mr, BLUE_WINS);
	}
	
	// 41
    @Test
    public void blueRedDrawGameBothSorroundButterfly() throws HantoException {
    	game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
    	game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
    	game.makeMove(SPARROW, null, makeCoordinate(-1, 0));
    	game.makeMove(SPARROW, null, makeCoordinate(1, 1));
    	game.makeMove(SPARROW, null, makeCoordinate(-1, 1));
    	game.makeMove(SPARROW, null, makeCoordinate(2, -1));
    	game.makeMove(SPARROW, null, makeCoordinate(0, -1));
    	game.makeMove(SPARROW, null, makeCoordinate(2, 0));
    	game.makeMove(SPARROW, null, makeCoordinate(-1, 2));
    	game.makeMove(SPARROW, null, makeCoordinate(2, -2));
    	game.makeMove(SPARROW, makeCoordinate(-1, 2), makeCoordinate(0, 1));
    	MoveResult mr = game.makeMove(SPARROW, makeCoordinate(2, -2), makeCoordinate(1, -1));
        assertEquals(DRAW, mr);
        game.getPrintableBoard();
    }
	
	
	
	
	
	
	
	
	
	
	/**
	 * Make a MoveData object given the piece type and the x and y coordinates of the
	 * desstination. This creates a move data that will place a piece (source == null)
	 * @param type piece type
	 * @param toX destination x-coordinate
	 * @param toY destination y-coordinate
	 * @return the desitred MoveData object
	 */
	private MoveData md(HantoPieceType type, int toX, int toY) 
	{
		return new MoveData(type, null, makeCoordinate(toX, toY));
	}
	
	private MoveData md(HantoPieceType type, int fromX, int fromY, int toX, int toY)
	{
		return new MoveData(type, makeCoordinate(fromX, fromY), makeCoordinate(toX, toY));
	}
	
	/**
	 * Make the moves specified. If there is no exception, return the move result of
	 * the last move.
	 * @param moves
	 * @return the last move result
	 * @throws HantoException
	 */
	private MoveResult makeMoves(MoveData... moves) throws HantoException
	{
		MoveResult mr = null;
		for (MoveData md : moves) {
			mr = game.makeMove(md.type, md.from, md.to);
		}
		return mr;
	}
	/**
	 * helper method
	 * @param x
	 * @param y
	 * @return HantoCoordinate created
	 */
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
	
	@Test(expected=HantoException.class)
	public void placePieceInNonAdjacentPosition() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(3, -2));
	}
	
	
	
	
	
	
}