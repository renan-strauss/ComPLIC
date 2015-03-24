package complic.analyse;

import complic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 * Represente une expression
*/
public interface Expression extends ArbreAbstrait {
	/**
	 * Retourne le type de l'expression et leve
	 * une exception si des types incompatibles 
	 * sont detectes lors de l'analyse
	*/
	public String getType() throws ErreurSemantique;
}