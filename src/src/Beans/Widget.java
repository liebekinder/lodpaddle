package src.Beans;

import java.util.ArrayList;
import java.util.List;

public class Widget {

	private final int pitch = 162;
	private int id;
	private String theme;
	private int x;
	private int y;
	private String color;
	private String content;
	private String title;
	private String picto;
	private String background;  // utiliser le background pour le fond
	private WidgetContents contents;
	private List<WidgetContents> themeItems;
	private String type; /** Le type du widget : image, titre + image, thématique, simple **/
						// Faire une classe widget pour les thématique

	public Widget(int widgetid, String widgetTitle, int xCoord, int yCoord, String widgetColor, String widgetContent, String linksColor){
		
		id = widgetid;
		x = xCoord;
		y = yCoord;
		color = widgetColor;
		setContents(new WidgetContents(widgetContent, linksColor));
		themeItems= new ArrayList<WidgetContents>();
		title = widgetTitle;
		type="";
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
		return y*pitch;
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


	public WidgetContents getContents() {
		return contents;
	}


	public void setContents(WidgetContents contents) {
		this.contents = contents;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getBackground() {
		return background;
	}


	public void setBackground(String background) {
		this.background = background;
	}


	public String getTheme() {
		return theme;
	}


	public void setTheme(String theme) {
		this.theme = theme;
	}


	public void addthemeItems(WidgetContents thm){
		themeItems.add(thm);
	}
	
	public List<WidgetContents> getThemeItems() {
		return themeItems;
	}


	public void setThemeItems(List<WidgetContents> themeItems) {
		this.themeItems = themeItems;
	}

	
}
