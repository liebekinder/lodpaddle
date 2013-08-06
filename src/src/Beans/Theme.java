package src.Beans;

import java.util.ArrayList;
import java.util.List;


public class Theme {

	String titre;
	String style;
	List<SousTheme> listeSousTheme;
	
	public Theme(String titre, String style) {
		this.titre = titre;
		this.style = style;
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
			//sstheme.creeLiens();
			sstheme.creeLienTemp();
			listeSousTheme.add(sstheme);
		}
	}
	
	
	
}
