package src.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

public class WikiMediaImage {

	public final static String API = "http://fr.wikipedia.org/w/api.php";
	
	public static List<String> getUrlImage(String nom){
		InputStream stream = null;
		URL url_;
		
		List<String> listeUrl = new ArrayList<String>();
		try {
			url_ = new URL(API+"?callback=?&action=query&titles="+nom+"&prop=images&format=json");
		URLConnection connection = url_.openConnection();
		stream = connection.getInputStream();
		
		JSONObject json = new JSONObject(convertStreamToString(stream));
		
		json = json.getJSONObject("query").getJSONObject("page");
		
		Iterator<Object> it = json.keys();
		String cle="";
		while(it.hasNext()){
			cle = (String) it.next();
		}
		
		json = json.getJSONObject(cle);
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listeUrl;
	}
	
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    while ((line = reader.readLine()) != null) {
	      sb.append(line + "\n");
	    }
	    is.close();
	    return sb.toString();
	  }
}
