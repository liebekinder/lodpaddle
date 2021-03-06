package src.core;

import java.util.HashMap;
import java.util.Iterator;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;


public class SparqlQuery {

	private final static boolean isBehindProxy = true;
//	private final static boolean isBehindProxy = false;
	
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
//		QueryExecution qexec = null;
		QueryEngineHTTP objectToExec;
		switch (serveur) {
		case Fac:
			objectToExec = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(
					"http://lodpaddle.univ-nantes.fr/sparql", query);
			break;
		case DBpedia:
			objectToExec = QueryExecutionFactory.createServiceRequest("http://fr.dbpedia.org/sparql",query);
			objectToExec.addParam("timeout","3000");
			
//			qexec = QueryExecutionFactory.sparqlService(
//					"http://fr.dbpedia.org/sparql", query);
			break;
		case Insee:
			objectToExec = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(
					"http://rdf.insee.fr/sparql", query);
			break;
		case Local:
			objectToExec = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(
					"http://localhost:8890/sparql", query);
			break;
		default:
			objectToExec = (QueryEngineHTTP) QueryExecutionFactory.sparqlService(
					"http://lodpaddle.univ-nantes.fr/sparql", query);
			break;
		}

		try {
			
			ResultSet results=objectToExec.execSelect();
			
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
			System.err.println("Le Sparql Endpoint n'est pas accessible!" + e.getLocalizedMessage());
			return null;
		} finally {
//			if(qexec != null) qexec.close();
			if(objectToExec != null) objectToExec.close();
		}

		return resultat;
	}
	
	public static ResultSet requeteResultSet(String requete, EndPoint serveur) {
		ResultSet results;
		
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
		case Local:
			qexec = QueryExecutionFactory.sparqlService(
					"localhost:8890/sparql", query);
			break;
		default:
			qexec = QueryExecutionFactory.sparqlService(
					"http://lodpaddle.univ-nantes.fr/sparql", query);
			break;
		}

		try {
			results = qexec.execSelect();
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			return null;
		} finally {
			qexec.close();
		}
		return results;
	}
}
