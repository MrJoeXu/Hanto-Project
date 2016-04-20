/**
 * 
 */
package hanto.studentzxu3.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.HantoPlayerColor.RED;

import java.util.Queue;
import java.util.Set;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentzxu3.common.BaseValidator;
import hanto.studentzxu3.common.HantoBoard;
import hanto.studentzxu3.common.HantoCoordinateImpl;
import hanto.studentzxu3.common.MoveValidatorStrategy;

/**
 * @author JoeXu
 *
 */
public class GammaButterflyValidator extends BaseValidator implements MoveValidatorStrategy {	
	/**
	 * 
	 */
	public GammaButterflyValidator() {
		isValidMove = false;
		type = BUTTERFLY;
		canWalk = true;
	}
	
}
