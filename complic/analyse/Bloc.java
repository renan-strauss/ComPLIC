package complic.analyse;

import java.util.ArrayList;

import complic.erreurs.ErreurSemantique;

/**
 * @author Renan Strauss
 * Cette classe représente un bloc,
 * qui est une liste d'instructions
*/
public class Bloc extends ArrayList<Instruction> {
	/**
	 * Analyse semantique
	*/
	public void verifier() throws ErreurSemantique {
		/**
		 * Chaque instruction verifie
		 * sa propre integrite
		*/
		for(Instruction instr : this) {
			instr.verifier();
		}
	}

	/** 
	 * Generation du code assembleur
	 * MIPS
	*/
	public String generer() {
		StringBuffer asm = new StringBuffer();
		ASMUtils utils = ASMUtils.getInstance();

		for(Instruction instr : this) {
			asm.append(instr.generer());
		}

		return asm.toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(Instruction instr : this) {
			sb.append(instr.toString());
		}

		return sb.toString();
	}
}