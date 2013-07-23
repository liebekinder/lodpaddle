package src.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.Beans.Widget;


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
    public final String domain = "http://localhost:8080/lodpaddleTest/";
//    public final String domain = "http://lodpaddle.univ-nantes.fr/lodpaddle/";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
        /** Création des widgets **/
		
		String s= new String( "Pays : France \n"+" Region : Pays de la Loire \n "+"Departement : Loire Atlantique \n"+" Code commune : 44036 \n" );
		Widget commune = new Widget(10,"Nantes",300,400,"blue",s,"");
		
		
		String texteIntro= new String("LodPaddle est un service innovant qui premet de surfer à travers les données liées issues " +
								"de l'open data de la Région des Pays de la Loire , le département de Loire-Atlantique, de Nantes métropole et la ville de Nantes. <a href=\"header.jsp\">Mon lien de texte</a>");

		Widget introduction= new Widget(1,"Présentation",2, 1,"#C0C0C0",texteIntro,"red");
		Widget websemantique = new Widget(2,"Le Web Sémantique",2,1,"#C0C0C0","","blue");
		Widget endpoint = new Widget(3,"Accès développeurs",2,1, "#C0C0C0", "loulou","yellow");

		Widget a= new Widget(1,"Présentation",4, 2,"#C0C0C0",texteIntro,"red");
		Widget z = new Widget(2,"Hotels",1,1,"#C0C0C0","lala","blue");
		Widget e = new Widget(3,"Accès développeurs",2,2, "#C0C0C0", "loulou","yellow");
		
		

		websemantique.getContents().addLink("My link 1", "index.jsp",domain+"/media/marqueur.png");
		websemantique.getContents().addLink("My link 2", "footer.jspf",domain+"/media/marqueur.png");
		websemantique.getContents().addLink("My link 3", "footer.jspf",domain+"/media/marqueur.png");

		
		endpoint.setType("image");
		endpoint.setPicto(domain+"media/nantes.jpg");
		
		z.setType("presentation");
		z.setPicto(domain+"/media/hotel.jpg");
		//On ajoute qu'une fois
		if(Compteur==1){
		widgets.add(introduction);
		widgets.add(websemantique);
		widgets.add(endpoint);
		widgets.add(a);
		widgets.add(z);
		widgets.add(e);
		}
		
		// IL FAUT AJOUTER LE TYPE DES WIDGET=> SI CLIQUABLE ET OU SA REDIRIGE
		// IL FAUT MODIFIER LE CONTENU POUR QU'IL SOIT DE PLUSIEURS TYPES
		
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
		
		//ZONE TEST A COMMENTER
		
//		double lat = 47.716743;
//		double lon = -1.376516;
//		int distance = 1 ;
//		
//		String test = CalculDistance.allQuery(distance, lat, lon, "?lat", "?long", "?d2brgrad");
//		request.setAttribute("TEST", test);
		
		//FIN ZONE DE TEST
		
    	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    	Compteur=Compteur+1;
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
        /** Création des objets **/
		

		
		/**  Affichage de la vue **/
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );

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
