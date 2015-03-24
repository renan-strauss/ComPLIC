package plic.analyse.instructions;

import plic.analyse.ASMUtils;
import plic.analyse.Bloc;
import plic.analyse.Expression;
import plic.analyse.Instruction;
import plic.analyse.expressions.Variable;

import plic.definitions.Types;

import plic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 * L'instruction pour
*/
public class Pour implements Instruction {
	private Variable var;
	private Expression debut;
	private Expression fin;
	private Bloc repete;

	public Pour(Variable v, Expression d, Expression f, Bloc r) {
		this.var = v;
		this.debut = d;
		this.fin = f;
		this.repete = r;
	}

	@Override
	public String generer() {
		return ASMUtils.getInstance().genererPour(this.var, this.debut, this.fin, this.repete);
	}

	@Override
	public void verifier() throws ErreurSemantique {
		if(!this.debut.getType().equals(Types.ENTIER) || !this.fin.getType().equals(Types.ENTIER)) {
			throw new ErreurSemantique("pour : bornes entieres attendues");
		}
	}
}