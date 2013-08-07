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
	String marker;
	Categorie cat;
	List<Entree> entrees;

	public SousTheme(String titre, String picto, String marker, Categorie cat) {
		this.titre = titre;
		this.picto = picto;
		this.marker = marker;
		this.cat = cat;
		entrees = new ArrayList<Entree>();
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public Categorie getCat() {
		return cat;
	}

	public void setCat(Categorie cat) {
		this.cat = cat;
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

	public List<Entree> getEntrees() {
		return entrees;
	}

	public void setEntrees(List<Entree> entrees) {
		this.entrees = entrees;
	}

	public void ajoutEntree(String nom, String lien, Coordonnee position) {
		Entree monLien = new Entree(nom, lien, position);
		entrees.add(monLien);
	}

	public void creeEntrees(Coordonnee position) {
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
			this.ajoutEntree(Utilitaires.nettoieAffichage(map.get("nom")),
					map.get("ressource"),new Coordonnee(Utilitaires.nettoieAffichage(map.get("long")),
					Utilitaires.nettoieAffichage(map.get("lat"))));
		}
	}
}
