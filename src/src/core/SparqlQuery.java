package src.core;

import java.util.HashMap;
import java.util.Iterator;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;


public class SparqlQuery {

	private final static boolean isBehindProxy = true;
	
	private SparqlQuery(){		
	}

	public static Resultat requete(String requete, EndPoint serveur) {
		Resultat resultat;
		
		if(requete == null) return null;
		
		// la gestion du proxy n'est necessaire uniquement pour le dev. En prod
		// il est systématiquement à valider
		if (isBehindProxy) {

			String proxyHost = "193.52.105.147";
			String proxyPort = "3128";
//			String proxyHost = "cache.etu.univ-nantes.fr";
//			String proxyPort = "3128";

			System.setProperty("proxySet", "true");
			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);
		}

		com.hp.hpl.jena.query.Query query = QueryFactory.create(requete);

		// on switche en fonction du serveur d'exécution. Le defaut est notre
		// serveur, mais il ne sera jamais utilisé
		QueryExecution qexec;
		switch (serveur) {
		case Fac:
			qexec = QueryExecutionFactory.sparqlService(
					"http://lodpaddle.univ-nantes.fr/sparql", query);
			break;
		case DBpedia:
			qexec = QueryExecutionFactory.sparqlService(
					"http://fr.dbpedia.org/sparql", query);
			break;
		case Insee:
			qexec = QueryExecutionFactory.sparqlService(
					"http://rdf.insee.fr/sparql", query);
			break;
		default:
			qexec = QueryExecutionFactory.sparqlService(
					"http://lodpaddle.univ-nantes.fr/sparql", query);
			break;
		}

		try {
			ResultSet results = qexec.execSelect();
			resultat = new Resultat();
			for (; results.hasNext();) {
				// affiche chaque prochaine solution
				QuerySolution uneLigne = results.nextSolution();
				Iterator<String> variables = uneLigne.varNames();
				HashMap<String, String> mapUneSolution = new HashMap<String, String>();
				while(variables.hasNext()){
					String variable = variables.next();
					mapUneSolution.put(variable, uneLigne.get(variable).toString());
				}
				resultat.ajoutResultat(mapUneSolution);
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			return null;
		} finally {
			qexec.close();
		}

		return resultat;
	}
}
