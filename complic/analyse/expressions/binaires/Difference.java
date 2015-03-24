package complic.analyse.expressions.binaires;

import complic.analyse.ASMUtils;
import complic.analyse.Expression;
import complic.analyse.expressions.Binaire;

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