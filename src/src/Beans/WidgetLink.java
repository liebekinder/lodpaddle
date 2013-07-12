package src.Beans;

public class WidgetLink {
	
	private String linkText;
	private String linkUri;
	
	public WidgetLink(String text, String uri){
		
		linkText = text;
		linkUri = uri;
	} 

	public String getLinkText() {
		return linkText;
	}

	public void setLinkText(String linkText) {
		this.linkText = linkText;
	}

	public String getLinkUri() {
		return linkUri;
	}

	public void setLinkUri(String linkUri) {
		this.linkUri = linkUri;
	}

}
