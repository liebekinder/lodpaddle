package src.Beans;

import java.util.ArrayList;
import java.util.List;

import src.core.Coordonnee;


public class Theme {

	String titre;
	String style;
	List<SousTheme> listeSousTheme;
	Coordonnee position;
	
	public Theme(String titre, String style, Coordonnee position) {
		this.titre = titre;
		this.style = style;
		this.position = position;
		listeSousTheme = new ArrayList<SousTheme>();
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public List<SousTheme> getListeSousTheme() {
		return listeSousTheme;
	}

	public void setListeSousTheme(List<SousTheme> listeSousTheme) {
		this.listeSousTheme = listeSousTheme;
	}
	
	public int nbSousTheme(){
		return listeSousTheme.size();
	}
	
	public void ajoutSousTheme(SousTheme sstheme){
		if(sstheme != null){
			sstheme.creeEntrees(position);
			listeSousTheme.add(sstheme);
		}
	}
	
	
	
}
