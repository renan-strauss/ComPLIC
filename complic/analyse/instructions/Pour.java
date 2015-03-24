package complic.analyse.instructions;

import complic.analyse.ASMUtils;
import complic.analyse.Bloc;
import complic.analyse.Expression;
import complic.analyse.Instruction;
import complic.analyse.expressions.Variable;

import complic.definitions.Types;

import complic.erreurs.ErreurSemantique;

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
			throw new ErreurSemantique("'pour' expected type 'entier'");
		}
	}
}