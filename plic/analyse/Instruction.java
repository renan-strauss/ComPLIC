package plic.analyse;

import plic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 * Represente une instruction
*/
public interface Instruction extends ArbreAbstrait {
	/**
	 * Analyse semantique de l'instruction
	*/
	public void verifier() throws ErreurSemantique;
}