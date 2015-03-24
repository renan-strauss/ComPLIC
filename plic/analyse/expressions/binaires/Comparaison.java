package plic.analyse.expressions.binaires;

import plic.analyse.AnalyseurLexical;
import plic.analyse.ASMUtils;
import plic.analyse.Expression;
import plic.analyse.expressions.Binaire;

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

		return AnalyseurLexical.TYPE_BOOLEEN;
	}

	@Override
	public String genererOperation() {
		switch(this.operateur) {
			case AnalyseurLexical.OP_EQ:
				return ASMUtils.getInstance().genererEgal();
			case AnalyseurLexical.OP_NEQ:
				return ASMUtils.getInstance().genererNonEgal();
			case AnalyseurLexical.OP_LT:
				return ASMUtils.getInstance().genererInferieur();
			case AnalyseurLexical.OP_GT:
				return ASMUtils.getInstance().genererSuperieur();
			case AnalyseurLexical.OP_LOET:
				return ASMUtils.getInstance().genererInferieurOuEgal();
			case AnalyseurLexical.OP_GOET:
				return ASMUtils.getInstance().genererSuperieurOuEgal();
			default:
				// Ca ne devrait jamais arriver
			  // TODO : check operateur dans getType()
				return "j end";
		}
	}
}