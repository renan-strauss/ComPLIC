package plic.analyse;

public class MIPSUtils extends ASMUtils {
	private static final int DECALAGE = -4;

	private int etiquetteId = 0;

	@Override
	public String generer(Bloc blk) {
		return this.genererEntete() + blk.generer() + this.genererFin();
	}

	@Override
	public String genererEntete() {
		int nbSym = TableSymboles.getInstance().size();
		String data = ".data\nstr:\n\t.asciiz \"\\n\"\n";

		return data + ".text\nmain:\n\tmove $s7, $sp\n\taddi $sp, $sp, " + (DECALAGE * nbSym) + "\n";
	}

	@Override
	public String genererFin() {
		return "end:\n\tli $v0, 10\n\tsyscall";
	}

	/**
	 * Genere une affectation du type
	 * expr = val ptr
	*/
	@Override
	public String genererAffectation(Expression expr, int ptr) {
		return expr.generer() + "\n\tsw $v0, " + (DECALAGE * ptr) + "($s7)\n";
	}

	/**
	 * Genere l'affiche de la variable
	 * pointee par ptr en memoire,
	 * avec un retour a la ligne
	*/
	@Override
	public String genererEcrire(int ptr) {
		String ln = "\tli $v0, 4\n\tla $a0, str\n\tsyscall\n";
		
		return "\tlw $a0, " + (DECALAGE * ptr) + "($s7)\n\tli $v0, 1\n\tsyscall\n" + ln;
	}


	@Override
	public String genererCondition(Expression e, Bloc then, Bloc othw) {
		String expr = e.generer();

		int id = etiquetteId++;

		StringBuffer condition = new StringBuffer(expr);
		condition.append("\n\tbne $v0, 1, ELSE" + id + "\n");
		condition.append(then.generer());
		condition.append("\n\tj ENDIF" + id);
		condition.append("\nELSE" + id + ":\n");
		condition.append((othw != null) ? othw.generer() : "");
		condition.append("\nENDIF" + id + ":\n");

		
		return condition.toString();
	}

	public String genererEt() {
		return "\tand $v0, $t2, $v0\n";
	}

	public String genererOu() {
		return "\tor $v0, $t2, $v0\n";
	}

	/**
	 * Charge la constante entiere
	 * val dans $v0
	*/
	@Override
	public String genererConstanteEntiere(int val) {
		return "\tli $v0, " + String.valueOf(val) + "\n";
	}

	/**
	 * Charge la valeur de la variable
	 * dont la valeur est a l'adresse ptr
	 * dans $v0
	*/
	@Override
	public String genererVariable(int ptr) {
		return "\tlw $v0, " + (DECALAGE * ptr) + "($s7)\n";
	}

	/**
	 * Empile $v0
	*/
	@Override
	public String genererEmpiler() {
		return "\tsw $v0, 0($sp)\n\taddi $sp, $sp, -4\n";
	}

	@Override
	public String genererDepiler() {
		return "\tlw $t2, 4($sp)\n\taddi $sp, $sp, 4\n";
	}

	@Override
	public String genererSomme() {
		return "\tadd $v0, $t2, $v0\n";
	}

	@Override
	public String genererDifference() {
		return "\tsub $v0, $t2, $v0\n";
	}

	@Override
	public String genererProduit() {
		return "\tmult $t2, $v0\n\tmflo $v0\n";
	}

	/**
	 * Ici on verifie en plus que $v0 != 0
	*/
	@Override
	public String genererQuotient() {
		return "\tbeqz $v0, end\n\tdiv $t2, $v0\n\tmflo $v0\n";
	}
}