package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import src.core.EndPoint;
import src.core.Resultat;
import src.core.SparqlQuery;
import src.core.Utilitaires;

public class JeuPDLL extends Jeu {

	public JeuPDLL() {
		super();
		type = 3;
		ext = "PDLL";
	}

	public boolean trouveVilles() {
		String requete = new String(
				""
						+ "PREFIX sc: <http://schema.org/>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> \n"
						+ "SELECT ?nom ?insee ?lon ?lat\n"
						+ "FROM <http://lodpaddle.univ-nantes.fr/Communes_geolocalises>\n"
						+ "WHERE{ \n" + "?a foaf:name ?nom . \n"
						+ "?a dbpprop:insee ?insee . \n"
						+ "?a geo:lat ?lat . \n" + "?a geo:long ?lon . \n"
						+ "}");

		Resultat results = SparqlQuery.requete(requete, EndPoint.Fac);
		if (results != null) {
			Random rand = new Random();
			List<Integer> ids = new ArrayList<Integer>();
			int cpt = 0;
			while (cpt < nbCycle) {
				int temp = rand.nextInt(results.taille());
				if (!ids.contains(temp)) {
					ids.add(temp);
					cpt++;
				}
			}

			for (int i : ids) {
				HashMap<String, String> result = results.at(i);

				String nom = Utilitaires
						.nettoieRessourceLeger((result.get("nom") != null && !result
								.get("nom").isEmpty()) ? result.get("nom")
								: "Nom inconnu!");
				String insee = Utilitaires
						.nettoieRessourceLeger((result.get("insee") != null && !result
								.get("insee").isEmpty()) ? result.get("insee")
								: "insee inconnu!");
				double lon = (result.get("lon") != null && !result.get("lon")
						.isEmpty()) ? Double.valueOf(Utilitaires
						.nettoieRessourceLeger(result.get("lon"))) : 0;
				double lat = (result.get("lat") != null && !result.get("lat")
						.isEmpty()) ? Double.valueOf(Utilitaires
						.nettoieRessourceLeger(result.get("lat"))) : 0;
				villes.add(new Ville(nom, insee, lon, lat));

			}
			return true;
		} else {
			return false;
		}
	}

	public int getType() {
		return type;
	}
}
