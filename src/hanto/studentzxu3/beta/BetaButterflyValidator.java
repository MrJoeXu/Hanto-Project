/**
 * 
 */
package hanto.studentzxu3.beta;

import hanto.studentzxu3.common.BaseValidator;
import hanto.studentzxu3.common.MoveValidatorStrategy;
import static hanto.common.HantoPieceType.BUTTERFLY;


/**
 * @author JoeXu
 *
 */
public class BetaButterflyValidator extends BaseValidator implements MoveValidatorStrategy {

	public BetaButterflyValidator() {
		isValidMove = false;
		canWalk = false;
		type = BUTTERFLY;
		maxNum = 1;
	}
}
