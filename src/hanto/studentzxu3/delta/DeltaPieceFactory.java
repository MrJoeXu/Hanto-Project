/**
 * 
 */
package hanto.studentzxu3.delta;

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
public class DeltaPieceFactory implements HantoPieceFactory {

	@Override
	public HantoPieceImpl makeHantoPiece(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException {
		HantoPieceImpl piece = null;
		switch (pieceType) {
			case BUTTERFLY:
				MoveValidatorStrategy butterflyValid = new DeltaButterflyValidator();
				piece = new HantoPieceImpl(pieceColor, BUTTERFLY, butterflyValid);
				break;
			case SPARROW:
				MoveValidatorStrategy sparrowValid = new DeltaSparrowValidator();
				piece = new HantoPieceImpl(pieceColor, SPARROW, sparrowValid);
				break;
			case CRAB:
				MoveValidatorStrategy crabValid = new DeltaCrabValidator();
				piece = new HantoPieceImpl(pieceColor, CRAB, crabValid);
				break;
		default:
			throw new HantoException("You can only place Butterfly, Sparrow or Crab!");
		}
		return piece;
	}

}