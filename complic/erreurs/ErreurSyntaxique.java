package complic.erreurs;

/**
 * @author Renan Strauss
 * Exception personnalisée
*/
public class ErreurSyntaxique extends RuntimeException {
	public ErreurSyntaxique(String in) {
		super(in);
	}
}