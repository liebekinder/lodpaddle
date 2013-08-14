package src.Servlets;

import game.Jeu;
import game.JeuLA;
import game.JeuNM;
import game.JeuPDLL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import src.Beans.MyWidget;
import src.core.CalculDistance;

/**
 * Servlet implementation class Game
 */
@WebServlet("/Game")
public class Game extends HttpServlet {

	public final String domain = "http://localhost:8080/lodpaddleTest/";
//	public final String domain = "http://lodpaddle.univ-nantes.fr/lodpaddle/";

	private static final long serialVersionUID = 1L;
	public Jeu monJeu;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Game() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String VUE = "/game.jsp";

		String jeu = request.getParameter("typeJeu");
		if (jeu == null || jeu == "") {

			List<MyWidget> widgets = new ArrayList<MyWidget>();

			MyWidget nantesMetropole = new MyWidget("Nantes métropôle",
					getTexte(1), "1", "#e75735", 200);
			MyWidget loireAtlantique = new MyWidget("Web_sémantique",
					getTexte(2), "2", "#e75735", 200);
			MyWidget paysDeLaLoire = new MyWidget("Accès_développeurs",
					getTexte(3), "3", "#e75735", 200);

			widgets.add(nantesMetropole);
			widgets.add(loireAtlantique);
			widgets.add(paysDeLaLoire);

			request.setAttribute("widgets", widgets);
			request.setAttribute("typePage", "accueil");
		} else {
			String jeuEnCoursTemp = request.getParameter("jeuEnCours");
			if (jeuEnCoursTemp == null || jeuEnCoursTemp == "") {
				int jeuId = Integer.valueOf(jeu);
				// Le jeu est lancé, on crée son initialisation
				switch (jeuId) {
				case 1:
					monJeu = new JeuNM();
					break;
				case 2:
					monJeu = new JeuLA();
					break;
				case 3:
					monJeu = new JeuPDLL();
					break;
				default:
					monJeu = null;
				}
				monJeu.trouveVilles();

			} else {
				// on est déjà dans une phase de jeu
			}

			request.setAttribute("typeJeu", jeu);
			request.setAttribute("jeuEnCours", true);
		}

		request.setAttribute("domain", domain);
		this.getServletContext().getRequestDispatcher(VUE)
				.forward(request, response);
	}

	private String getTexte(int i) {
		String lieu;
		switch (i) {
		case 1:
			lieu = "de Nantes Métropôle";
			break;
		case 2:
			lieu = "de la Loire-Atlantique";
			break;
		case 3:
			lieu = "des Pays de la Loire";
			break;
		default:
			lieu = "Erreur";
			break;
		}
		String retour = new String(
				"<div class=\"jeuWidgetTitre\">Jouer au niveau " + lieu
						+ "</div>" + "<div class=\"jeuWidgetButton\">"
						+ "	<form method=\"get\" action=\"" + domain
						+ "Game\">"
						+ "<input type=\"hidden\" name=\"typeJeu\" value=\""
						+ i + "\"/>"
						+ "	<input type=\"submit\" value=\"jouer\" />"
						+ "</form></div>");
		return retour;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		if (request.getParameter("ajax") != null) {
			if (request.getParameter("ville") != null) {
				response.getWriter().write(getVille());
			} else {
				if (request.getParameter("score") != null) {
					response.getWriter().write(getScore());
				} else {
					response.getWriter().write(
							getReponse(request.getParameter("resultatLon"),
									request.getParameter("resultatLat"),
									request.getParameter("temps")));
				}
			}
		}
	}

	private String getScore() {
		String json = new String("{\n" + "\"total\":\""
				+ monJeu.getScore() + "\"\n" + "}");

		return json;
	}

	private String getVille() {
		String json = new String("{\n" + "\"ville\":\""
				+ monJeu.getVilleCourante() + "\"\n" + "}");

		return json;
	}

	private String getReponse(String lon, String lat, String tps) {
		// System.out.println(lat);
		double dist = CalculDistance.distanceVolOiseauKM(Double.valueOf(lat),
				Double.valueOf(lon), monJeu.getLatCourante(),
				monJeu.getLonCourante());
		int score = calculScore(dist, tps, monJeu.getType());
		String json = new String("{\n" + "\"points\":" + score + ",\n"
				+ "\"total\":" + monJeu.getScore() + ",\n" + "\"ville\":\""
				+ monJeu.getVilleCourante() + "\",\n" + "\"trueLon\":"
				+ monJeu.getLonCourante() + ",\n" + "\"trueLat\":"
				+ monJeu.getLatCourante() + ",\n" + "\"distance\":" + dist
				+ "\n" + "}");

		monJeu.augmenteScore(score);
		monJeu.avance();
		return json;
	}

	private int calculScore(double dist, String tps, int type) {
		double score = 0;
		switch(type){
		case 3:
			score = (-2000 * dist + 100000) >= 0 ? -2000 * dist + 100000 : 0;
			// on prend une fraction de ce score en fonction du temps passé à
			// répondre
			score = score - (score * (Double.valueOf(tps) / 10));
			break;
		case 2:
			score = (-4000 * dist + 100000) >= 0 ? -4000 * dist + 100000 : 0;
			// on prend une fraction de ce score en fonction du temps passé à
			// répondre
			score = score - (score * (Double.valueOf(tps) / 10));
			break;
		case 1:
			score = (-10000 * dist + 100000) >= 0 ? -10000 * dist + 100000 : 0;
			// on prend une fraction de ce score en fonction du temps passé à
			// répondre
			score = score - (score * (Double.valueOf(tps) / 10));
			break;
		default:
			break;
		}
		return (int) score;
	}

}
