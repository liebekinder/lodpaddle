package src.Servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.core.EndPoint;
import src.core.Resultat;
import src.core.SparqlQuery;

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

		// Data from our server
		String queryString = "PREFIX foaf:<http://xmlns.com/foaf/0.1/> "
				+ "PREFIX dbpprop: <http://dbpedia.org/property/> "
				+ "SELECT ( STR(?nomS) AS ?nom ) ( STR(?codeS) AS ?code) "
				+ "FROM <http://lodpaddle.univ-nantes.fr/Communes_geolocalises> "
				+ "WHERE {" + "?commune foaf:name ?nomS . "
				+ "?commune dbpprop:insee ?codeS ." + "FILTER regex(?nomS,\"^"
				+ filtre + "\", \"i\") " + "}" + "ORDER BY ?nom"
				+ "  LIMIT 10 ";

		String retour = "[";
		try {
			Resultat resultats = SparqlQuery.requete(queryString, EndPoint.Fac);
			Iterator<HashMap<String,String>> it = resultats.iterator();
			while(it.hasNext()){
				HashMap<String, String> map = it.next();
				retour += "\"" + map.get("nom") + " - "
						+ map.get("code") + "\",";
			}
		} catch (Exception e) {
			if (retour.length() <= 2)
				return "[\"pas de résultat     \"]";
			else
				return retour.substring(0, retour.length() - 1) + "]";
		}

		// System.out.println(retour);

		if (retour.length() <= 2)
			return "[\"Ville introuvable. Une au hasard?     \"]";
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
