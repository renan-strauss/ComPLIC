package complic.analyse.expressions;

import complic.analyse.ASMUtils;
import complic.analyse.Expression;
import complic.analyse.TableSymboles;

import complic.erreurs.ErreurSemantique;

public class Variable implements Expression {
	private String idf;

	public Variable(String idf) {
		this.idf = idf;
	}

	public String getIdf() {
		return this.idf;
	}

	@Override
	public String getType() throws ErreurSemantique {
		TableSymboles sym = TableSymboles.getInstance();
		if(!sym.hasKey(this.idf)) {
			throw new ErreurSemantique("Unknown idf : '" + this.idf + "'");
		}

		return sym.get(this.idf).getType();
	}

	@Override
	public String generer() {
		int ptr = TableSymboles.getInstance().get(this.idf).getAddr();
		return ASMUtils.getInstance().genererVariable(ptr);
	}
}