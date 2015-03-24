package plic.analyse.expressions.binaires;

import plic.analyse.ASMUtils;
import plic.analyse.Expression;
import plic.analyse.expressions.Binaire;

/**
 * @author Renan Strauss
 * Un produit
*/
public class Quotient extends Binaire {
	public Quotient(Expression fg, Expression fd) {
		super(fg, fd);
	}

	@Override
	public String genererOperation() {
		return ASMUtils.getInstance().genererQuotient();
	}
}