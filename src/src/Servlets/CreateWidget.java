package src.Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import src.Beans.Widget;
import src.core.CalculDistance;


public class CreateWidget extends HttpServlet{
	
	public static final String VUE = "/index.jsp";
			//    "/WEB-INF/afficherWidget.jsp";
    public String VILLE;
    public List<Widget> widgets=new ArrayList<Widget>();
    public int Compteur=1;
    public final String domain = "http://localhost:8080/lodpaddleTest/";
    public List<String> listIds= new ArrayList<String>();
    //public final String domain = "http://lodpaddle.univ-nantes.fr/lodpaddle/";
	
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
        /** Création des widgets **/
		
		String s= new String( "Pays : France \n"+" Region : Pays de la Loire \n "+"Departement : Loire Atlantique \n"+" Code commune : 44036 \n" );
		Widget commune = new Widget(10,"Nantes",300,400,"blue",s,"");
		
		
		String texteIntro= new String("LodPaddle est un service innovant qui premet de surfer à travers les données liées issues " +
								"de l'open data de la Région des Pays de la Loire , le département de Loire-Atlantique, de Nantes métropole et la ville de Nantes. <a href=\"header.jsp\">Mon lien de texte</a>");

		Widget introduction= new Widget(1,"Présentation",2, 1,"#C0C0C0",texteIntro,"red");
		Widget websemantique = new Widget(2,"Le Web Sémantique",2,1,"#C0C0C0","","blue");
		Widget endpoint = new Widget(3,"Accès développeurs",2,1, "#C0C0C0", "loulou","yellow");
		
		Widget culture = new Widget(4, "CULTURE",2,3,"green","","");
		Widget loisirs = new Widget(5, "LOISIRS",2,3,"blue","","");
		Widget services_pratique = new Widget(6, "SERVICES PRATIQUE",2,2,"red","","");
		Widget	a_visiter= new Widget(7,"À VISITER", 2,3,"brown", "","");
		Widget	villes = new Widget(8, "VILLES",1,1,"yellow","","");
		Widget	transport= new Widget(9,"TRANSPORT",2, 2,"purple","","");
		Widget presentationVille = new Widget(10,"Chateaubriant", 2,2, "pink",texteIntro,"");
		Widget monparcours= new Widget(11,"parcours", 1, 1,"","","");
		Widget jeu= new Widget(12,"jeu", 1, 1,"","","");

		websemantique.getContents().addLink("My link 1", "index.jsp",domain+"/media/marqueur.png");
		websemantique.getContents().addLink("My link 2", "footer.jspf",domain+"/media/marqueur.png");
		websemantique.getContents().addLink("My link 3", "footer.jspf",domain+"/media/marqueur.png");

		
		endpoint.setType("image");
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
		
		/*z.setType("presentation");
		z.setPicto(domain+"/media/hotel.jpg"); */
		//On ajoute qu'une fois
		if(Compteur==1){
		/** Premiere page**/
		//widgets.add(introduction);
		//widgets.add(websemantique);
		//widgets.add(endpoint);
		
		widgets.add(presentationVille);
		widgets.add(loisirs);
		widgets.add(jeu);
		widgets.add(monparcours);
		widgets.add(culture);
		widgets.add(services_pratique);
		widgets.add(a_visiter);
		widgets.add(transport);
		
		/** Page de widgets**/

		}
		
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
		request.setAttribute("idsForChange", listIds);
		
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
