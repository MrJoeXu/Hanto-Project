/**
 * 
 */
package hanto.studentzxu3.delta;

import static hanto.common.HantoPieceType.*;
import hanto.studentzxu3.common.BaseValidator;

import hanto.studentzxu3.common.MoveValidatorStrategy;

/**
 * @author JoeXu
 *
 */
public class DeltaCrabValidator extends BaseValidator implements MoveValidatorStrategy {	
	/**
	 * 
	 */
	public DeltaCrabValidator() {
		isValidMove = false;
		type = CRAB;
		canWalk = true;
		maxNum = 4;
	}
	
}
