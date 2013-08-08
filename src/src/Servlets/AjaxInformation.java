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
 * Servlet implementation class AjaxInformation
 */
@WebServlet("/AjaxInformation")
public class AjaxInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxInformation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	private String getInformation(String parameter) {
		Resultat resultat = questionne(parameter);
		HashMap<String, String> result = resultat.at(0);
		String nom = Utilitaires.nettoieRessource((result.get("nom") != null && !result.get("nom").isEmpty()) ? result.get("nom"): "néant");
		String adresse = Utilitaires.nettoieRessource((result.get("adresse") != null && !result.get("adresse").isEmpty()) ? result.get("adresse"): " ");
		String codePostal = Utilitaires.nettoieRessource((result.get("codePostal") != null && !result.get("codePostal").isEmpty()) ? result.get("codePostal")+",": " ");
		String ville = Utilitaires.nettoieRessource((result.get("ville") != null && !result.get("ville").isEmpty()) ? result.get("ville"): "inconnue");
		String telephone = Utilitaires.nettoieRessource((result.get("telephone") != null && !result.get("telephone").isEmpty()) ? result.get("telephone"): "inconnu");
		String fax = Utilitaires.nettoieRessource((result.get("fax") != null && !result.get("fax").isEmpty()) ? result.get("fax"): "inconnu");
		String site = Utilitaires.nettoieRessource((result.get("site") != null && !result.get("site").isEmpty()) ? result.get("site"): "inconnu");
		
		String reponse = new String("<div id=\"cadreInfoTitre\">"+
					"<span id=\"cadreInfoTitreContenu\">"+nom+"</span>"+
				"</div>"+
				"<div id=\"cadreInfoContenu\">"+
					"<ul>"+
						"<li>Adresse: "+adresse+"</li>"+
						"<li>"+codePostal+" "+ville+"</li>"+
						"<li>Tèl : "+telephone+"</li>"+
						"<li>Fax : "+fax+"</li>"+
						"<li>Site web : "+site+"</li>"+
					"</ul>"+
				"</div>"+
				"<div id=\"cadreInfoPlus\">"+
				"<div style=\"position:relative;float:left;width:75px;height:20px;background:black;\" onclick=\"cadreInfoHide();\"></div>"+
				"<div style=\"position:relative;float:left;width:175px;text-align:right;\" onclick=\"cadreInfoHide();\">plus d'information...&nbsp;</div>"+
				"</div>"
				);
		return reponse;
	}
	
	private Resultat questionne(String ressource){
		String requete = new String(
				"PREFIX sc: <http://schema.org/>\n"+
					"PREFIX dbpprop: <http://dbpedia.org/property/>\n"+
					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"+
					"SELECT ?nom ?adresse ?codePostal ?ville ?telephone ?fax ?site \n"+
					"WHERE{ \n"+
					    "<"+ressource+"> foaf:name ?nom . \n"+
					    "OPTIONAL { <"+ressource+"> dbpprop:location ?adresse .}\n "+
					    "OPTIONAL { <"+ressource+"> sc:postalCode ?codePostal .} \n"+
					    "OPTIONAL { <"+ressource+"> dbpprop:town ?ville .} \n"+
					    "OPTIONAL { <"+ressource+"> sc:telephone ?telephone .} \n"+
					    "OPTIONAL { <"+ressource+"> sc:faxNumber ?fax .}\n "+
					    "OPTIONAL { <"+ressource+"> dbpprop:website ?siteWeb .} \n"+
					"}"
					    );
//		System.out.println(requete);
		return SparqlQuery.requete(requete, EndPoint.Fac);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getInformation(request.getParameter("ressource")));
	}

}
