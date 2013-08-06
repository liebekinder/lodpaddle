package src.Beans;

public class Lien {
	String lienNom;
	String lienUrl;

	public Lien(String lienNom, String lienUrl) {
		this.lienNom = lienNom;
		this.lienUrl = lienUrl;
	}

	public String getLienNom() {
		return lienNom;
	}

	public void setLienNom(String lienNom) {
		this.lienNom = lienNom;
	}

	public String getLienUrl() {
		return lienUrl;
	}

	public void setLienUrl(String lienUrl) {
		this.lienUrl = lienUrl;
	}
}