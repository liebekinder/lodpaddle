package src.Servlets;

import java.io.IOException;

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


		String proxyHost = "193.52.105.147";
		String proxyPort = "3128";

		String queryString = "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX idemo:<http://rdf.insee.fr/def/demo#>"
				+ "PREFIX igeo:<http://rdf.insee.fr/def/geo#>"
				+ "SELECT ?nomVille WHERE {"
				+ "?region igeo:subdivisionDirecte ?departement ."
				+ "?region igeo:codeRegion \"52\" ."
				+ "?departement igeo:nom ?nom ."
				+ "?departement igeo:subdivisionDirecte ?subdiv."
				+ "?commune igeo:subdivisionDe ?subdiv ."
				+ "?commune a igeo:Commune ."
				+ "?commune igeo:nom ?nomVille ."
				+ "FILTER regex(?nomVille,\"^"
				+ filtre
				+ "\", \"i\") "
				+ "}"
				+ "ORDER BY ?nomVille"
				+ "  LIMIT 10 ";
		System.out.println(queryString);

		System.setProperty("proxySet", "true");
		System.setProperty("http.proxyHost", proxyHost);
		System.setProperty("http.proxyPort", proxyPort);

		com.hp.hpl.jena.query.Query query = QueryFactory.create(queryString);

		QueryExecution qexec = QueryExecutionFactory.sparqlService(
				"http://rdf.insee.fr/sparql", query);

		String retour = "[";
		try {
			ResultSet results = qexec.execSelect();

			for (; results.hasNext();) {
				// affiche chaque prochaine solution
				QuerySolution soln = results.nextSolution();
				RDFNode x = soln.get("nomVille");
				retour += "\"" + x.toString() + "\",";
			}

		} finally {
			qexec.close();
		}
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
