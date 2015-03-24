package plic.analyse;

import plic.analyse.expressions.ConstanteEntiere;
import plic.analyse.expressions.Variable;
import plic.analyse.expressions.binaires.Somme;

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
		int id = etiquetteId++;

		StringBuffer condition = new StringBuffer(e.generer());
		condition.append("\n\tbne $v0, 1, ELSE" + id + "\n");
		condition.append(then.generer());
		condition.append("\n\tj ENDIF" + id);
		condition.append("\nELSE" + id + ":\n");
		condition.append((othw != null) ? othw.generer() : "");
		condition.append("\nENDIF" + id + ":\n");

		
		return condition.toString();
	}

	@Override
	public String genererTantQue(Expression e, Bloc rep) {
		int id = etiquetteId++;

		StringBuffer boucle = new StringBuffer(e.generer());
		boucle.append("\nLOOP" + id + ":\n");
		boucle.append("\tbne $v0, 1, EXIT" + id + "\n");
		boucle.append(rep.generer());
		boucle.append(e.generer());
		boucle.append("\n\tj LOOP" + id + "\n");
		boucle.append("\nEXIT" + id + ":\n");

		return boucle.toString();
	}

	@Override
	public String genererPour(Variable v, Expression deb, Expression fin, Bloc rep) {
		int id = etiquetteId++;

		/**
		 * On initialise v a la valeur de deb
		*/
		int vPtr = TableSymboles.getInstance().get(v.getIdf()).getAddr();
		String init = this.genererAffectation(deb, vPtr);

		StringBuffer pour = new StringBuffer(init);
		// on empile la valeur de v avant le debut de la boucle
		pour.append(this.genererEmpiler());
		pour.append("\nLOOP" + id + ":\n");
		// on recupere la valeur de fin
		pour.append(fin.generer());
		// on depile la valeur de v
		pour.append(this.genererDepiler());
		// on met $v0 a 1 ssi v <= fin
		pour.append("\tsle $v0, $t2, $v0\n");
		// si $v0 est a 1, on continue, sinon on sort
		pour.append("\tbne $v0, 1, EXIT" + id + "\n");
		// le bloc a repeter
		pour.append(rep.generer());
		// incrementation de v
		pour.append(this.genererAffectation(new Somme(v, new ConstanteEntiere(1)), vPtr));
		// puis on empile de nouveau v
		pour.append(this.genererEmpiler());
		// fin de la boucle
		pour.append("\tj LOOP" + id + "\n");
		pour.append("EXIT" + id + ":\n");

		return pour.toString();
	}

	/**
	 * C'est le meme principe partout:
	 * l'operande gauche est dans $t2, la droite dans $v0.
	 * Apres l'instruction, $v0 = $t2 op $v0
	*/
	@Override
	public String genererEt() {
		return "\tand $v0, $t2, $v0\n";
	}

	@Override
	public String genererOu() {
		return "\tor $v0, $t2, $v0\n";
	}

	@Override
	public String genererEgal() {
		return "\tseq $v0, $t2, $v0\n";
	}

	@Override
	public String genererNonEgal() {
		return "\tsne $v0, $t2, $v0\n";
	}

	@Override
	public String genererInferieur() {
		return "\tslt $v0, $t2, $v0\n";
	}

	@Override
	public String genererSuperieur() {
		return "\tsgt $v0, $t2, $v0\n";
	}

	@Override
	public String genererInferieurOuEgal() {
		return "\tsle $v0, $t2, $v0\n";
	}

	@Override
	public String genererSuperieurOuEgal() {
		return "\tsge $v0, $t2, $v0\n";
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

	// FIXME : 4 devrait etre calcule theoriquement
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