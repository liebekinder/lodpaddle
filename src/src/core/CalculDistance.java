package src.core;

public class CalculDistance {

	final static double brgrad = 111.1949;
	final static double phi = 47.0;

	public CalculDistance() {
	}

	private static double cac2() {
		return Math.cos(phi) * Math.sin(phi) * (Math.PI / 180);
	}

	private static double cac1(double X) {
		return Math.cos(phi) * ( Math.cos(phi) - Math.sin(phi) *(Math.PI/180)*(X - 2*phi) )   ;
	}

	public static String filterString(int radius_, double lat_, double lon_,
			String lats, String lons) {
		return "FILTER (" + laFormule(radius_, lat_, lon_, lats, lons) + " < "
				+ calculZ(radius_) + " ) .";
	}
	
	public static String selectPartString(int radius_, double lat_, double lon_,
			String lats, String lons, String varName) {
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
			String lats, String lons, String varName){
		return "SELECT ?nom "+selectPartString(radius_, lat_, lon_, lats, lons, varName)+"\n" +
				"WHERE{" + "\n" +
				"?sub geo:lat " + lats + ".\n" +
				"?sub geo:long" + lons + ".\n" +
				"?sub foaf:name ?nom" + ".\n" +
				filterString(radius_, lat_, lon_, lats, lons) + "\n" +
				"}" + "\n" +
				"ORDER BY ASC 2";
	}
}
