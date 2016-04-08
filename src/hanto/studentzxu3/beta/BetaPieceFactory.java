/**
 * 
 */
package hanto.studentzxu3.beta;

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
public class BetaPieceFactory implements HantoPieceFactory {


	@Override
	public HantoPieceImpl makeHantoPiece(HantoPieceType pieceType, HantoPlayerColor pieceColor) throws HantoException {
		HantoPieceImpl piece = null;
		switch (pieceType) {
			case BUTTERFLY:
				MoveValidatorStrategy butterflyValid = new ButterflyValidator();
				piece = new HantoPieceImpl(pieceColor, BUTTERFLY, butterflyValid);
				break;
			case SPARROW:
				MoveValidatorStrategy sparrowValid = new SparrowValidator();
				piece = new HantoPieceImpl(pieceColor, SPARROW, sparrowValid);
				break;
		default:
			throw new HantoException("You can only place Butterfly or Sparrow!");
		}
		return piece;
	}

}
