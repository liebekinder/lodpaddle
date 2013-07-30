package src.Servlets;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 * Servlet implementation class AjaxListName
 */
@WebServlet("/AjaxListName")
public class AjaxListName extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public final boolean isBehindProxy = true;

	// PREFIX foaf:<http://xmlns.com/foaf/0.1/>
	//
	// SELECT ?nomVille
	// FROM <http://lodpaddle.univ-nantes.fr/Communes_geolocalises>
	// WHERE {
	// ?commune owl:sameAs ?nomVille .
	// FILTER regex(?nomVille,"^sain", "i")
	// }
	// ORDER BY ?nomVille LIMIT 10

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxListName() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getListName(request.getParameter("term")));
	}

	private String getListName(String filtre) {

		// Data from INSEE
		// String queryString =
		// "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
		// + "PREFIX idemo:<http://rdf.insee.fr/def/demo#>"
		// + "PREFIX igeo:<http://rdf.insee.fr/def/geo#>"
		// + "SELECT ?nom ?code WHERE {"
		// + "  ?commune rdf:type igeo:Commune ."
		// + "  ?commune igeo:codeCommune ?code ."
		// + "  ?commune igeo:nom ?nom ."
		// + "  ?commune igeo:subdivisionDe ?arrondissement ."
		// + "  ?arrondissement igeo:subdivisionDe ?dep ."
		// + "  ?dep igeo:subdivisionDe ?region ."
		// + "  ?region igeo:codeRegion \"52\" ."
		// + "FILTER regex(?nom,\"^"
		// + filtre
		// + "\", \"i\") "
		// + "}"
		// + "ORDER BY ?nom" + " LIMIT 10 ";

		// Data from our server
		String queryString = "PREFIX foaf:<http://xmlns.com/foaf/0.1/> "
				+ "PREFIX dbpprop: <http://dbpedia.org/property/> "
				+ "SELECT ( STR(?nomS) AS ?nom ) ( STR(?codeS) AS ?code) "
				+ "FROM <http://lodpaddle.univ-nantes.fr/Communes_geolocalises> "
				+ "WHERE {" + "?commune foaf:name ?nomS . "
				+ "?commune dbpprop:insee ?codeS ." + "FILTER regex(?nomS,\"^"
				+ filtre + "\", \"i\") " + "}" + "ORDER BY ?nom"
				+ "  LIMIT 10 ";

		 System.out.println(queryString);
		if (isBehindProxy) {

			String proxyHost = "193.52.105.147";
			String proxyPort = "3128";
			// String proxyHost = "cache.etu.univ-nantes.fr";
			// String proxyPort = "3128";

			System.setProperty("proxySet", "true");
			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);
		}

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);

		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://lodpaddle.univ-nantes.fr/sparql", query);

		// QueryExecution qexec = QueryExecutionFactory.sparqlService(
		// "http://rdf.insee.fr/sparql", query);

		//
		// QueryExecution qexec = QueryExecutionFactory.sparqlService(
		// "http://localhost:8890/sparql", query);

		String retour = "[";
		try {
			ResultSet results = qexec.execSelect();

			for (; results.hasNext();) {
				// affiche chaque prochaine solution
				QuerySolution soln = results.nextSolution();
				RDFNode x = soln.get("nom");
				RDFNode y = soln.get("code");
				retour += "\"" + x.toString() + " - "
						+ y.toString().substring(0, 2) + "\",";
			}
		} catch (Exception e) {
			// System.out.println("probleme avec le retour de la requete :/");
			if (retour.length() <= 2)
				return "[\""+query+" - " + e.getLocalizedMessage() + "\"]";
			else
				return retour.substring(0, retour.length() - 1) + "]";
		} finally {
			qexec.close();

		}

		// System.out.println(retour);

		if (retour.length() <= 2)
			return "[\"aucune entrée\"]";
		else
			return retour.substring(0, retour.length() - 1) + "]";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
