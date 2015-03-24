package plic.analyse.expressions.binaires;

import plic.analyse.ASMUtils;
import plic.analyse.Expression;
import plic.analyse.expressions.Binaire;

/**
 * @author Renan Strauss
 * Une difference
*/
public class Difference extends Binaire {
	public Difference(Expression fg, Expression fd) {
		super(fg, fd);
	}

	@Override
	public String genererOperation() {
		return ASMUtils.getInstance().genererDifference();
	}
}