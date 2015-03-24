package complic.analyse.instructions;

import complic.analyse.ASMUtils;
import complic.analyse.Bloc;
import complic.analyse.Expression;
import complic.analyse.Instruction;

import complic.definitions.Lexique;
import complic.definitions.Types;

import complic.erreurs.ErreurSemantique;

public class Condition implements Instruction {
	private Expression expr;
	private Bloc then;
	private Bloc otherwise;

	public Condition(Expression e, Bloc th, Bloc othw) {
		this.expr = e;
		this.then = th;
		this.otherwise = othw;
	}

	@Override
	public void verifier() throws ErreurSemantique {
		if(!this.expr.getType().equals(Types.BOOLEEN)) {
			throw new ErreurSemantique("Condition expected Expression of type 'booleen'");
		}
		this.then.verifier();
		if(this.otherwise != null) {
			this.otherwise.verifier();
		}
	}

	@Override
	public String generer() {
		return ASMUtils.getInstance().genererCondition(this.expr, this.then, this.otherwise);
	}
}