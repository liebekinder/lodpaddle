package src.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Resultat {
	List<HashMap<String,String>> resultats;
	
	public Resultat(){
		resultats = new ArrayList<HashMap<String,String>>();
	}
	
	public boolean estVide(){
		return resultats.size() <= 0;
	}
	
	public void ajoutResultat(HashMap<String,String> resultat){
		if(resultat != null){
			resultats.add(resultat);
		}
		else{
			System.out.println("resultat null");
		}
	}

	public int taille() {
		return resultats.size();
	}
	
	public HashMap<String,String> at(int pos){
		if(pos>=taille()) return null;
		Iterator<HashMap<String,String>> it = resultats.iterator();
		int i=0;
		while(it.hasNext() && i<pos){
			it.next();
			++i;
		}
		return it.next();
	}
	
	public Iterator<HashMap<String,String>> iterator(){
		return resultats.iterator();
	}
}
