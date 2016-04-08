/**
 * 
 */
package hanto.studentzxu3.beta;

import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;

import hanto.common.MoveResult;
import hanto.studentzxu3.common.GameStateUpdaterStrategy;
import hanto.studentzxu3.common.HantoBoard;

/**
 * An implementation of GameStateUpdaterStrategy for Beta Hanto
 * @author JoeXu
 * 
 */
public class BetaGameStateUpdater implements GameStateUpdaterStrategy {


	@Override
	public MoveResult updateGameState(HantoBoard board) {
		if (board.butterflyIsSurrounded(BLUE) && board.butterflyIsSurrounded(RED)) {
			return DRAW;
		}
		else if (board.butterflyIsSurrounded(BLUE)) {
			return RED_WINS;
		}
		else if (board.butterflyIsSurrounded(RED)) {
			return BLUE_WINS;
		}
		else if (board.getNumMoves() > 12) {
			return DRAW;
		}
		return OK;
	}

}
