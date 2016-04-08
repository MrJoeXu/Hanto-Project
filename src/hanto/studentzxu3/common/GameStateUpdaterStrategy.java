/**
 * 
 */
package hanto.studentzxu3.common;

import hanto.common.MoveResult;

/**
 * GameStateUpdaterStrategy is an updater for the result of the game
 * @author JoeXu
 *
 */
public interface GameStateUpdaterStrategy {
	/**
	 * Updater for the result of the game
	 * @param board
	 * @return moveResult
	 */
	MoveResult updateGameState(HantoBoard board);
}
