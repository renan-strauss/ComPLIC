package plic.analyse;

import plic.erreurs.FichierInexistant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Renan Strauss
 * L'analyseur lexical utilise la
 * classe Scanner
*/
public class AnalyseurLexical {
	/**
	 * Le Scanner associé
	*/
	public Scanner scan;

	public static final String PROGRAMME  = "programme";
	public static final String DEBUT_BLOC = "{";
	public static final String FIN_BLOC   = "}";

	public static final String ECRIRE = "ecrire";

	public static final String SI = "si";
	public static final String ALORS = "alors";
	public static final String SINON = "sinon";
	public static final String FINSI = "finsi";

	public static final String TYPE_ENTIER = "entier";
	public static final String TYPE_BOOLEEN = "booleen";
	public static final String[] TYPES = {
		TYPE_ENTIER, TYPE_BOOLEEN
	};

	public static final String VRAI = "vrai";
	public static final String FAUX = "faux";

	public static final String OP_ASSIGN = ":=";
	public static final String OP_ADD = "+";
	public static final String OP_SUB = "-";
	public static final String OP_DIV = "/";
	public static final String OP_MULT = "*";
	public static final String OP_AND = "et";
	public static final String OP_OR = "ou";

	public static final String SEMICOLON = ";";

	public static final String[] MOTS_CLES = {
		/**
		 * Les mots cles
		*/
		PROGRAMME, DEBUT_BLOC, FIN_BLOC,
		ECRIRE, SI, ALORS, SINON, FINSI,

		/**
		 * Les operateurs
		*/
		OP_ASSIGN, OP_ADD, OP_SUB, OP_DIV, OP_MULT,
		OP_AND, OP_OR,

		/**
		 * Les types
		*/
		TYPE_ENTIER, TYPE_BOOLEEN,
		// Pour les booleens
		VRAI, FAUX
	};

	public static final String EOF = "EOF";

	/**
	 * Constructeur
	 * @param le nom du fichier à analyser
	*/
	public AnalyseurLexical(String nomFichier) throws FichierInexistant{
		try {
			this.scan = new Scanner(new File(nomFichier).toPath());
		} catch(IOException e) {
			this.scan = null;
			throw new FichierInexistant(nomFichier);
		}
	}

	/**
	 * @return true si et seulement si next() != EOF
	*/
	public boolean hasNext() {
		return this.scan.hasNext();
	}

	/**
	 * @return le prochain bloc lexical lu
	*/
	public String next() {
		String next = this.scan.next();
		
		/*
		 * Commentaires multi-lignes
		*/
		if(next.startsWith("/*")) {
			while(!next.endsWith("*/")) {
				if(this.hasNext()) {
					next = this.scan.next();
				} else {
					break;
				}
			}
		}
		/**
		 * Commentaires en une seule ligne
		*/
		else if(next.startsWith("//")) {
			this.scan.nextLine();
		}
		/**
		 * Pas de commentaire(s)
		*/
		else {
			return next;
		}

		return this.hasNext() ? this.next() : next;
	}

	/**
	 * @param word Le mot a tester
	 * @return Vrai ssi word est un identificateur
	*/
	public boolean estUnIdentificateur(String word) {
		return word.matches("[a-zA-Z]+") && !Arrays.asList(MOTS_CLES).contains(word);
	}

	/**
	 * @param word Le mot a tester
	 * @return Vrai ssi word est une contante entiere
	*/
	public boolean estUneConstanteEntiere(String word) {
		boolean ok = true;
		try {
			Integer.parseInt(word);
		} catch(NumberFormatException e) {
			ok = false;
		}

		return ok;
	}

	public boolean estUneConstanteBooleenne(String word) {
		return word.equals(VRAI) || word.equals(FAUX);
	}
}