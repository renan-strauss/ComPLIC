package complic.analyse.instructions;

import complic.analyse.ASMUtils;
import complic.analyse.Bloc;
import complic.analyse.Expression;
import complic.analyse.Instruction;

import complic.definitions.Types;

import complic.erreurs.ErreurSemantique;

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
		if(!this.condition.getType().equals(Types.BOOLEEN)) {
			throw new ErreurSemantique("tant que : valeur booleenne attendue");
		}
	}
}