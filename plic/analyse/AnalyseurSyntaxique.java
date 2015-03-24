package plic.analyse;

import plic.analyse.AnalyseurLexical;
import plic.analyse.Bloc;

import plic.analyse.expressions.*;
import plic.analyse.expressions.binaires.*;
import plic.analyse.instructions.*;

import plic.definitions.Lexique;

import plic.erreurs.ErreurSyntaxique;
import plic.erreurs.FichierInexistant;

import java.util.Arrays;

/**
 * @author Renan Strauss
 * L'analyseur syntaxique
*/
public class AnalyseurSyntaxique {
	private AnalyseurLexical lex;
	private String unite;

	public AnalyseurSyntaxique() {
		this.lex = null;
	}

	public Bloc analyser(String nomFichier) throws ErreurSyntaxique {
		try {
			this.lex = new AnalyseurLexical(nomFichier);
		} catch(FichierInexistant e) {
			e.printStackTrace();
		}

		this.unite = this.lex.next();
		Bloc blk = this.analyseProgramme();

		if(lex.hasNext()) {
			throw new ErreurSyntaxique("Le fichier ne se termine pas correctement");
		} else {
			//System.out.println("Analyse syntaxique reussie !");
		}

		return blk;
	}

	private Bloc analyseProgramme() throws ErreurSyntaxique {
		this.check(Lexique.PROGRAMME);
		this.unite = this.lex.next();

		return this.analyseBloc();
	}

	private Bloc analyseBloc() throws ErreurSyntaxique {
		Bloc blk = new Bloc();

		/**
		 * Debut du bloc
		*/
		this.check(Lexique.DEBUT_BLOC);
		/**
		 * Il est contraignant de devoir refaire des declarations
		 * dans les blocs conditionnels ou iteratifs par exemple.
		 * Pour l'instant, on choisit donc d'autoriser l'absence
		 * de déclartion.
		 *
		 * Attention !
		 * Les déclarations doivent toujours se faire avant toute
		 * instruction, sinon une erreur syntaxique sera levée
		*/
		while(this.estUnType(this.unite)) {
			this.analyseDeclaration();
		}
		do {
			blk.add(this.analyseInstruction());
		} while(!this.unite.equals(Lexique.FIN_BLOC));

		this.check(Lexique.FIN_BLOC);
		/**
		 * Fin du bloc, on le retourne
		*/

		return blk;
	}

	private void analyseDeclaration() throws ErreurSyntaxique {
		String idf;
		String type = this.analyseType();
		while(!this.unite.equals(Lexique.SEMICOLON)) {
			idf = this.analyseIdentificateur();
			TableSymboles.getInstance().put(idf, type);
		}

		// Skip ;
		this.check(Lexique.SEMICOLON);
	}

	private Ecrire analyseEcrire() throws ErreurSyntaxique {
		this.check("ecrire");

		String idf = this.analyseIdentificateur();
		return new Ecrire(idf);
	}

	/**
	 * EXPRESSION -> TERME | TERME { +|- TERME }+
	*/
	private Expression analyseExpression() throws ErreurSyntaxique {
		Expression expr = this.analyseTerme();
		while(this.lex.estUnOperateur(this.unite) && 
			  !(this.unite.equals(Lexique.OP_MULT) || this.unite.equals(Lexique.OP_DIV))) {
			switch(this.unite) {
				case Lexique.OP_ADD:
					this.unite = this.lex.next();
					expr = new Somme(expr, this.analyseTerme());
					break;
				case Lexique.OP_SUB:
					this.unite = this.lex.next();
					expr = new Difference(expr, this.analyseTerme());
					break;
				case Lexique.OP_AND:
					this.unite = this.lex.next();
					expr = new Et(expr, this.analyseTerme());
					break;
				case Lexique.OP_OR:
					this.unite = this.lex.next();
					expr = new Ou(expr, this.analyseTerme());
					break;
				case Lexique.OP_EQ:
				case Lexique.OP_LT:
				case Lexique.OP_GT:
				case Lexique.OP_LOET:
				case Lexique.OP_GOET:
					String op = this.unite;
					this.unite = this.lex.next();
					expr = new Comparaison(expr, op, this.analyseTerme());
					break;
			}
		}

		return expr;
	}

	/**
	 * TERME -> FACTEUR | FACTEUR { *|/ FACTEUR }+
	*/
	private Expression analyseTerme() throws ErreurSyntaxique {
		Expression expr = this.analyseFacteur();
		while(this.lex.estUnOperateur(this.unite) && 
			  !(this.unite.equals(Lexique.OP_ADD) || this.unite.equals(Lexique.OP_SUB))) {
			switch(this.unite) {
				case Lexique.OP_MULT:
					this.unite = this.lex.next();
					expr = new Produit(expr, this.analyseFacteur());
					break;
				case Lexique.OP_DIV:
					this.unite = this.lex.next();
					expr = new Quotient(expr, this.analyseFacteur());
					break;
				case Lexique.OP_AND:
					this.unite = this.lex.next();
					expr = new Et(expr, this.analyseFacteur());
					break;
				case Lexique.OP_OR:
					this.unite = this.lex.next();
					expr = new Ou(expr, this.analyseFacteur());
					break;
				case Lexique.OP_EQ:
				case Lexique.OP_LT:
				case Lexique.OP_GT:
				case Lexique.OP_LOET:
				case Lexique.OP_GOET:
					String op = this.unite;
					this.unite = this.lex.next();
					expr = new Comparaison(expr, op, this.analyseTerme());
					break;
			}
		}

		return expr;
	}

	/**
	 * FACTEUR -> idf | const entiere | ( EXPRESSION )
	*/
	private Expression analyseFacteur() throws ErreurSyntaxique {
		Expression expr = null;
		if(this.lex.estUnIdentificateur(this.unite)) {
			expr = new Variable(this.analyseIdentificateur());
		} else if(this.lex.estUneConstanteEntiere(this.unite)) {
			expr = new ConstanteEntiere(this.analyseValeur());
		} else if(this.lex.estUneConstanteBooleenne(this.unite)) {
			expr = ConstanteBooleenne.getByValue(this.analyseConstanteBooleenne());
		} else {
			this.check("(");
			expr = this.analyseExpression();
			this.check(")");
		}

		return expr;
	}

	private Affectation analyseAffectation() throws ErreurSyntaxique {
		String idf = this.analyseIdentificateur();
		this.check(Lexique.OP_ASSIGN);

		return new Affectation(idf, this.analyseExpression());
	}
 
	private Instruction analyseInstruction() throws ErreurSyntaxique {
		Instruction instr = null;
		switch(this.unite) {
			case Lexique.ECRIRE:
				/**
			 	* C'est ecrire
				*/
				instr = this.analyseEcrire();
			break;
			case Lexique.SI:
				/**
				 * C'est une conditionnelle
				*/
				instr = this.analyseCondition();
				break;
			case Lexique.TANT_QUE:
				/**
				 * C'est une boucle while
				*/
				instr = this.analyseTantQue();
				break;
			case Lexique.POUR:
				/**
				 * C'est une boucle for
				*/
				instr = this.analysePour();
				break;
			default:
				/**
				 * C'est une affectation
				*/
				instr = this.analyseAffectation();
		}

		// Skip ;
		this.check(Lexique.SEMICOLON);

		return instr;
	}

	private TantQue analyseTantQue() throws ErreurSyntaxique {
		this.check(Lexique.TANT_QUE);
		this.check("(");
		Expression expr = this.analyseExpression();
		this.check(")");
		this.check(Lexique.REPETER);
		Bloc blk = this.analyseBloc();

		return new TantQue(expr, blk);
	}

	private Pour analysePour() throws ErreurSyntaxique {
		this.check(Lexique.POUR);
		Variable v = new Variable(this.analyseIdentificateur());

		this.check(Lexique.DANS);

		Expression deb = this.analyseExpression();
		this.check("..");
		Expression fin = this.analyseExpression();

		this.check(Lexique.REPETER);

		Bloc blk = this.analyseBloc();

		return new Pour(v, deb, fin, blk);
	}

	private Condition analyseCondition() throws ErreurSyntaxique {
		this.check(Lexique.SI);
		this.check("(");
		Expression expr = this.analyseExpression();
		this.check(")");


		this.check(Lexique.ALORS);

		Bloc then = this.analyseBloc();
		Bloc othw = null;
		if(this.unite.equals(Lexique.SINON)) {
			this.unite = this.lex.next();
			othw = this.analyseBloc();
		}

		this.check(Lexique.FINSI);

		return new Condition(expr, then, othw);
	}

	private String analyseConstanteBooleenne() throws ErreurSyntaxique {
		String val = null;
		if(this.lex.estUneConstanteBooleenne(this.unite)) {
			val = this.unite;
			this.unite = this.lex.next();
		} else {
			throw new ErreurSyntaxique("Constante booleenne invalide : " + this.unite);
		}

		return val;
	}

	private String analyseIdentificateur() throws ErreurSyntaxique {
		String idf = null;
		if(this.lex.estUnIdentificateur(this.unite)) {
			idf = this.unite;
			this.unite = this.lex.next();
		} else {
			throw new ErreurSyntaxique("Identificateur inconnu : " + this.unite);
		}

		return idf;
	}

	private String analyseType() throws ErreurSyntaxique {
		String type = null;
		if(this.estUnType(this.unite)) {
			type = this.unite;
			this.unite = this.lex.next();
		} else {
			throw new ErreurSyntaxique("Type inconnu : " + this.unite);
		}

		return type;
	}

	private int analyseValeur() throws ErreurSyntaxique {
		int val = 0;
		if(this.lex.estUneConstanteEntiere(this.unite)) {
			val = Integer.parseInt(this.unite);
			this.unite = this.lex.next();
		} else {
			throw new ErreurSyntaxique("Valeur incompatible : " + this.unite);
		}

		return val;
	}

	private void check(String word) {
		if(this.unite.equals(word)) {
			if(this.lex.hasNext()) {
				this.unite = this.lex.next();
			}
		} else {
			throw new ErreurSyntaxique("Expected : " + word + ", got :" + this.unite);
		}
	}

	private boolean estUnType(String word) {
		return Arrays.asList(Lexique.TYPES).contains(word);
	}
}