/**
 * 
 */
package hanto.studentzxu3.beta;

import static hanto.common.HantoPieceType.SPARROW;
import hanto.studentzxu3.common.BaseValidator;
import hanto.studentzxu3.common.MoveValidatorStrategy;

/**
 * @author JoeXu
 *
 */
public class BetaSparrowValidator extends BaseValidator implements MoveValidatorStrategy {	
	/**
	 * 
	 */
	public BetaSparrowValidator() {
		isValidMove = false;
		canWalk = false;
		type = SPARROW;
		maxNum = 5;
	}
}
