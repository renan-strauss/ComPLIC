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
public class Et extends Binaire {
	public Et(Expression fg, Expression fd) {
		super(fg, fd);
	}

	@Override
	public String getType() throws ErreurSemantique {
		if(!(this.filsGauche.getType().equals(AnalyseurLexical.TYPE_BOOLEEN)
		  && this.filsDroit.getType().equals(AnalyseurLexical.TYPE_BOOLEEN))) {
			throw new ErreurSemantique("AND : booleans expected");
		}
		return AnalyseurLexical.TYPE_BOOLEEN;
	}

	@Override
	public String genererOperation() {
		return ASMUtils.getInstance().genererEt();
	}
}