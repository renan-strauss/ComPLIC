package plic.analyse.expressions;

import plic.analyse.ASMUtils;
import plic.analyse.Expression;
import plic.analyse.Lexique;

import plic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 * Represente un nombre entier
*/
public class ConstanteEntiere implements Expression {
	private int valeur;

	public ConstanteEntiere(int val) {
		this.valeur = val;
	}

	@Override
	public String getType() throws ErreurSemantique {
		return Lexique.TYPE_ENTIER;
	}

	@Override
	public String generer() {
		return ASMUtils.getInstance().genererConstanteEntiere(this.valeur);
	}
}