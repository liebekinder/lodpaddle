package src.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.Beans.Widget;
import src.Beans.WidgetContents;


public class CreateWidget extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String VUE = "/index.jsp";
			//    "/WEB-INF/afficherWidget.jsp";
    public String VILLE;
    public List<Widget> widgets=new ArrayList<Widget>();
    public int Compteur=1;
    public List<String> listIds= new ArrayList<String>();
    public final String domain = "http://localhost:8080/lodpaddleTest/";
//    public final String domain = "http://lodpaddle.univ-nantes.fr/lodpaddle/";
    public List<Widget> thematiques= new ArrayList<Widget>();

	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
        /** Création des widgets **/
		

		request.setAttribute("page", "accueil");
		
		if (request.getAttribute("page")!="accueil"){
			themeManagmement(request, response);
		}
		
		String s= new String( "Pays : France \n"+" Region : Pays de la Loire \n "+"Departement : Loire Atlantique \n"+" Code commune : 44036 \n" );
		Widget commune = new Widget(10,"Nantes",300,400,"blue",s,"");
		
		
		String texteIntro= new String("LodPaddle est un <br /> service innovant qui premet de surfer <br /> de <a href=\"blabla.jsp\"> l'open data de la Région des Pays de la Loire </a> ");

		Widget introduction= new Widget(1,"Présentation",200, 1,"#1abc9c",texteIntro,"red");
		Widget websemantique = new Widget(2,"Le Web Sémantique",200,1,"#2ecc71","","blue");
		Widget endpoint = new Widget(3,"Accès développeurs",200,1, "#5dabe3", "","yellow");
		
		Widget culture = new Widget(4, "CULTURE",2,3,"green","","");
		Widget loisirs = new Widget(5, "LOISIRS",2,3,"blue","","");
		Widget services_pratique = new Widget(6, "SERVICES PRATIQUE",2,2,"red","","");
		Widget	a_visiter= new Widget(7,"À VISITER", 2,3,"brown", "","");
		Widget	villes = new Widget(8, "VILLES",1,1,"yellow","","");
		Widget	transport= new Widget(9,"TRANSPORT",2, 2,"purple","","");
		Widget presentationVille = new Widget(10,"Chateaubriant", 2,2, "pink",texteIntro,"");
		Widget monparcours= new Widget(11,"parcours", 1, 1,"","","");
		Widget jeu= new Widget(12,"jeu", 1, 1,"","","");

		//websemantique.getContents().addLink("My link 1", "index.jsp",domain+"/media/marqueur.png");
		//websemantique.getContents().addLink("My link 2", "footer.jspf",domain+"/media/marqueur.png");
		//websemantique.getContents().addLink("My link 3", "footer.jspf",domain+"/media/marqueur.png");

		
		endpoint.setType("presentation");
		//introduction.setType("presentation");
		
		endpoint.setPicto(domain+"media/nantes.jpg");
		
		
		presentationVille.setType("presentation");
		presentationVille.setBackground(domain+"media/presentation.png");
		presentationVille.setTheme("presentationVille");
		culture.setType("image");
		culture.setTheme("culture");
		loisirs.setType("image");
		loisirs.setTheme("loisirs");
		services_pratique.setType("image");
		services_pratique.setTheme("servicesPratique");
		a_visiter.setType("image");
		a_visiter.setTheme("aVisiter");
		villes.setType("image");
		villes.setTheme("villes");
		transport.setType("image");
		transport.setTheme("transport");
		monparcours.setType("image");
		monparcours.setTheme("monParcours");
		jeu.setType("image");
		jeu.setTheme("Jeu");
		culture.setPicto(domain+"media/culture.png");
		loisirs.setPicto(domain+"media/loisirs.png");
		monparcours.setPicto(domain+"media/parcours.png");
		jeu.setPicto(domain+"media/jeu.png");
		services_pratique.setPicto(domain+"media/servicePratique.png");
		a_visiter.setPicto(domain+"media/aVisiter.png");
		transport.setPicto(domain+"media/transport.png");
		

		//if(Compteur==1){
		/** Premiere page**/
		widgets.add(introduction);
		widgets.add(websemantique);
		widgets.add(endpoint);
		
		/*widgets.add(presentationVille);
		widgets.add(loisirs);
		widgets.add(jeu);
		widgets.add(monparcours);
		widgets.add(culture);
		widgets.add(services_pratique);
		widgets.add(a_visiter);
		widgets.add(transport);*/
		
		/** Page de widgets**/

		//}
		
		/** Ajout les themes dans la liste **/
		
		for(Widget wg: widgets){
			listIds.add(wg.getTheme());
		}
		
        /** Stockage du bean dans l'objet request **/
		
		VILLE="communes";
		int taille;
		if(!widgets.isEmpty()){
			taille= widgets.size();
		}else{
			taille=0;
		}
		
		request.setAttribute(VILLE,widgets);
		request.setAttribute("Compteur",Compteur);
		request.setAttribute("taille",taille);
		request.setAttribute("domain", domain);
		request.setAttribute("idsForChange",".loisir");
		
		//ZONE TEST A COMMENTER
		
//		double lat = 47.716743;
//		double lon = -1.376516;
//		int distance = 1 ;
//		
//		String test = CalculDistance.allQuery(distance, lat, lon, "?lat", "?long", "?d2brgrad");
//		request.setAttribute("TEST", test);
		
		//FIN ZONE DE TEST
		
    	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    	widgets.clear();
    	Compteur=Compteur+1;
	}

	private void themeManagmement(HttpServletRequest request,
			HttpServletResponse response) {
		//this function manage static theme
		List<String> listeTheme = new ArrayList<String>();
		listeTheme.add("loisir");
		listeTheme.add("culture");
		listeTheme.add("ville");
		listeTheme.add("service");
		listeTheme.add("visite");
		listeTheme.add("transport");	
		
		int nbCol =(listeTheme.size() % 2 == 0) ? listeTheme.size()/2 : listeTheme.size()/2+1;
		
		request.setAttribute("theme",listeTheme);
		request.setAttribute("nbCol",nbCol);
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		themeManagmement(request, response);
		/* Récupérer le nom de la ville request.getParameter( nomChamp );*/

		
		String nomVille= request.getParameter("saisie");

		System.out.println(nomVille);
		
		if(nomVille != null && nomVille != ""){
			themeManagmement(request, response);
		}
		
        /** widgets footer **/
		
		String ficheVilleText;
         
		Widget ficheVille;
		Widget tweetVille;
		Widget photoVille;
		Widget photoBateau;
		
		ficheVilleText="<div style=\"width: 53%; float:left; position: relative; height:100%; line-height: 1.4;\"><b>Administration</b>" +
				"<br />Département : Loire - Atlantique" +
				"<br />Maire : Alain Humault " +
				"<br />Mandat : 2008 - 2014 " +
				"<br />Site web : http://www.marie-chateaubriant.fr " +
				"</div>" +
				"<div style=\"width: 45%;float:left; position: relative; height:100%; line-height: 1.2;\"><b>Démographie</b> " +
				"<br />Les habitants s'appellent les cantonnais." +
				"<br />La population est de 10000 personnes pour une densité de 258 hab au km2." +
				"<br />" +
				"<br />" +
				"<b>Geographie</b> " +
				"<br />Altitude :" +
				"<br />Superficie : " +
				"</div>";
		ficheVille= new Widget(20,nomVille,550,0,"#2980b9",ficheVilleText,"");
		tweetVille = new Widget(21,"",200,0,"#2980b9", "", "");
		photoVille = new Widget(22,"Photos",200,0, "#2980b9", "", "");
		photoBateau = new Widget(23,"",300,0, "#2980b9", "", "");
		tweetVille.setBackground(domain+"media/tweet.png");
		photoVille.setType("image");
		photoVille.setPicto(domain+"media/nantesTown.jpg");
		photoBateau.setType("image");
		photoBateau.setPicto(domain+"media/photoBateau.jpg");
		
        /** widget items for flip  **/
		Widget loisir= new Widget(25,"Loisir",0,0,"","","");
		String texteRestau;
		String texteHotel;
		WidgetContents restaurants;
		WidgetContents Hotels;
		WidgetContents Golf;
		WidgetContents Plages;
		WidgetContents  sportLoisir;
		
		texteRestau="Le poellon d'or <br />" +
				"Hotelerie de la ferrerie <br />" +
				"Plusieurs gourmands <br />";
		
		
		restaurants = new WidgetContents(texteRestau,"");
		restaurants.setTheme("RESTAURANT");
		restaurants.setThemePicto(domain+"/media/restaurantPicto.png");
		restaurants.addLink("Le poellon d'or","index.jsp","");
		restaurants.addLink("Hotelerie de la ferrerie","index.jsp","");
		restaurants.addLink("Plusieurs gourmands","index.jsp","");
		
		Hotels = new WidgetContents("","");
		Hotels.setTheme("HOTELS");
		Hotels.setThemePicto(domain+"/media/hotelPicto.png");
		Hotels.addLink("le Chateau de vincenne","index.jsp","");
		Hotels.addLink("baf hotel","index.jsp","");
		Hotels.addLink("le relais de la grange","index.jsp","");
		
		Golf = new WidgetContents("","");
		Golf.setTheme("GOLF");
		Golf.setThemePicto(domain+"/media/golfPicto.png");
		Golf.addLink("Golf de carquefou","index.jsp","");
		Golf.addLink("Golf de jump up","index.jsp","");
		Golf.addLink("Golf de vander woodsen","index.jsp","");
		
		Plages = new WidgetContents("","");
		Plages.setTheme("PLAGES");
		Plages.setThemePicto(domain+"/media/plagePicto.png");
		Plages.addLink("Pornic","index.jsp","");
		Plages.addLink("La baule les pins","index.jsp","");
		Plages.addLink("Plages du cantonnais","index.jsp","");
		
		sportLoisir = new WidgetContents("","");
		sportLoisir.setTheme("SPORT ET LOISIRS");
		sportLoisir.setThemePicto(domain+"/media/sportPicto.png");
		sportLoisir.addLink("Stade de la tourniere","index.jsp","");
		sportLoisir.addLink("Tennis municipaux maine et Loire","index.jsp","");
		sportLoisir.addLink("Gymnase Aman du blac","index.jsp","");

		loisir.addthemeItems(restaurants);
		loisir.addthemeItems(Hotels);
		loisir.addthemeItems(Golf);
		loisir.addthemeItems(Plages);
		loisir.addthemeItems(sportLoisir);
		
        /* Stockage de la réponse et du bean (widget)  dans l'objet request */

		if(nomVille!=""){
		widgets.clear();
		widgets.add(ficheVille);
		widgets.add(tweetVille);
		widgets.add(photoVille);
		widgets.add(photoBateau);
		}
			
		if(widgets.isEmpty()==false){
			request.setAttribute(VILLE,widgets);
		}
		
		if(loisir.getThemeItems().isEmpty()==false){
			request.setAttribute("themeLoisir",loisir);
		}
		
		request.setAttribute("page","affichageDetail");
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        widgets.clear();
        Compteur=Compteur+1;
	}
	
	public void addInList(Widget w){
				
		if(w!=null && widgets==null){
			widgets.add(w);
		}
		else{
			for( Widget widg : widgets){
				if (!(widg.getTitle().equals(w.getTitle()))){
					widgets.add(w);
				}
			} 
		}
	}
	
}
