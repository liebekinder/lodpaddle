package src.core;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class CalculDistance {

	// ZONE TEST A COMMENTER

	// double lat = 47.716743;
	// double lon = -1.376516;
	// int distance = 1 ;
	//
	// String test = CalculDistance.allQuery(distance, lat, lon, "?lat",
	// "?long", "?d2brgrad");
	// request.setAttribute("TEST", test);

	// FIN ZONE DE TEST

	final static double brgrad = 111.1949;
	final static double phi = 47.0;

	public CalculDistance() {
	}

	private static double cac2() {
		return Math.cos(phi) * Math.sin(phi) * (Math.PI / 180);
	}

	private static double cac1(double X) {
		return Math.cos(phi)
				* (Math.cos(phi) - Math.sin(phi) * (Math.PI / 180)
						* (X - 2 * phi));
	}

	public static String filterString(int radius_, double lat_, double lon_,
			String lats, String lons) {
		return "FILTER (" + laFormule(radius_, lat_, lon_, lats, lons) + " < "
				+ calculZ(radius_) + " ) .";
	}

	public static String selectPartString(int radius_, double lat_,
			double lon_, String lats, String lons) {
		return "(" + laFormule(radius_, lat_, lon_, lats, lons) + ")";
	}

	private static double calculZ(int radius_) {
		return (radius_ / brgrad) * (radius_ / brgrad);
	}

	private static String laFormule(int radius_, double lat_, double lon_,
			String lats, String lons) {
		return "(" + lat_ + "-xsd:float(" + lats + "))*(" + lat_
				+ "-xsd:float(" + lats + ")) + (" + lon_ + "-xsd:float(" + lons
				+ "))*(" + lon_ + "-xsd:float(" + lons + "))*(" + cac1(lat_)
				+ "-(" + cac2() + "*xsd:float(" + lats + ")))";
	}

	public static String allQuery(int radius_, double lat_, double lon_,
			String lats, String lons) {
		return "SELECT ?nom "
				+ selectPartString(radius_, lat_, lon_, lats, lons) + "\n"
				+ "WHERE{" + "\n" + "?sub geo:lat " + lats + ".\n"
				+ "?sub geo:long" + lons + ".\n" + "?sub foaf:name ?nom"
				+ ".\n" + filterString(radius_, lat_, lon_, lats, lons) + "\n"
				+ "}" + "\n" + "ORDER BY ASC 2";
	}

	public static double distanceVolOiseauKM(
			double lat1, double lon1, double lat2, double lon2) {

		// d=2*asin(sqrt((sin((lat1-lat2)/2))^2 +
		// cos(lat1)*cos(lat2)*(sin((lon1- lon2)/2))^2))

//		return
//		2 * Math.asin(
//		Math.sqrt(
//		Math.pow((Math.sin((lat1 - lat2) / 2)), 2)
//		+
//		Math.cos(lat1) * Math.cos(lat2) *
//		(Math.pow(Math.sin(((lon1 - lon2) / 2)), 2))
//		)
//		);
		
		LatLng point1 = new LatLng(lat1, lon1);
		LatLng point2 = new LatLng(lat2, lon2);
		return LatLngTool.distance(point1, point2, LengthUnit.KILOMETER);

	}
	
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double sindLat = Math.sin(dLat / 2);
	    double sindLng = Math.sin(dLng / 2);
	    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
	            * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    return dist;
	    }
}
