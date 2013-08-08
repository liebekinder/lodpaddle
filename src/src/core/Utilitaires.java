package src.core;

public class Utilitaires {

	
	public static String nettoieRessource(String string, String autre) {
		String retour = nettoieRessource(string);
		if (autre.equals("dep") && retour.contains("_")) {
			return retour.substring(0, retour.indexOf('_'));
		}
		return retour;
	}

	public static String nettoieRessource(String string) {
		// on suppose qu'une string commençant par " est malformée, ie ressource
		// ou littéral avec type
		if (string.split("@").length > 1) {
			return string.split("@")[0];
		}
		if (string.split("http://fr.dbpedia.org/resource/").length > 1) {
//			System.out.println("fr needed!!!   " +string);
			return string.split("http://fr.dbpedia.org/resource/")[1];
		}
		if (string.split("http://dbpedia.org/resource/").length > 1) {
//			System.out.println("normal needed!!!   " +string);
			return string.substring(28);
		}
		if (string.contains("^^http")) {
			int pos = string.indexOf("^^http");
			return string.substring(0, pos);
		}
		return string;
	}
	
	public static String nettoieRessourceLeger(String string) {
		if (string.contains("^^http")) {
			int pos = string.indexOf("^^http");
			return string.substring(0, pos);
		}
		return string;
	}
	
	public static String nettoieAffichage(String s){
		String retour = nettoieRessourceLeger(s);
		retour = retour.substring(0, 1).toUpperCase()+retour.substring(1).toLowerCase();
		return retour;
	}
	
}
