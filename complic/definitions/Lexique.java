package complic.definitions;

public class Lexique {
	public static final String PROGRAMME         = "programme";
	public static final String DEBUT_BLOC        = "{";
	public static final String DEBUT_BLOC_INLINE = "=>";
	public static final String FIN_BLOC          = "}";

	public static final String ECRIRE = "ecrire";

	public static final String SI    = "si";
	public static final String ALORS = "alors";
	public static final String SINON = "sinon";

	public static final String TANT_QUE = "tantque";
	public static final String REPETER  = "repeter";

	public static final String POUR = "pour";
	public static final String DANS = "dans";

	public static final String[] TYPES = {
		Types.ENTIER, Types.BOOLEEN
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

	public static final String OP_EQ = "=";
	public static final String OP_NEQ = "!=";
	public static final String OP_LT = "<";
	public static final String OP_GT = ">";
	public static final String OP_LOET = "<=";
	public static final String OP_GOET = ">=";

	public static final String SEMICOLON = ";";

	public static final String[] OPERATEURS = {
		OP_ADD, OP_SUB, OP_DIV, OP_MULT,
		OP_AND, OP_OR, OP_EQ, OP_NEQ, OP_LT,
		OP_GT, OP_LOET, OP_GOET
	};

	public static final String[] MOTS_CLES = {
		/**
		 * Les mots cles
		*/
		PROGRAMME, DEBUT_BLOC, FIN_BLOC,
		ECRIRE, SI, ALORS, SINON,

		/**
		 * Les operateurs
		*/
		OP_ASSIGN, OP_ADD, OP_SUB, OP_DIV, OP_MULT,
		OP_AND, OP_OR, OP_EQ,

		/**
		 * Les types
		*/
		Types.ENTIER, Types.BOOLEEN,
		// Pour les booleens
		VRAI, FAUX
	};

	public static final String EOF = "EOF";
}