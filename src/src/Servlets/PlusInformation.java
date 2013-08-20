package src.Servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.core.EndPoint;
import src.core.Resultat;
import src.core.SparqlQuery;
import src.core.Utilitaires;

/**
 * Servlet implementation class PlusInformation
 */
@WebServlet("/PlusInformation")
public class PlusInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PlusInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(
				getPlusInformation(request.getParameter("ressource")));
	}

	private String getPlusInformation(String parameter) {
		Resultat resultat = getVoID(parameter);
		resultat.concat(questionne(parameter));
		
		String reponse = new String("<table id=\"tablePlusInformation\"><tr><th>Propriété</th><th>Valeur</th></tr>");
		Iterator<HashMap<String, String>> results = resultat.iterator();
		while(results.hasNext()){
			HashMap<String, String> result = results.next();
			
			reponse += "<tr>";
			reponse += "<td>"+Utilitaires.nettoieRessourceLeger(result.get("predicate"))+"</td><td>"+Utilitaires.nettoieRessourceLeger(result.get("object"))+"</td>";			
			reponse += "</tr>";
			
		}
		reponse += "</table>";
		return reponse;
	}

private Resultat getVoID(String ressource) {
	String requete = new String(
					"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n"+
					"SELECT ?predicate ?object \n"+
					"WHERE{ \n"+
				    "<"+ressource+"> rdf:type ?type . \n"+
					 	"?type ?predicate ?object"+
					"}"
					    );
	return SparqlQuery.requete(requete, EndPoint.Fac);
	}

private Resultat questionne(String ressource) {
		String requete = new String(
				"PREFIX sc: <http://schema.org/>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
						+ "SELECT ?predicate ?object \n"
						+ "WHERE{ \n" 
						+ "<" + ressource+ "> ?predicate ?object . \n" 
						+ "}");
		return SparqlQuery.requete(requete, EndPoint.Fac);
	}

}
