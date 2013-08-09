package src.Servlets;

import java.io.IOException;
import java.util.HashMap;

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
 * Servlet implementation class AjaxInsee
 */
@WebServlet("/AjaxInsee")
public class AjaxInsee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AjaxInsee() {
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
				getListName(request.getParameter("ressource")));
	}

	private String getListName(String parameter) {
		Resultat resultat = questionne(parameter);
		HashMap<String, String> result = resultat.at(0);

//		System.out.println(parameter);
//		System.out.println(parameter);
		String nom = Utilitaires
				.nettoieRessourceLeger((result.get("nom") != null && !result
						.get("nom").isEmpty()) ? result.get("nom")
						: "Nom inconnu!");
		String insee = Utilitaires
				.nettoieRessourceLeger((result.get("insee") != null && !result
						.get("insee").isEmpty()) ? result.get("insee")
						: "insee inconnu!");

		String reponse = new String("<div id=\"cadreInfoTitre\">"
				+ "<span id=\"cadreInfoTitreContenu\">" + nom + "</span>"
				+ "</div>" + "<div id=\"cadreInfoContenu\">"
				+ "<p> <a href=\"./?saisie=");
					reponse += nom;
				reponse += " - "
				+ insee
				+ "\">Lancer la recherche pour cette ville!</a></p>"
				+ "</div>"
				+ "<div id=\"cadreInfoPlus\">"
				+ "<div style=\"position:relative;float:left;width:25px;height:20px;background:black;\" onclick=\"cadreInfoHide();\"></div>"
				+ "<div style=\"position:relative;float:left;width:225px;text-align:right;\" onclick=\"afficheCadreInfoPlus('"
				+ parameter + "');\">plus d'information...&nbsp;</div>"
				+ "</div>";

		return reponse;
	}

	private Resultat questionne(String ressource) {
		String requete = new String("PREFIX sc: <http://schema.org/>\n"
				+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
				+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
				+ "SELECT ?nom ?insee \n" + "WHERE{ \n" + "<");
		requete += ressource;
		requete += "> foaf:name ?nom . \n" + "<" + ressource
				+ "> dbpprop:insee ?insee . \n" + "}";

//		System.out.println(requete);
		return SparqlQuery.requete(requete, EndPoint.Fac);
	}
}
