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
public class DeltaSparrowValidator extends BaseValidator implements MoveValidatorStrategy {	
	/**
	 * 
	 */
	public DeltaSparrowValidator() {
		isValidMove = false;
		type = SPARROW;
		canFly = true;
		canWalk = false;
		maxNum = 4;
	}
	
}
