package game;

import java.util.ArrayList;
import java.util.List;

public class Jeu {

	public int avancement;
	public List<Ville> villes;
	public int score;
	public int nbCycle = 10;
	
	public Jeu(){
		avancement = 1;
		villes = new ArrayList<Ville>();
		score = 0;
	}
	
	public void trouveVilles() {
		//implemented in subclasses
		System.out.println("problem d'héritage si ce message s'affiche");
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
	
}
