package src.Beans;

public class Widget {

	private int id;
	private int x;
	private int y;
	private String color;
	private String content;
	private String title;
	private String picto;

	public Widget(int widgetid, String widgetTitle, int xCoord, int yCoord, String widgetColor, String widgetContent){
		
		id = widgetid;
		x = xCoord;
		y = yCoord;
		color = widgetColor;
		content = widgetContent;
		title = widgetTitle;
	}


	public int getId() {
		return id;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getPicto() {
		return picto;
	}


	public void setPicto(String picto) {
		this.picto = picto;
	}

	
}
