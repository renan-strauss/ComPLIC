package plic.erreurs;

/**
 * @author Renan Strauss
 * Exception personnalisée
*/
public class ErreurSemantique extends RuntimeException {
	public ErreurSemantique(String in) {
		super(in);
	}
}