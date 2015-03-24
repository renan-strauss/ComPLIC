package plic.analyse.expressions;

import plic.analyse.ASMUtils;
import plic.analyse.Expression;
import plic.analyse.Lexique;

import plic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 * Represente une operation binaire
 * entre deux expressions
*/
public abstract class Binaire implements Expression {
	protected Expression filsGauche;
	protected Expression filsDroit;

	public Binaire(Expression fg, Expression fd) {
		this.filsGauche = fg;
		this.filsDroit = fd;
	}

	public abstract String genererOperation();

	@Override
	public String generer() {
		StringBuffer asm = new StringBuffer();

		asm.append(this.filsGauche.generer());
		asm.append(ASMUtils.getInstance().genererEmpiler());
		asm.append(this.filsDroit.generer());
		asm.append(ASMUtils.getInstance().genererDepiler());

		asm.append(this.genererOperation());

		return asm.toString();
	}

	@Override
	public String getType() throws ErreurSemantique {
		String fgType = this.filsGauche.getType();
		String fdType = this.filsDroit.getType();
		if(!fgType.equals(fdType)) {
			throw new ErreurSemantique("Types incompatibles : " + fgType + " et " + fdType);
		}

		// Par defaut
		return Lexique.TYPE_ENTIER;
	}
}