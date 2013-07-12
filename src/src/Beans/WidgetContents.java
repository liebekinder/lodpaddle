package src.Beans;

import java.util.ArrayList;
import java.util.List;

public class WidgetContents {
	
	private String contentTexte;
	private List<String> contentImages;
	private List<WidgetLink> contentLinks;
	
	public WidgetContents(String texte){
		
		contentTexte = texte;
		contentImages = new ArrayList<String>();
		contentLinks = new ArrayList<WidgetLink>();
	}

	public String getContentTexte() {
		return contentTexte;
	}

	public void setContentTexte(String contentTexte) {
		this.contentTexte = contentTexte;
	}

	public List<String> getContentImages() {
		return contentImages;
	}

	public void setContentImages(List<String> contentImages) {
		this.contentImages = contentImages;
	}

	public List<WidgetLink> getContentLinks() {
		return contentLinks;
	}

	public void setContentLinks(List<WidgetLink> contentLinks) {
		this.contentLinks = contentLinks;
	}
	

	
	
}
