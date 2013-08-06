package src.Beans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import src.core.Categorie;
import src.core.Coordonnee;
import src.core.EndPoint;
import src.core.Resultat;
import src.core.SparqlQuery;
import src.core.Utilitaires;

public class SousTheme {

	String titre;
	String picto;
	Categorie cat;
	List<Lien> entree;

	public SousTheme(String titre, String picto, Categorie cat) {
		this.titre = titre;
		this.picto = picto;
		this.cat = cat;
		entree = new ArrayList<Lien>();
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getPicto() {
		return picto;
	}

	public void setPicto(String picto) {
		this.picto = picto;
	}

	public List<Lien> getEntree() {
		return entree;
	}

	public void setEntree(List<Lien> entree) {
		this.entree = entree;
	}

	public void ajoutLien(String nom, String lien) {
		Lien monLien = new Lien(nom, lien);
		entree.add(monLien);
	}

	public void creeLiens(Coordonnee position) {
		Resultat resultats = SparqlQuery
				.requete(cat.getRequete(position), EndPoint.Fac);
		if (resultats == null || resultats.estVide())
			return;

		// on parcours tout les résultats et on enregistre le nom et la
		// ressource(id)
		// la requête limite le nombre de réponse (arbitrairement 3)
		Iterator<HashMap<String, String>> it = resultats.iterator();
		while (it.hasNext()) {
			HashMap<String, String> map = it.next();
			this.ajoutLien(Utilitaires.nettoieRessource(map.get("nom")).toLowerCase(), map.get("ressource"));
		}
	}

	public void creeLienTemp() {
		switch (cat) {
		case RESTAURANT:
			this.ajoutLien("Le poellon d'or", "index.jsp");
			this.ajoutLien("Hotelerie de la ferrerie", "index.jsp");
			this.ajoutLien("Plusieurs gourmands", "index.jsp");
			break;
		case HOTEL:
			this.ajoutLien("le Chateau de vincenne", "index.jsp");
			this.ajoutLien("baf hotel", "index.jsp");
			this.ajoutLien("le relais de la grange", "index.jsp");
			break;
		case GOLF:
			this.ajoutLien("Golf de carquefou", "index.jsp");
			this.ajoutLien("Golf de jump up", "index.jsp");
			this.ajoutLien("Golf de vander woodsen", "index.jsp");
			break;
		case PLAGE:
			this.ajoutLien("Pornic", "index.jsp");
			this.ajoutLien("La baule les pins", "index.jsp");
			this.ajoutLien("Plages du cantonnais", "index.jsp");
			break;
		case SPORT:
			this.ajoutLien("Stade de la tourniere", "index.jsp");
			this.ajoutLien("Tennis municipaux maine et Loire", "index.jsp");
			this.ajoutLien("Gymnase Aman du blac", "index.jsp");
			break;
		default:
			break;
		}
	}
}
