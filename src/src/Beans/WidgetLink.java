package src.Beans;

public class WidgetLink {
	
	private String linkText;
	private String linkUri;
	private String linkImage;
	
	public WidgetLink(String text, String uri, String image){
		
		linkText = text;
		linkUri = uri;
		linkImage= image;
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

	public String getLinkImage() {
		return linkImage;
	}

	public void setLinkImage(String linkImage) {
		this.linkImage = linkImage;
	}

}
