package complic.analyse.expressions;

import complic.analyse.ASMUtils;
import complic.analyse.Expression;

import complic.definitions.Types;

import complic.erreurs.ErreurSemantique;

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
		return Types.ENTIER;
	}

	@Override
	public String generer() {
		return ASMUtils.getInstance().genererConstanteEntiere(this.valeur);
	}
}