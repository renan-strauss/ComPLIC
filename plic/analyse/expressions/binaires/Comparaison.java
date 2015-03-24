package plic.analyse.expressions.binaires;

import plic.analyse.ASMUtils;
import plic.analyse.Expression;
import plic.analyse.expressions.Binaire;

import plic.definitions.Lexique;
import plic.definitions.Types;

import plic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 *
*/
public class Comparaison extends Binaire {
	private String operateur;

	public Comparaison(Expression fg, String op, Expression fd) {
		super(fg, fd);

		this.operateur = op;
	}

	@Override
	public String getType() throws ErreurSemantique {
		super.getType();

		return Types.BOOLEEN;
	}

	@Override
	public String genererOperation() {
		switch(this.operateur) {
			case Lexique.OP_EQ:
				return ASMUtils.getInstance().genererEgal();
			case Lexique.OP_NEQ:
				return ASMUtils.getInstance().genererNonEgal();
			case Lexique.OP_LT:
				return ASMUtils.getInstance().genererInferieur();
			case Lexique.OP_GT:
				return ASMUtils.getInstance().genererSuperieur();
			case Lexique.OP_LOET:
				return ASMUtils.getInstance().genererInferieurOuEgal();
			case Lexique.OP_GOET:
				return ASMUtils.getInstance().genererSuperieurOuEgal();
			default:
				// Ca ne devrait jamais arriver
			  // TODO : check operateur dans getType()
				return "j end";
		}
	}
}