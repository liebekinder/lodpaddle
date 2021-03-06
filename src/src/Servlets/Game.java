package src.Servlets;

import game.HighScore;
import game.Jeu;
import game.JeuLA;
import game.JeuNM;
import game.JeuPDLL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import src.Beans.MyWidget;
import src.core.CalculDistance;

/**
 * Servlet implementation class Game
 */
@WebServlet("/Game")
public class Game extends HttpServlet {

//	public final String domain = "http://localhost:8080/lodpaddle/";
	// public final String domain = "http://localhost:8081/lodpaddle/";
	 public final String domain =
	 "http://lodpaddle.univ-nantes.fr/lodpaddle/";

	private static final long serialVersionUID = 1L;

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
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");

		String VUE = "/game.jsp";
		String ERROR = "/error.jsp";
		boolean erreur = false;
		Jeu monJeu;
		// on créer une variable de session
		HttpSession session = request.getSession();

		String fini = request.getParameter("fini");
		if (fini == null || fini == "") {
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
				// on est rentré dans une sous catégorie de jeu
				String jeuEnCoursTemp = request.getParameter("jeuEnCours");
				int jeuId = Integer.valueOf(jeu);

				if (jeuEnCoursTemp == null || jeuEnCoursTemp == "") {
					// Le jeu est lancé, on crée son initialisation
					switch (jeuId) {
					case 1:
						monJeu = new JeuNM();
						session.setAttribute("monJeu", monJeu);
						break;
					case 2:
						monJeu = new JeuLA();
						session.setAttribute("monJeu", monJeu);
						break;
					case 3:
						monJeu = new JeuPDLL();
						session.setAttribute("monJeu", monJeu);
						break;
					default:
						monJeu = null;
					}
					if (monJeu != null) {
						if (!monJeu.trouveVilles()) {
							erreur = true;
						} else {
							session.setAttribute("monJeu", monJeu);
						}
					} else {
						erreur = true;
					}

				} else {
					// on est déjà dans une phase de jeu
				}

				request.setAttribute("typeJeu", jeu);
				request.setAttribute("jeuEnCours", true);
				request.setAttribute("session", session.getId());
			}

			request.setAttribute("domain", domain);

			if (erreur)
				this.getServletContext().getRequestDispatcher(ERROR)
						.forward(request, response);
			else
				this.getServletContext().getRequestDispatcher(VUE)
						.forward(request, response);
		} else {
			// le jeu est fini
			if (session.getAttribute("monJeu") != null) {
				monJeu = (Jeu) session.getAttribute("monJeu");
				response.sendRedirect(domain + "?retourJeu="
						+ URLEncoder.encode(monJeu.getRequestData(), "UTF-8"));
			} else {
				response.sendRedirect(domain);
			}
		}
	}

	private String getTexte(int i) {
		String lieu;
		switch (i) {
		case 1:
			lieu = "de Nantes Métropole";
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
				"<div class=\"jeuWidgetTitre\">Localisez 10 communes "
						+ lieu
						+ "</div>"
						+ "<div class=\"jeuWidgetButton\">"
						+ "<a href=\"#\" class=\"boutonJeu2\"	onclick=\"document.location.href='"
						+ domain + "Game?typeJeu=" + i + "'\">Jouer</a>"
						+ "</div>");
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
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");

		Jeu monJeu = null;

		// on récupère la session
		HttpSession session = request.getSession();

		if (session.getAttribute("monJeu") != null) {
			// //on récupère la variable de session
			// en fonction du type
			// System.out.println(request.getParameter("type"));
			int jeuId = 0;
			if (request.getParameter("type") != null) {
				jeuId = Integer.valueOf(request.getParameter("type"));
			}
			switch (jeuId) {
			case 1:
				monJeu = (JeuNM) session.getAttribute("monJeu");
				break;
			case 2:
				monJeu = (JeuLA) session.getAttribute("monJeu");
				break;
			case 3:
				monJeu = (JeuPDLL) session.getAttribute("monJeu");
				break;
			default:
				monJeu = null;
			}

			if (request.getParameter("ajax") != null) {
				if (request.getParameter("ville") != null) {
					response.getWriter().write(getVille(monJeu, session));
				} else {
					if (request.getParameter("score") != null) {
						response.getWriter().write(
								getScore(monJeu, session, this));
					} else {
						if (request.getParameter("save") != null) {
							response.getWriter().write(
									saveScore(monJeu, session, this,
											request.getParameter("nom")));
						} else {
							response.getWriter()
									.write(getReponse(
											request.getParameter("resultatLon"),
											request.getParameter("resultatLat"),
											request.getParameter("temps"),
											monJeu, session));
						}
					}
				}
			} else {
				response.getWriter().write("{\"error\":\"pas d'ajax\"}");
			}
		} else {
			response.getWriter().write(
					"{\"error\":\"variable session null\", \"id\":\""
							+ session.getId() + "\"}");
		}
	}

	private String saveScore(Jeu monJeu, HttpSession session, Game game,
			String nom) {
		// System.out.println("Ajout score!!");
		HighScore.addHighScore(nom, "" + monJeu.getScore(), this
				.getServletContext().getRealPath("WEB-INF"), monJeu.getExt());

		String json = new String("{\n"
				+ "\"total\":\""
				+ monJeu.getScore()
				+ "\",\n"
				+ "\"topten\":\""
				+ HighScore.getHighScore(
						this.getServletContext().getRealPath("WEB-INF"),
						monJeu.getExt())
				+ "\",\n"
				+ "\"isHS\":\""
				+ HighScore.isHighScore("" + monJeu.getScore(), this
						.getServletContext().getRealPath("WEB-INF"), monJeu
						.getExt()) + "\"\n" + "}");

		session.setAttribute("monJeu", monJeu);

		return json;
	}

	private String getScore(Jeu monJeu, HttpSession session, Game game) {

		String json = new String("{\n"
				+ "\"total\":\""
				+ monJeu.getScore()
				+ "\",\n"
				+ "\"topten\":\""
				+ HighScore.getHighScore(
						this.getServletContext().getRealPath("WEB-INF"),
						monJeu.getExt())
				+ "\",\n"
				+ "\"isHS\":\""
				+ HighScore.isHighScore("" + monJeu.getScore(), this
						.getServletContext().getRealPath("WEB-INF"), monJeu
						.getExt()) + "\"\n" + "}");

		session.setAttribute("monJeu", monJeu);

		return json;
	}

	private String getVille(Jeu monJeu, HttpSession session) {
		String json = new String("{\n" + "\"ville\":\""
				+ monJeu.getVilleCourante() + "\"\n" + "}");

		session.setAttribute("monJeu", monJeu);

		return json;
	}

	private String getReponse(String lon, String lat, String tps, Jeu monJeu,
			HttpSession session) {
		// System.out.println(lat);
		double dist = CalculDistance.distanceVolOiseauKM(Double.valueOf(lat),
				Double.valueOf(lon), monJeu.getLatCourante(),
				monJeu.getLonCourante());
		int score = calculScore(dist, tps, monJeu.getType());
		monJeu.augmenteScore(score);
		String json = new String("{\n" + "\"points\":" + score + ",\n"
				+ "\"total\":" + monJeu.getScore() + ",\n" + "\"ville\":\""
				+ monJeu.getVilleCourante() + "\",\n" + "\"trueLon\":"
				+ monJeu.getLonCourante() + ",\n" + "\"trueLat\":"
				+ monJeu.getLatCourante() + ",\n" + "\"distance\":" + dist
				+ "\n" + "}");

		monJeu.avance();

		session.setAttribute("monJeu", monJeu);

		return json;
	}

	private int calculScore(double dist, String tps, int type) {
		double score = 0;
		switch (type) {
		case 3:
			score = (-2000 * dist + 100000) >= 0 ? -2000 * dist + 100000 : 0;
			// on prend une fraction de ce score en fonction du temps passé à
			// répondre
			break;
		case 2:
			score = (-4000 * dist + 100000) >= 0 ? -4000 * dist + 100000 : 0;
			// on prend une fraction de ce score en fonction du temps passé à
			// répondre
			break;
		case 1:
			score = (-10000 * dist + 100000) >= 0 ? -10000 * dist + 100000 : 0;
			// on prend une fraction de ce score en fonction du temps passé à
			// répondre
			break;
		default:
			break;
		}
		score = score - (score * (Double.valueOf(tps) / 10));
		return (int) score;
	}
}
