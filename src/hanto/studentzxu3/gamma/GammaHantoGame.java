/**
 * 
 */
package hanto.studentzxu3.gamma;

import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.OK;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentzxu3.beta.BetaGameStateUpdater;
import hanto.studentzxu3.beta.BetaPieceFactory;
import hanto.studentzxu3.common.HantoBoard;
import hanto.studentzxu3.common.HantoCoordinateImpl;
import hanto.studentzxu3.common.HantoPieceFactory;
import hanto.studentzxu3.common.HantoPieceImpl;

/**
 * @author JoeXu
 *
 */
public class GammaHantoGame implements HantoGame{
	
	private HantoPlayerColor colorFirstMoves;
	private HantoBoard board;
	private HantoPieceFactory pieceFactory;
	/** 
	 * Default constructor
	 * @param movesFirst color of the player who moves first.
	 */
	public GammaHantoGame(HantoPlayerColor movesFirst)
	{
		colorFirstMoves =  movesFirst;
		board = new HantoBoard();
		pieceFactory = new GammaPieceFactory();
		
	}

	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		final HantoCoordinateImpl destination = new HantoCoordinateImpl(to);
		HantoPlayerColor pieceColor = updatePieceColor();
		
		HantoPieceImpl newPiece = pieceFactory.makeHantoPiece(pieceType, pieceColor);

		if (newPiece.canMove(from, destination, pieceColor, board)) {
			board.addNewPiece(destination, newPiece);		
		}

		return OK;
	}

	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		HantoPiece targetPiece = board.getPiece(where);
		return targetPiece;
	}

	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
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
