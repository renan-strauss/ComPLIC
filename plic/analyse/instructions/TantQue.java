package plic.analyse.instructions;

import plic.analyse.ASMUtils;
import plic.analyse.Bloc;
import plic.analyse.Expression;
import plic.analyse.Instruction;

import plic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 * L'instruction tant que
*/
public class TantQue implements Instruction {
	private Expression condition;
	private Bloc repete;

	public TantQue(Expression e, Bloc b) {
		this.condition = e;
		this.repete = b;
	}

	@Override
	public String generer() {
		return ASMUtils.getInstance().genererTantQue(this.condition, this.repete);
	}

	@Override
	public void verifier() throws ErreurSemantique {
	}
}