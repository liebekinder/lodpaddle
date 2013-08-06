package src.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.Beans.MyWidget;
import src.Beans.SousTheme;
import src.Beans.Theme;
import src.Beans.Widget;
import src.core.Categorie;
import src.core.Coordonnee;
import src.core.EndPoint;
import src.core.Resultat;
import src.core.SparqlQuery;
import src.core.Utilitaires;

public class Index extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/index.jsp";
	public List<String> listIds = new ArrayList<String>();
	public final String domain = "http://localhost:8080/lodpaddleTest/";
	// public final String domain =
	// "http://lodpaddle.univ-nantes.fr/lodpaddle/";
	public List<Widget> thematiques = new ArrayList<Widget>();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * Création des widgets
		 * 
		 * Cette page possède 2 gros états: La page d'accueil et la page
		 * associée à une ville, dites de navigation Cette différence d'état est
		 * détectée par la présence ou non de la variable GET 'saisie'. Si
		 * saisie est non null et non vide alors on considère qu'on est en mode
		 * navigation.
		 * 
		 * **/

		String nomVille = request.getParameter("saisie");

		if (nomVille != null && nomVille != "") {
			// on est en navigation
			themeManagmement(request, response);
			List<MyWidget> widgets = new ArrayList<MyWidget>();
			String insee = nomVille.split(" - ")[1];
			nomVille = nomVille.split(" - ")[0];

			// gestion des widgets du footer. Creation et passage en GET
			MyWidget ficheVille;
			MyWidget tweetVille;
			MyWidget photoVille;

			Coordonnee positionVille = recuperePosition(insee);

			ficheVille = new MyWidget(nomVille, getVillePresentation(nomVille,
					insee), "PresentationVille", "#2980b9", 550);

			tweetVille = new MyWidget("twitter", "", "twitter", "#2980b9", 250);
			tweetVille.setImageFond(domain + "media/tweet.png");

			photoVille = new MyWidget("photo", "", "photo1", "#2980b9", 200);
			photoVille.setImageFond(domain + "media/nantesTown.jpg");

			creationDataFlip(request, response, nomVille, insee, positionVille);

			widgets.add(ficheVille);
			widgets.add(tweetVille);
			widgets.add(photoVille);
			
			request.setAttribute("position", positionVille);
			request.setAttribute("widgets", widgets);
			request.setAttribute("typePage", nomVille);

		} else {
			// il s'agit de la page d'accueil

			List<MyWidget> widgets = new ArrayList<MyWidget>();

			MyWidget introduction = new MyWidget("Présentation", texteIntro(),
					"1", "#1abc9c", 200);
			MyWidget websemantique = new MyWidget("Web_sémantique",
					texteWebSemantique(), "2", "#2ecc71", 200);
			MyWidget endpoint = new MyWidget("Accès_développeurs", texteDev(),
					"3", "#5dabe3", 200);

			widgets.add(introduction);
			widgets.add(websemantique);
			widgets.add(endpoint);

			request.setAttribute("widgets", widgets);
			request.setAttribute("typePage", "accueil");
		}

		request.setAttribute("domain", domain);
		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}

	private Coordonnee recuperePosition(String insee) {

		String requete = new String(
				"PREFIX dbpprop: <http://dbpedia.org/property/> "
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> "
						+ "SELECT ?lat ?lon "
						+ "FROM <http://lodpaddle.univ-nantes.fr/Communes_geolocalises> "
						+ "WHERE { " + "?commune dbpprop:insee "+insee+" . "
						+ "?commune geo:lat ?lat . "
						+ "?commune geo:long ?lon . " + "}");
		Resultat resultats = SparqlQuery.requete(requete, EndPoint.Fac);

		// on teste si la requete à bien retourné quelque chose
		if (resultats.estVide())
			return null;
		Double lat = Double.valueOf(Utilitaires.nettoieRessource(resultats.at(0).get("lat")));
		Double lon = Double.valueOf(Utilitaires.nettoieRessource(resultats.at(0).get("lon")));
		Coordonnee position = new Coordonnee(lat, lon);

		return position;
	}

	private void creationDataFlip(HttpServletRequest request,
			HttpServletResponse response, String nomVille, String insee, Coordonnee position) {

		/** widget items for flip **/
		Theme loisirs = new Theme("Loisirs", ".flipLoisir", position);
		// Theme culture = new Theme("Culture", ".flipCulture");
		// Theme ville = new Theme("Ville", ".flipVille");
		// Theme service = new Theme("Service", ".flipService");
		// Theme transport = new Theme("Transport", ".flipTransport");
		// Theme aVisiter = new Theme("&Agrave; visiter", ".flipVisite");

		SousTheme restaurant = new SousTheme("Restaurants", domain
				+ "/media/restaurantPicto.png", Categorie.RESTAURANT);
		SousTheme hotel = new SousTheme("Hotels", domain
				+ "/media/hotelPicto.png", Categorie.HOTEL);
		SousTheme golf = new SousTheme("Parcs de golf", domain
				+ "/media/golfPicto.png", Categorie.GOLF);
		SousTheme plage = new SousTheme("Plages", domain
				+ "/media/plagePicto.png", Categorie.PLAGE);
		SousTheme sport = new SousTheme("Centres sportifs", domain
				+ "/media/sportPicto.png", Categorie.SPORT);

		loisirs.ajoutSousTheme(restaurant);
		loisirs.ajoutSousTheme(hotel);
		loisirs.ajoutSousTheme(golf);
		loisirs.ajoutSousTheme(plage);
		loisirs.ajoutSousTheme(sport);

		request.setAttribute("themeLoisir", loisirs);

	}

	private String getVillePresentation(String nomVille, String insee) {

		// Création de la requête qui recupèrera le departement, altitude
		// min-max, gentile, maire mandat, superficie site et description de la
		// ville identifiée par le code insee trouvé précédemment
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
						+ "PREFIX prop-fr: <http://fr.dbpedia.org/property/>"
						+ "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>"
						+ "SELECT ?dep ?min ?max ?gentile ?maire ?mandat ?superficie ?description ?site WHERE {"
						+ "?ville prop-fr:insee "
						+ insee
						+ " ."
						+ "OPTIONAL{?ville prop-fr:département ?dep .}"
						+ "OPTIONAL{?ville prop-fr:gentilé ?gentile .}"
						+ "OPTIONAL{?ville dbpedia-owl:abstract ?description .}"
						+ "OPTIONAL{?ville prop-fr:superficie ?superficie .}"
						+ "OPTIONAL{?ville prop-fr:siteweb ?site . }"
						+ "OPTIONAL{?ville prop-fr:maire ?maire .}"
						+ "OPTIONAL{?maire prop-fr:nom ?mairenom .}"
						+ "OPTIONAL{?ville prop-fr:mandatMaire ?mandat .}"
						+ "OPTIONAL{?ville prop-fr:altMini ?min .}"
						+ "OPTIONAL{?ville prop-fr:altMaxi ?max .}"
						+ "FILTER( lang(?description) = \"fr\")" + "}");
		Resultat resultat = SparqlQuery.requete(requete, EndPoint.DBpedia);

		String requete2 = new String(
				"PREFIX idemo:<http://rdf.insee.fr/def/demo#>"
						+ "PREFIX igeo:<http://rdf.insee.fr/def/geo#>" + ""
						+ "SELECT ?pop WHERE {"
						+ "?commune igeo:codeCommune \"" + insee + "\" ."
						+ "?commune idemo:population ?popT ."
						+ "?popT idemo:populationTotale ?pop ." + "}");

		Resultat resultat2 = SparqlQuery.requete(requete2, EndPoint.Insee);

		if (resultat == null || resultat.estVide())
			return contenuVide(nomVille);

		// System.out.println(resultat.taille());
		HashMap<String, String> ligne = resultat.at(0);
		HashMap<String, String> ligne2 = resultat2.at(0);

		String dep = Utilitaires.nettoieRessource(
				(ligne.get("dep") != null && !ligne.get("dep").isEmpty()) ? ligne.get("dep")
						: "néant", "dep");
		String min = Utilitaires.nettoieRessource((ligne.get("min") != null && !ligne.get(
				"min").isEmpty()) ? ligne.get("min") : "néant");
		String max = Utilitaires.nettoieRessource((ligne.get("max") != null && !ligne.get(
				"max").isEmpty()) ? ligne.get("max") : "néant");
		String gentile = Utilitaires.nettoieRessource((ligne.get("gentile") != null && !ligne
				.get("gentile").isEmpty()) ? ligne.get("gentile") : "néant");
		String maire = Utilitaires.nettoieRessource((ligne.get("maire") != null && !ligne
				.get("maire").isEmpty()) ? ligne.get("maire") : "néant");
		String mandat = Utilitaires.nettoieRessource((ligne.get("mandat") != null && !ligne
				.get("mandat").isEmpty()) ? ligne.get("mandat") : "néant");
		String superficie = Utilitaires.nettoieRessource((ligne.get("superficie") != null && !ligne
				.get("superficie").isEmpty()) ? ligne.get("superficie")
				: "néant");
		// String description = (ligne.get("description") != null &&
		// !ligne.get("description").isEmpty())?ligne.get("description"):"néant";
		String site = (ligne.get("site") != null && !ligne.get("site")
				.isEmpty()) ? "<a href=\""
				+ ligne.get("site")
				+ "\" alt=\"lien vers le site de la commune\" target=\"_blank\">"
				+ ligne.get("site") + "</a>"
				: "néant";
		String pop = "néant";
		if (ligne2 != null)
			pop = Utilitaires.nettoieRessource((ligne2.get("pop") != null && !ligne2.get(
					"pop").isEmpty()) ? ligne2.get("pop") : "néant");

		String ficheVilleText = new String("<div id=\"presentationCadre\">"
				+ "<div id=\"presentationTitre\">" + "<h3 class=\"center\">"
				+ nomVille
				+ "</h3>"
				+ "</div>"
				+ "<div id=\"presentationGauche\">"
				+ "<br /><b>Département :</b> "
				+ dep
				+ "<br /><b>Maire :</b> "
				+ maire
				+ "<br /><b>Début du mandat :</b> "
				+ mandat
				+ "<br /><b>Site web :</b> "
				+ site
				+ "</div>"
				+ "<div id=\"presentationDroite\">"
				+ "<br /><b>Gentilé:</b> "
				+ gentile
				+ "<br /><b>Population totale:</b> "
				+ pop
				+ "<br /><b>Densitée :</b>"
				+ densite(pop, superficie)
				+ "<br /><b>Altitude :</b> "
				+ min
				+ "m - "
				+ max
				+ "m"
				+ "<br /><b>Superficie :</b> "
				+ superficie
				+ "km²"
				+ "</div>"
				+ "</div>");
		return ficheVilleText;
	}

	private String densite(String pop, String superficie) {
		if (pop.equals("néant") || superficie.equals("néant")) {
			return "néant";
		} else {
			// les deux ont une valeur numérique
			double h = Double.valueOf(pop);
			double s = Double.valueOf(superficie);
			String retour = Double.toString(h / s);
			return retour.substring(0, retour.indexOf('.') + 2) + " hab/km²";
		}
	}

	private String contenuVide(String nomVille) {
		return "<div id=\"presentationCadre\">"
				+ "<div id=\"presentationTitre\">"
				+ "<h3 class=\"center\">"
				+ nomVille.split(" - ")[0]
				+ "</h3>"
				+ "</div>"
				+ "<div class=\"noInfo\" style=\" position:absolute; top:50px;\">Nous n'avons pas pu récupérer d'information sur le sujet. :(</br>"
				+ "Mais cela va probablement changer rapidement! :)" + "</div>"
				+ "</div>";
	}

	private String texteIntro() {
		String texteIntro = new String(
				"<div style=\"height: 100%;width:100%;word-wrap: break-word;padding:5px;\">"
						+ "<h3 class=\"center\">Présentation</h3>"
						+ "<p>LodPaddle est un service innovant qui premet de surfer sur "
						+ "<a href=\"blabla.jsp\"> l'open data de la Région des Pays de la Loire </a></p>"
						+ "</div>");
		return texteIntro;
	}

	private String texteWebSemantique() {
		String texteIntro = new String(
				"<div style=\"height: 100%;width:100%;word-wrap: break-word;padding:5px;\">"
						+ "<h3 class=\"center\">&Agrave; propos du web sémantique</h3>"
						+ "</div>");
		return texteIntro;
	}

	private String texteDev() {
		String texteIntro = new String(
				"<div style=\"height: 100%;width:100%;word-wrap: break-word;padding:5px;\">"
						+ "<h3 class=\"center\">Accès developpeur</h3>"
						+ "</div>");
		return texteIntro;
	}

	private void themeManagmement(HttpServletRequest request,
			HttpServletResponse response) {
		// this function manage static theme
		List<String> listeTheme = new ArrayList<String>();
		listeTheme.add("loisir");
		listeTheme.add("culture");
		listeTheme.add("ville");
		listeTheme.add("service");
		listeTheme.add("visite");
		listeTheme.add("transport");

		int nbCol = (listeTheme.size() % 2 == 0) ? listeTheme.size() / 2
				: listeTheme.size() / 2 + 1;

		request.setAttribute("theme", listeTheme);
		request.setAttribute("nbCol", nbCol);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
