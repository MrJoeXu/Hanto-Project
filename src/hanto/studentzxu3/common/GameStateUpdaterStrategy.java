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
	public MoveResult updateGameState(HantoBoard board);
}
