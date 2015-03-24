package complic.analyse.expressions.binaires;

import complic.analyse.ASMUtils;
import complic.analyse.Expression;
import complic.analyse.expressions.Binaire;

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