package src.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import src.Beans.MyWidget;
import src.Beans.SousTheme;
import src.Beans.Theme;
import src.core.CalculDistance;
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

//	public final String domain = "http://localhost:8080/lodpaddle/";
//	public final String domain = "http://localhost:8081/lodpaddle/";
	public final String domain = "http://lodpaddle.univ-nantes.fr/lodpaddle/";

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
		String retourJeu = request.getParameter("retourJeu");
		String quitter = request.getParameter("quitter");
		
		HttpSession session = request.getSession(true);
		List<List<String>> listeVille = null;
		if(session.getAttribute("listeVille") != null){

			listeVille = (List<List<String>>) session.getAttribute("listeVille");
		}
		String VUE = "/index.jsp";
		String ERROR = "/error.jsp";
		
		if(quitter != null && quitter != ""){
			//on revient du jeu, il y a une liste de 10 Ville dans cette variable	
			
			listeVille = null;
			session.setAttribute("listeVille", listeVille);
		}
		
		if(retourJeu != null && retourJeu != ""){
			//on revient du jeu, il y a une liste de 10 Ville dans cette variable	
			
			listeVille = new ArrayList<List<String>>();
			
			for(String s:retourJeu.split(" ; ")){
				List<String> temp = new ArrayList<String>();
				temp.add(s.split(" - ")[0]);
				temp.add(s.split(" - ")[1]);
				listeVille.add(temp);
			}
			
			session.setAttribute("listeVille", listeVille);
			//on defini nomVille pour ne pas déclencher la recherche aléatoire
			nomVille = listeVille.get(0).get(0)+" - "+listeVille.get(0).get(1);
		}
		if (nomVille == null || nomVille == ""
				|| nomVille.split(" - ").length < 2) {
			Resultat results = new Resultat();
			if (request.getParameter("lat") == null
					|| request.getParameter("lon") == null) {
				// cas particulier de l'accueil => on trouve une ville au hasard
				results = trouveUneVille();
				if (results != null) {
					int nbResultat = results.taille();
					Random rand = new Random();
					int villeInd = rand.nextInt(nbResultat);
					HashMap<String, String> result = results.at(villeInd);

					String nomRandom = Utilitaires
							.nettoieRessourceLeger((result.get("nom") != null && !result
									.get("nom").isEmpty()) ? result.get("nom")
									: "Nom inconnu!");
					String inseeRandom = Utilitaires
							.nettoieRessourceLeger((result.get("insee") != null && !result
									.get("insee").isEmpty()) ? result
									.get("insee") : "insee inconnu!");
					nomVille = nomRandom + " - " + inseeRandom;
				} else {
					nomVille = null;
				}
			} else {
				results = trouveVilleParCoord(request.getParameter("lon"),
						request.getParameter("lat"));
				if (results != null) {
					HashMap<String, String> result = results.at(0);

					String nomRandom = Utilitaires
							.nettoieRessourceLeger((result.get("nom") != null && !result
									.get("nom").isEmpty()) ? result.get("nom")
									: "Nom inconnu!");
					String inseeRandom = Utilitaires
							.nettoieRessourceLeger((result.get("insee") != null && !result
									.get("insee").isEmpty()) ? result
									.get("insee") : "insee inconnu!");
					nomVille = nomRandom + " - " + inseeRandom;
				} else {
					nomVille = null;
				}
			}
		}
		// on est en navigation

		themeManagmement(request, response);
		imageManagment(request,response);
		List<MyWidget> widgets = new ArrayList<MyWidget>();
		if (nomVille != null) {
			String insee = nomVille.split(" - ")[1];
			nomVille = nomVille.split(" - ")[0];

			// gestion des widgets du footer. Creation et passage en GET
			MyWidget ficheVille;
			MyWidget jeu;
			MyWidget projet;

			Coordonnee positionVille = recuperePosition(insee);

			ficheVille = new MyWidget(nomVille, getVillePresentation(nomVille,
					insee), "PresentationVille", "#2980b9", 480);
			
			jeu = new MyWidget("Jeu", getJeuTexte(), "Jeu", "#e75735", 140);
			projet = new MyWidget("projet", getProjetTexte(), "Projet", "#e75735", 140);



			if (positionVille != null) {
				creationDataFlip(request, response, nomVille, insee,
						positionVille);
			}
			else{
				request.setAttribute("error", "noSparql");
			}

			widgets.add(ficheVille);
			widgets.add(jeu);
			widgets.add(projet);

			if(listeVille != null && listeVille.size() != 0){
				request.setAttribute("listeVille", listeVille);
			}
			
			request.setAttribute("position", positionVille);
			request.setAttribute("widgets", widgets);
			request.setAttribute("typePage", nomVille);

			request.setAttribute("domain", domain);
			this.getServletContext().getRequestDispatcher(VUE)
					.forward(request, response);
		} else {

			this.getServletContext().getRequestDispatcher(ERROR)
					.forward(request, response);
		}
	}

	private String getProjetTexte() {
		String retour = new String(
				"<div id=\"accesProjetTitre\" onclick=\"$('#dialogContact').load('data/description.html').dialog('open');\"><table><tr><td class=\"moyenTexte\">" +
				"Le projet Lodpaddle" +
				"</td></tr>" +
				"<tr><td class=\"petitTexte\"><br/>Faire du web sémantique une réalité.</td></tr></table></div>"
				);
		return retour;
	}

	private String getJeuTexte() {
		String retour = new String(
				"<div id=\"accesJeuTitre\" onclick=\"document.location.href='"+domain+"Game'\"><table><tr><td>" +
				"Jouer avec les données!" +
				"</td></tr><tr><td><img src=\""+domain+"media/pictojeusurvol.png\"/></td></tr></table></div>"
				);
		return retour;
	}

	private void imageManagment(HttpServletRequest request,
			HttpServletResponse response) {
		//methode qui recupere les urls des images à partir de mediaWiki
		
		
	}

	private Resultat trouveVilleParCoord(String lonS, String latS) {
		Double lon = Double.valueOf(lonS);
		Double lat = Double.valueOf(latS);
		String requete = new String(
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>\n"
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"

						+ "SELECT ?insee ?nom ?lat ?long("
						+ CalculDistance.selectPartString(50, lat, lon, "?lat",
								"?long")
						+ " AS ?distance)\n"
						+ "FROM <http://lodpaddle.univ-nantes.fr/Communes_geolocalises>\n"
						+ "WHERE{\n" + "?ressource geo:lat ?lat.\n"
						+ "?ressource geo:long?long.\n"
						+ "?ressource foaf:name ?nom.\n"
						+ "?ressource dbpprop:insee ?insee . \n"
						+ "} ORDER BY ASC(?distance) LIMIT 1");

		// System.out.println(requete);
		return SparqlQuery.requete(requete, EndPoint.Fac);
	}

	private Resultat trouveUneVille() {
		String requete = new String(
				""
						+ "PREFIX dbpprop: <http://dbpedia.org/property/>\n"
						+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n"
						+ "SELECT ?nom ?insee \n"
						+ "FROM <http://lodpaddle.univ-nantes.fr/Communes_geolocalises>\n"
						+ "WHERE{ \n" + "?a foaf:name ?nom . \n"
						+ "?a dbpprop:insee ?insee . \n" + "}");

		// System.out.println(requete);
		return SparqlQuery.requete(requete, EndPoint.Fac);
	}

	private Coordonnee recuperePosition(String insee) {

		String requete = new String(
				"PREFIX dbpprop: <http://dbpedia.org/property/> "
						+ "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#> "
						+ "SELECT ?lat ?lon "
						+ "FROM <http://lodpaddle.univ-nantes.fr/Communes_geolocalises> "
						+ "WHERE { " + "?commune dbpprop:insee " + insee
						+ " . " + "?commune geo:lat ?lat . "
						+ "?commune geo:long ?lon . " + "}");
		Resultat resultats = SparqlQuery.requete(requete, EndPoint.Fac);

		// on teste si la requete à bien retourné quelque chose
		if (resultats == null || resultats.estVide())
			return null;
		Double lat = Double.valueOf(Utilitaires.nettoieRessource(resultats
				.at(0).get("lat")));
		Double lon = Double.valueOf(Utilitaires.nettoieRessource(resultats
				.at(0).get("lon")));
		Coordonnee position = new Coordonnee(lon, lat);

		return position;
	}

	private void creationDataFlip(HttpServletRequest request,
			HttpServletResponse response, String nomVille, String insee,
			Coordonnee position) {

		/** widget items for flip **/
		Theme loisirs = new Theme("Loisirs", ".flipLoisir", position);
		Theme culture = new Theme("Culture", ".flipCulture", position);
		Theme ville = new Theme("Ville", ".flipVille", position);
		Theme service = new Theme("Service", ".flipService", position);
		Theme transport = new Theme("Transport", ".flipTransport", position);
		Theme aVisiter = new Theme("&Agrave; visiter", ".flipVisite", position);

		SousTheme restaurant = new SousTheme("Restaurants", domain
				+ "media/picto/restaurantPicto.png", domain
				+ "media/marker/M-restaurant.png", Categorie.RESTAURANT);
		SousTheme hotel = new SousTheme("Hotels", domain
				+ "media/picto/hotelPicto.png", domain
				+ "media/marker/M-hotel.png", Categorie.HOTEL);
		SousTheme golf = new SousTheme("Parcs de golf", domain
				+ "media/picto/golfPicto.png", domain
				+ "media/marker/M-golf.png", Categorie.GOLF);
		SousTheme plage = new SousTheme("Plages", domain
				+ "media/picto/plagePicto.png", domain
				+ "media/marker/M-plage.png", Categorie.PLAGE);
		SousTheme sport = new SousTheme("Centres sportifs", domain
				+ "media/picto/sportPicto.png", domain
				+ "media/marker/M-sport.png", Categorie.SPORT);

		loisirs.ajoutSousTheme(restaurant);
		loisirs.ajoutSousTheme(hotel);
		loisirs.ajoutSousTheme(golf);
		loisirs.ajoutSousTheme(plage);
		loisirs.ajoutSousTheme(sport);

		SousTheme cinema = new SousTheme("Cinéma", domain
				+ "media/picto/cinemaPicto.png", domain
				+ "media/marker/M-cinema.png", Categorie.CINEMA);
		SousTheme equipementCulturel = new SousTheme("Equipement Culturel",
				domain + "media/picto/equipementPicto.png", domain
						+ "media/marker/M-equipement.png",
				Categorie.EQUIPEMENT_CULTURE);
		SousTheme mediatheque = new SousTheme("Médiathèque", domain
				+ "media/picto/mediathequePicto.png", domain
				+ "media/marker/M-mediatheque.png", Categorie.MEDIATHEQUE);
		SousTheme salleSpectacle = new SousTheme("Salle de Spectacle", domain
				+ "media/picto/salle-de-spectaclePicto.png", domain
				+ "media/marker/M-spectacle.png", Categorie.SALLE_SPECTACLE);

		culture.ajoutSousTheme(cinema);
		culture.ajoutSousTheme(equipementCulturel);
		culture.ajoutSousTheme(mediatheque);
		culture.ajoutSousTheme(salleSpectacle);

		SousTheme actionSociale = new SousTheme("Action sociale", domain
				+ "media/picto/action-socialePicto.png", domain
				+ "media/marker/M-action-social.png", Categorie.ACTION_SOCIALE);
		SousTheme decheterie = new SousTheme("Décheterie", domain
				+ "media/picto/decheteriePicto.png", domain
				+ "media/marker/M-dechetterie.png", Categorie.DECHETERIE);
		SousTheme justice = new SousTheme("Justice", domain
				+ "media/picto/justicePicto.png", domain
				+ "media/marker/M-justice.png", Categorie.JUSTICE);
		SousTheme poste = new SousTheme("Poste", domain
				+ "media/picto/postePicto.png", domain
				+ "media/marker/M-poste.png", Categorie.POSTE);
		SousTheme vieSociale = new SousTheme("Vie sociale", domain
				+ "media/picto/vie-socialePicto.png", domain
				+ "media/marker/M-vie-social.png", Categorie.VIE_SOCIALE);
		SousTheme servicePublic = new SousTheme("Service public", domain
				+ "media/picto/publicPicto.png", domain
				+ "media/marker/M-public.png", Categorie.SERVICE_PUBLIC);

		service.ajoutSousTheme(actionSociale);
		service.ajoutSousTheme(decheterie);
		service.ajoutSousTheme(justice);
		service.ajoutSousTheme(poste);
		service.ajoutSousTheme(vieSociale);
		service.ajoutSousTheme(servicePublic);

		SousTheme chateau = new SousTheme("Château", domain
				+ "media/picto/cinemaPicto.png", domain
				+ "media/marker/M-chateau.png", Categorie.CHATEAU);
		SousTheme citeCaractere = new SousTheme("Petites cités de caractère",
				domain + "media/picto/equipementPicto.png", domain
						+ "media/marker/M-cite-de-caractere.png",
				Categorie.CITE);
		SousTheme jardin = new SousTheme("Jardins familiaux", domain
				+ "media/picto/mediathequePicto.png", domain
				+ "media/marker/M-jardin.png", Categorie.JARDIN);
		SousTheme patrimoine = new SousTheme("Patrimoine", domain
				+ "media/picto/patrimoinePicto.png", domain
				+ "media/marker/M-patrimoine.png", Categorie.PATRIMOINE);
		SousTheme parc = new SousTheme("Parc animalier et à thème", domain
				+ "media/picto/parkPicto.png", domain
				+ "media/marker/M-park.png", Categorie.PARC_ANIMALIER);

		aVisiter.ajoutSousTheme(chateau);
		aVisiter.ajoutSousTheme(citeCaractere);
		aVisiter.ajoutSousTheme(jardin);
		aVisiter.ajoutSousTheme(patrimoine);
		aVisiter.ajoutSousTheme(parc);

		SousTheme mobilite = new SousTheme("Mobilité", domain
				+ "media/picto/mobilitePicto.png", domain
				+ "media/marker/M-mobilite.png", Categorie.MOBILITE);
		SousTheme parking = new SousTheme("Parking", domain
				+ "media/picto/parkingPicto.png", domain
				+ "media/marker/M-parking.png", Categorie.PARKING);

		transport.ajoutSousTheme(mobilite);
		transport.ajoutSousTheme(parking);

		SousTheme villeProche = new SousTheme("Villes à proximité", domain
				+ "media/picto/villePicto.png", domain
				+ "media/marker/M-ville.png", Categorie.VILLEPROCHE);

		ville.ajoutSousTheme(villeProche);

		request.setAttribute("themeLoisir", loisirs);
		request.setAttribute("themeCulture", culture);
		request.setAttribute("themeService", service);
		request.setAttribute("themeVisite", aVisiter);
		request.setAttribute("themeTransport", transport);
		request.setAttribute("themeVille", ville);

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

		String dep = Utilitaires
				.nettoieRessource((ligne.get("dep") != null && !ligne
						.get("dep").isEmpty()) ? ligne.get("dep") : "inconnu",
						"dep");
		String min = Utilitaires
				.nettoieRessource((ligne.get("min") != null && !ligne
						.get("min").isEmpty()) ? ligne.get("min") : "?");
		String max = Utilitaires
				.nettoieRessource((ligne.get("max") != null && !ligne
						.get("max").isEmpty()) ? ligne.get("max") : "?");
		String gentile = Utilitaires
				.nettoieRessource((ligne.get("gentile") != null && !ligne.get(
						"gentile").isEmpty()) ? ligne.get("gentile")
						: "inconnue");
		String maire = Utilitaires
				.nettoieRessource((ligne.get("maire") != null && !ligne.get(
						"maire").isEmpty()) ? ligne.get("maire") : "inconnu");
		String mandat = Utilitaires
				.nettoieRessource((ligne.get("mandat") != null && !ligne.get(
						"mandat").isEmpty()) ? ligne.get("mandat") : "inconnu");
		String superficie = Utilitaires.nettoieRessource((ligne
				.get("superficie") != null && !ligne.get("superficie")
				.isEmpty()) ? (ligne.get("superficie") + " km²")
				: "non précisée");
		// String description = (ligne.get("description") != null &&
		// !ligne.get("description").isEmpty())?ligne.get("description"):"néant";
		String site = (ligne.get("site") != null && !ligne.get("site")
				.isEmpty()) ? "<a href=\"" + ligne.get("site")
				+ "\" target=\"_blank\">" + ligne.get("site") + "</a>"
				: "inconnu";
		String pop = "néant";
		if (ligne2 != null)
			pop = Utilitaires
					.nettoieRessource((ligne2.get("pop") != null && !ligne2
							.get("pop").isEmpty()) ? ligne2.get("pop")
							: "néant");

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
				+ " hab<br /><b>Densité :</b>"
				+ densite(pop, superficie)
				+ "<br /><b>Altitude :</b> "
				+ min
				+ "m - "
				+ max
				+ "m"
				+ "<br /><b>Superficie :</b> "
				+ superficie
				+ "km²</div>"
				+ "</div>");
		return ficheVilleText;
	}

	private String densite(String pop, String superficie) {
		if (pop.equals("néant") || superficie.equals("néant")
				|| superficie.equals("non précisée")) {
			return "néant";
		} else {
			// les deux ont une valeur numérique
			double h = Double.valueOf(pop);
			double s = Double.valueOf(superficie);
			String retour = Double.toString(h / s);
			return (retour.substring(0, retour.indexOf('.') + 2) + " hab/km²");
		}
	}

	private String contenuVide(String nomVille) {
		return "<div id=\"presentationCadre\">"
				+ "<div id=\"presentationTitre\">"
				+ "<h3 class=\"center\">"
				+ nomVille.split(" - ")[0]
				+ "</h3>"
				+ "</div>"
				+ "<div class=\"noInfo\" style=\" position:absolute; top:50px;\">Nous n'avons pas pu récupérer d'information utile sur "
				+ nomVille + " à partir de fr.dbpedia.org! :( </br>" + "</div>"
				+ "</div>";
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
