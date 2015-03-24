package complic.analyse.expressions;

import complic.analyse.ASMUtils;
import complic.analyse.Expression;

import complic.definitions.Lexique;
import complic.definitions.Types;

import complic.erreurs.ErreurSemantique;

public class ConstanteBooleenne implements Expression {
	private int valeur;

	private ConstanteBooleenne(int val) {
		this.valeur = val;
	}

	@Override
	public String getType() throws ErreurSemantique {
		return Types.BOOLEEN;
	}

	public static ConstanteBooleenne getByValue(String val) {
		switch(val) {
			case Lexique.VRAI:
				return new ConstanteBooleenne(1);
			case Lexique.FAUX:
				return new ConstanteBooleenne(0);
			default:
				return null;
		}
	}

	@Override
	public String generer() {
		return ASMUtils.getInstance().genererConstanteEntiere(this.valeur);
	}
}