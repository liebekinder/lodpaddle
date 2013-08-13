package game;


public class Ville {

	private String nom;
	private String insee;
	private double lon;
	private double lat;
	
	public Ville(String nom, String insee, double lon, double lat) {
		this.nom = nom;
		this.insee = insee;
		this.lon = lon;
		this.lat = lat;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getInsee() {
		return insee;
	}

	public void setInsee(String insee) {
		this.insee = insee;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	
}
