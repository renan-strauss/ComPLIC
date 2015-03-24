package complic.analyse.instructions;

import complic.analyse.ASMUtils;
import complic.analyse.Instruction;
import complic.analyse.TableSymboles;

import complic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 * L'instruction ecrire
*/
public class Ecrire implements Instruction {
	private String idf;

	public Ecrire(String idf) {
		this.idf = idf;
	}

	@Override
	public String generer() {
		int ptr = TableSymboles.getInstance().get(this.idf).getAddr();
		return ASMUtils.getInstance().genererEcrire(ptr);
	}

	@Override
	public void verifier() throws ErreurSemantique {
		if(!TableSymboles.getInstance().hasKey(this.idf)) {
			throw new ErreurSemantique("Identificateur inexistant : " + this.idf);
		}
	}

	@Override
	public String toString() {
		return "ecrire " + idf + "\n";
	}
}