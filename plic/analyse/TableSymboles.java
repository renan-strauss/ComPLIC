package plic.analyse;

import plic.erreurs.ErreurSemantique;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Renan Strauss
 * Stocke les identificateurs, qui
 * sont associés à leur symbole
 * (cf. class Symbole)
*/
public class TableSymboles {
	/**
	 * Singleton
	*/
	private static TableSymboles instance;

	/**
	 * Hash pour stocker les symboles
	 * (idf => symbole)
	*/
	private HashMap<String, Symbole> symboles;

	private TableSymboles() {
		this.symboles = new HashMap<String, Symbole>();
	}

	public void put(String key, String val) {
		if(!this.hasKey(key)) {
			this.symboles.put(key, new Symbole(val, this.size()));
		} else {
			throw new ErreurSemantique("Double declaration");
		}
	}

	public Symbole get(String key) {
		return this.symboles.get(key);
	}

	public boolean hasKey(String key) {
		return this.symboles.containsKey(key);
	}

	public int size() {
		return this.symboles.size();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Symboles = {\n");
		for(Map.Entry<String, Symbole> sym : this.symboles.entrySet()) {
			sb.append("\t" + sym.getKey() + " => " + sym.getValue().getType() + ", " + sym.getValue().getAddr() + "\n");
		}
		sb.append("}\n");

		return sb.toString();
	}

	public static TableSymboles getInstance() {
		if(instance == null) {
			instance = new TableSymboles();
		}

		return instance;
	}

	/**
	 * Cette classe represente un symbole,
	 * caracterise par son type (entier, booleen)
	 * et son addresse en memoire (le decalage)
	*/
	public static class Symbole {
		private String type;
		private int addr;

		public Symbole(String type, int addr) {
			this.type = type;
			this.addr = addr;
		}

		public String getType() {
			return this.type;
		}

		public int getAddr() {
			return this.addr;
		}
	}
}