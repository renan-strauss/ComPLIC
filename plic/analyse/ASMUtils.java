package plic.analyse;

/**
 * @author Renan Strauss
 * Un generateur de code assembleur
 * generique
*/
public abstract class ASMUtils {
	private static ASMUtils instance = null;
	public static final int MIPS = 0;

	/**
	 * Genere le bloc passe en parametre,
	 * avec les etiquettes de debut
	 * et de fin
	*/
	public abstract String generer(Bloc blk);

	public abstract String genererEntete();
	public abstract String genererAffectation(Expression expr, int ptr);
	public abstract String genererEcrire(int ptr);
	public abstract String genererConstanteEntiere(int val);
	public abstract String genererVariable(int ptr);
	public abstract String genererFin();

	public abstract String genererEmpiler();
	public abstract String genererDepiler();

	public abstract String genererDifference();
	public abstract String genererSomme();
	public abstract String genererProduit();
	public abstract String genererQuotient();

	public abstract String genererEt();
	public abstract String genererOu();

	public abstract String genererEgal();
	public abstract String genererNonEgal();
	public abstract String genererInferieur();
	public abstract String genererSuperieur();
	public abstract String genererInferieurOuEgal();
	public abstract String genererSuperieurOuEgal();

	public abstract String genererCondition(Expression e, Bloc then, Bloc othw);

	public static ASMUtils getInstance() {
		/**
		 * On ne gere que MIPS pour l'instant
		*/
		if(instance == null) {
			instance = new MIPSUtils();
		}
		return instance;
	}
}