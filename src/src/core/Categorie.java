package src.core;

public enum Categorie {

	RESTAURANT, HOTEL, GOLF, SPORT, PLAGE,
	CINEMA, EQUIPEMENT_CULTURE, MEDIATHEQUE, SALLE_SPECTACLE,
	ACTION_SOCIALE, DECHETERIE, JUSTICE, POSTE, VIE_SOCIALE, SERVICE_PUBLIC,
	CHATEAU, CITE, JARDIN, PATRIMOINE, PARC_ANIMALIER,
	PARKING, MOBILITE,
	VILLEPROCHE;

	public String getRequete(Coordonnee position) {
		switch (this) {
		case RESTAURANT:
			return requete("<http://lodpaddle.univ-nantes.fr/restaurant>", position);
		case HOTEL:
			return requete("<http://lodpaddle.univ-nantes.fr/Hotel>", position);
		case GOLF:
			return requete("<http://lodpaddle.univ-nantes.fr/golf>", position);
		case SPORT:
			return requete("<http://lodpaddle.univ-nantes.fr/sport_loisir>", position);
		case PLAGE:
			return requete("<http://lodpaddle.univ-nantes.fr/plage>", position);
		case CINEMA:
			return requete("<http://lodpaddle.univ-nantes.fr/cinema>", position);
		case EQUIPEMENT_CULTURE:
			return requete("<http://lodpaddle.univ-nantes.fr/equipement_culture>", position);
		case MEDIATHEQUE:
			return requete("<http://lodpaddle.univ-nantes.fr/bibliotheque_mediatheque>", position);
		case SALLE_SPECTACLE:
			return requete("<http://lodpaddle.univ-nantes.fr/salle_spectacle>", position);
		case ACTION_SOCIALE:
			return requete("<http://lodpaddle.univ-nantes.fr/action_sociale>", position);
		case DECHETERIE:
			return requete("<http://lodpaddle.univ-nantes.fr/decheteries>", position);
		case JUSTICE:
			return requete("<http://lodpaddle.univ-nantes.fr/justice_securite>", position);
		case POSTE:
			return requete("<http://lodpaddle.univ-nantes.fr/poste>", position);
		case VIE_SOCIALE:
			return requete("<http://lodpaddle.univ-nantes.fr/vie_sociale>", position);
		case SERVICE_PUBLIC:
			return requete("<http://lodpaddle.univ-nantes.fr/service_public>", position);
		case CHATEAU:
			return requete("<http://lodpaddle.univ-nantes.fr/chateau_monument>", position);
		case CITE:
			return requete("<http://lodpaddle.univ-nantes.fr/Petites_cites_de_caractere>", position);
		case JARDIN:
			return requete("<http://lodpaddle.univ-nantes.fr/jardin_familiaux>", position);
		case PATRIMOINE:
			return requete("<http://lodpaddle.univ-nantes.fr/patrimoine>", position);
		case PARC_ANIMALIER:
			return requete("<http://lodpaddle.univ-nantes.fr/parc_animalier_theme>", position);
		case MOBILITE:
			return requete("<http://lodpaddle.univ-nantes.fr/mobilite>", position);
		case PARKING:
			return requete("<http://lodpaddle.univ-nantes.fr/parking>", position);
		case VILLEPROCHE:
			return requete2("<http://lodpaddle.univ-nantes.fr/Communes_geolocalises>", position, "10", "1");
		default:
			return null;
		}
	}

	private String requete(String graph, Coordonnee position) {
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"

						+ "SELECT ?ressource ?nom ?lat ?long("
						+ CalculDistance.selectPartString(50,
								position.latitude, position.longitude, "?lat",
								"?long")
						+ " AS ?distance)\n"
						+ "WHERE{\n"
						+ "?ressource geo:lat ?lat.\n"
						+ "?ressource geo:long?long.\n"
						+ "?ressource foaf:name ?nom.\n"
						+ "?ressource a "
						+ graph
						+ " .\n"
						+ CalculDistance.filterString(50, position.latitude,
								position.longitude, "?lat", "?long")
						+ "\n} ORDER BY ASC(?distance) LIMIT 3");
		return requete;
	}
	
	private String requete2(String graph, Coordonnee position, String nbResultat, String offset) {
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"

						+ "SELECT ?ressource ?nom ?lat ?long("
						+ CalculDistance.selectPartString(50,
								position.latitude, position.longitude, "?lat",
								"?long")
						+ " AS ?distance)\n"
						+ "WHERE{\n"
						+ "?ressource geo:lat ?lat.\n"
						+ "?ressource geo:long?long.\n"
						+ "?ressource foaf:name ?nom.\n"
						+ "?ressource a "
						+ graph
						+ " .\n"
						+ CalculDistance.filterString(50, position.latitude,
								position.longitude, "?lat", "?long")
						+ "\n} ORDER BY ASC(?distance) OFFSET "+offset+"LIMIT "+nbResultat);
		return requete;
	}
}
