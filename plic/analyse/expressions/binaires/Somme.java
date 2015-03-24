package plic.analyse.expressions.binaires;

import plic.analyse.ASMUtils;
import plic.analyse.Expression;
import plic.analyse.expressions.Binaire;

/**
 * @author Renan Strauss
 * Une somme
*/
public class Somme extends Binaire {
	public Somme(Expression fg, Expression fd) {
		super(fg, fd);
	}

	@Override
	public String genererOperation() {
		return ASMUtils.getInstance().genererSomme();
	}
}