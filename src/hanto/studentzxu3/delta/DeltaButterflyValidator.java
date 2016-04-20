/**
 * 
 */
package hanto.studentzxu3.delta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import hanto.studentzxu3.common.BaseValidator;

import hanto.studentzxu3.common.MoveValidatorStrategy;

/**
 * @author JoeXu
 *
 */
public class DeltaButterflyValidator extends BaseValidator implements MoveValidatorStrategy {	
	/**
	 * 
	 */
	public DeltaButterflyValidator() {
		isValidMove = false;
		type = BUTTERFLY;
		canWalk = true;
		maxNum = 1;
	}
	
}
