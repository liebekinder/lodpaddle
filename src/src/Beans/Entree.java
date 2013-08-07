package src.Beans;

import src.core.Coordonnee;

public class Entree {
	String entreeNom;
	String entreeUrl;
	Coordonnee position;

	public Entree(String entreeNom, String entreeUrl, Coordonnee c) {
		this.entreeNom = entreeNom;
		this.entreeUrl = entreeUrl;
		position = c;
	}

	public String getEntreeNom() {
		return entreeNom;
	}

	public void setEntreeNom(String entreeNom) {
		this.entreeNom = entreeNom;
	}

	public String getEntreeUrl() {
		return entreeUrl;
	}

	public void setEntreeUrl(String entreeUrl) {
		this.entreeUrl = entreeUrl;
	}

	public Coordonnee getPosition() {
		return position;
	}

	public void setPosition(Coordonnee position) {
		this.position = position;
	}

}