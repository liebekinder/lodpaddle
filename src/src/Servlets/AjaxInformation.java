package src.Servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	private String getInformation(String parameter) {
		Resultat resultat = questionne(parameter);
		HashMap<String, String> result = resultat.at(0);
		String nom = Utilitaires.nettoieRessourceLeger((result.get("nom") != null && !result.get("nom").isEmpty()) ? result.get("nom"): "Nom inconnu!");
		String adresse = Utilitaires.nettoieRessourceLeger((result.get("adresse") != null && !result.get("adresse").isEmpty()) ? result.get("adresse"): "");
		String codePostal = Utilitaires.nettoieRessourceLeger((result.get("codePostal") != null && !result.get("codePostal").isEmpty()) ? result.get("codePostal")+",": "");
		String ville = Utilitaires.nettoieRessourceLeger((result.get("ville") != null && !result.get("ville").isEmpty()) ? result.get("ville"): "");
		String telephone = Utilitaires.nettoieRessourceLeger((result.get("telephone") != null && !result.get("telephone").isEmpty()) ? result.get("telephone"): "");
		String email = Utilitaires.nettoieRessourceLeger((result.get("email") != null && !result.get("email").isEmpty()) ? result.get("email"): "");
		String site = Utilitaires.nettoieRessourceLeger((result.get("site") != null && !result.get("site").isEmpty()) ? result.get("site"): "");
		
		String reponse = new String("<div id=\"cadreInfoTitre\">"+
					"<span id=\"cadreInfoTitreContenu\">"+nom+"</span>"+
				"</div>"+
				"<div id=\"cadreInfoContenu\">"+
					"<br/><table id=\"infoTableContent\">");
			reponse += (!adresse.isEmpty() || !adresse.equals("undefined") || !codePostal.isEmpty() || !codePostal.equals("undefined") || !ville.isEmpty() || !ville.equals("undefined"))?
					(adresse.isEmpty() && codePostal.isEmpty() && ville.isEmpty())?"":"<tr><td class=\"largeur40\">Adresse: </td><td> "+adresse+" "+codePostal+" "+ville+"</td></tr>":"";
			reponse += (telephone.isEmpty() || telephone.equals("undefined"))?"":"<tr><td>Tel: </td><td>"+telephone+"</td></tr>";
			reponse += (email.isEmpty() || email.equals("undefined"))?"":"<tr><td>Email: </td><td><a href=\"mailto:"+email+"\">"+email+"</a></td></tr>";
			reponse += (site.isEmpty() || site.equals("undefined") || !bonEmail(site))?"":"<tr><td>Site web: </td><td><a href=\"http://"+site+"\" target=\"_blank\">"+site+"</a></td></tr>";
			reponse += "</table><br/>"+
				"</div>"+
				"<div id=\"cadreInfoPlus\">"+
				"<div id=\"cadreInfoBasGauche\" onclick=\"cadreInfoHide();\"></div>"+
				"<div id=\"cadreInfoBasDroite\" onclick=\"afficheCadreInfoPlus('"+parameter+"');\"><span>Données sémantiques...&nbsp;</span></div>"+
				"</div>"
				;
		return reponse;
	}
	
	private boolean bonEmail(String site) {
		return Pattern.matches("[A-Za-z0-9\\.-]{3,}\\.[A-Za-z]{2,3}[A-Za-z0-9\\.-]*", site);
	}

	private Resultat questionne(String ressource){
		String requete = new String(
				"PREFIX sc: <http://schema.org/>\n"+
					"PREFIX dbpprop: <http://dbpedia.org/property/>\n"+
					"PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"+
					"SELECT ?nom ?adresse ?codePostal ?ville ?telephone ?email ?site \n"+
					"WHERE{ \n"+
					    "<"+ressource+"> foaf:name ?nom . \n"+
					    "OPTIONAL { <"+ressource+"> dbpprop:location ?adresse .}\n "+
					    "OPTIONAL { <"+ressource+"> sc:postalCode ?codePostal .} \n"+
					    "OPTIONAL { <"+ressource+"> dbpprop:town ?ville .} \n"+
					    "OPTIONAL { <"+ressource+"> sc:telephone ?telephone .} \n"+
					    "OPTIONAL { <"+ressource+"> sc:email ?email .}\n "+
					    "OPTIONAL { <"+ressource+"> dbpprop:website ?site .} \n"+
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
