package src.core;

public class Coordonnee {

	double longitude;
	double latitude;
	
	public Coordonnee(double longitude, double latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
	public Coordonnee(String longitude, String latitude) {
		this.longitude = Double.valueOf(longitude);
		this.latitude = Double.valueOf(latitude);
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	
}
