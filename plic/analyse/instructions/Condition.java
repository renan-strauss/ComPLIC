package plic.analyse.instructions;

import plic.analyse.ASMUtils;
import plic.analyse.Bloc;
import plic.analyse.Expression;
import plic.analyse.Instruction;

import plic.definitions.Lexique;
import plic.definitions.Types;

import plic.erreurs.ErreurSemantique;

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
			throw new ErreurSemantique("Boolean expected");
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