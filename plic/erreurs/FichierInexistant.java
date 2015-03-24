package plic.erreurs;

/**
 * @author Renan Strauss
 * Exception personnalisée
*/
public class FichierInexistant extends RuntimeException {
	public FichierInexistant(String in) {
		super("Fichier inexistant : " + in);
	}
}