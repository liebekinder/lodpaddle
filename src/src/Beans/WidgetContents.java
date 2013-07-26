package src.Beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WidgetContents {
	
	private String contentTexte;
	private List<String> contentImages;
	private List<WidgetLink> contentLinks;
	private String linkColor;
	private Map<String,String> blocks; //pour faire les blocs
	
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
	
}
