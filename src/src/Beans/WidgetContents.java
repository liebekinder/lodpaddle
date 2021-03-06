package src.Beans;

import java.util.ArrayList;
import java.util.List;

public class WidgetContents {
	
	private String contentTexte;
	private List<String> contentImages;
	private List<WidgetLink> contentLinks;
	private String linkColor;
	private String themePicto;
	private String theme;
	
	public WidgetContents(String texte, String linksColor){
		
		contentTexte = texte;
		contentImages = new ArrayList<String>();
		contentLinks = new ArrayList<WidgetLink>();
		linkColor = linksColor;
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
	

	public void addLink(String linktext, String linkuri, String image){
		
		WidgetLink wl= new WidgetLink(linktext, linkuri,image);
		contentLinks.add(wl);
	}
	
	public void addImage(String imageUrl){
		
		contentImages.add(imageUrl);
	}

	public String getLinkColor() {
		return linkColor;
	}

	public void setLinkColor(String linkColor) {
		this.linkColor = linkColor;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getThemePicto() {
		return themePicto;
	}

	public void setThemePicto(String themePicto) {
		this.themePicto = themePicto;
	}
	
}
