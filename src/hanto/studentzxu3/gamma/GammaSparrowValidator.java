/**
 * 
 */
package hanto.studentzxu3.gamma;

import static hanto.common.HantoPieceType.*;
import hanto.studentzxu3.common.BaseValidator;
import hanto.studentzxu3.common.MoveValidatorStrategy;

/**
 * @author JoeXu
 *
 */
public class GammaSparrowValidator extends BaseValidator implements MoveValidatorStrategy {
	
	/**
	 * 
	 */
	public GammaSparrowValidator() {
		isValidMove = false;
		type = SPARROW;
		canWalk = true;
	}
}
