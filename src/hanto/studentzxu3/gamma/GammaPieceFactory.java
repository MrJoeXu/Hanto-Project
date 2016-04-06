/**
 * 
 */
package hanto.studentzxu3.gamma;

import hanto.common.HantoException;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentzxu3.common.HantoPieceFactory;
import hanto.studentzxu3.common.HantoPieceImpl;
import hanto.studentzxu3.common.MoveValidatorStrategy;
import static hanto.common.HantoPieceType.*;


/**
 * @author JoeXu
 *
 */
public class GammaPieceFactory implements HantoPieceFactory {

	/**
	 * 
	 */
	public GammaPieceFactory() {};

	@Override
	public HantoPieceImpl makeHantoPiece(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException {
		HantoPieceImpl piece = null;
		switch (pieceType) {
			case BUTTERFLY:
				MoveValidatorStrategy butterflyValid = new ButterflyCanWalkValidator();
				piece = new HantoPieceImpl(pieceColor, BUTTERFLY, butterflyValid);
				break;
			case SPARROW:
				MoveValidatorStrategy sparrowValid = new SparrowCanWalkValidator();
				piece = new HantoPieceImpl(pieceColor, SPARROW, sparrowValid);
				break;
		default:
			throw new HantoException("You can only place Butterfly or Sparrow!");
		}
		return piece;
	}

}