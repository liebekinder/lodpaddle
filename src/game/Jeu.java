package game;

import java.util.ArrayList;
import java.util.List;

public class Jeu {

	public int avancement;
	public List<Ville> villes;
	public int score;
	public int nbCycle = 10;
	public int type;

	public Jeu(){
		avancement = 1;
		villes = new ArrayList<Ville>();
		score = 0;
	}
	
	public boolean trouveVilles() {
		//implemented in subclasses
		System.out.println("problem d'héritage si ce message s'affiche");
		return false;
	}
	
	public void initialiseJeu(){
		trouveVilles();
	}
	
	public void avance(){
		if(avancement<nbCycle) avancement++;
	}
	
	public int getAvancement(){
		return avancement;
	}
	
	public String getVilleCourante(){
		return villes.get(avancement-1).getNom();
	}
	
	public double getLatCourante(){
		return villes.get(avancement-1).getLat();
	}
	
	public double getLonCourante(){
		return villes.get(avancement-1).getLon();
	}
	
	public void augmenteScore(int val){
		score += val;
	}

	public int getScore() {
		return score;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
