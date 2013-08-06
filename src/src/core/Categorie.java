package src.core;

public enum Categorie {

	RESTAURANT, HOTEL, GOLF, SPORT, PLAGE;
	
	public String getRequete(){
		switch(this){
		case RESTAURANT:
			return restaurant();
		case HOTEL:
			return hotel();
		case GOLF:
			return golf();
		case SPORT:
			return sport();
		case PLAGE:
			return plage();
		default:
			return null;
		}
	}

	private String plage() {
		// TODO Auto-generated method stub
		return null;
	}

	private String sport() {
		// TODO Auto-generated method stub
		return null;
	}

	private String golf() {
		// TODO Auto-generated method stub
		return null;
	}

	private String hotel() {
		// TODO Auto-generated method stub
		return null;
	}

	private String restaurant() {
		// TODO Auto-generated method stub
		return null;
	}
}
