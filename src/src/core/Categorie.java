package src.core;

public enum Categorie {

	RESTAURANT, HOTEL, GOLF, SPORT, PLAGE;

	public String getRequete(Coordonnee position) {
		switch (this) {
		case RESTAURANT:
			return restaurant(position);
		case HOTEL:
			return hotel(position);
		case GOLF:
			return golf(position);
		case SPORT:
			return sport(position);
		case PLAGE:
			return plage(position);
		default:
			return null;
		}
	}

	private String plage(Coordonnee position) {
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"

						+ "SELECT ?sub ?nom ("
						+ CalculDistance.selectPartString(30,
								position.latitude, position.longitude, "?lat",
								"?long")+" AS ?distance)\n"
						+ "WHERE{\n"
						+ "?sub geo:lat ?lat.\n"
						+ "?sub geo:long?long.\n"
						+ "?sub foaf:name ?nom.\n"
						+ "?sub a <http://lodpaddle.univ-nantes.fr/plage> .\n"
						+ CalculDistance.filterString(30, position.latitude,
								position.longitude, "?lat", "?long")
						+ "\n} ORDER BY ASC(?distance) LIMIT 3");
		return requete;
	}

	private String sport(Coordonnee position) {
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"

						+ "SELECT ?sub ?nom ("
						+ CalculDistance.selectPartString(30,
								position.latitude, position.longitude, "?lat",
								"?long")+" AS ?distance)\n"
						+ "WHERE{\n"
						+ "?sub geo:lat ?lat.\n"
						+ "?sub geo:long?long.\n"
						+ "?sub foaf:name ?nom.\n"
						+ "?sub a <http://lodpaddle.univ-nantes.fr/sport_loisir> .\n"
						+ CalculDistance.filterString(30, position.latitude,
								position.longitude, "?lat", "?long")
						+ "\n} ORDER BY ASC(?distance) LIMIT 3");
		return requete;
	}

	private String golf(Coordonnee position) {
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"

						+ "SELECT ?sub ?nom ("
						+ CalculDistance.selectPartString(30,
								position.latitude, position.longitude, "?lat",
								"?long")+" AS ?distance)\n"
						+ "WHERE{\n"
						+ "?sub geo:lat ?lat.\n"
						+ "?sub geo:long?long.\n"
						+ "?sub foaf:name ?nom.\n"
						+ "?sub a <http://lodpaddle.univ-nantes.fr/golf> .\n"
						+ CalculDistance.filterString(30, position.latitude,
								position.longitude, "?lat", "?long")
						+ "\n} ORDER BY ASC(?distance) LIMIT 3");
		return requete;
	}

	private String hotel(Coordonnee position) {
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"

						+ "SELECT ?sub ?nom ("
						+ CalculDistance.selectPartString(30,
								position.latitude, position.longitude, "?lat",
								"?long")+" AS ?distance)\n"
						+ "WHERE{\n"
						+ "?sub geo:lat ?lat.\n"
						+ "?sub geo:long?long.\n"
						+ "?sub foaf:name ?nom.\n"
						+ "?sub a <http://lodpaddle.univ-nantes.fr/Hotel> .\n"
						+ CalculDistance.filterString(30, position.latitude,
								position.longitude, "?lat", "?long")
						+ "\n} ORDER BY ASC(?distance) LIMIT 3");
		return requete;
	}

	private String restaurant(Coordonnee position) {
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"

						+ "SELECT ?sub ?nom ("
						+ CalculDistance.selectPartString(30,
								position.latitude, position.longitude, "?lat",
								"?long")+" AS ?distance)\n"
						+ "WHERE{\n"
						+ "?sub geo:lat ?lat.\n"
						+ "?sub geo:long?long.\n"
						+ "?sub foaf:name ?nom.\n"
						+ "?sub a <http://lodpaddle.univ-nantes.fr/restaurant> .\n"
						+ CalculDistance.filterString(30, position.latitude,
								position.longitude, "?lat", "?long")
						+ "\n} ORDER BY ASC(?distance) LIMIT 3");
		return requete;
	}
}
