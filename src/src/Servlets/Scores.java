package src.Servlets;

import game.HighScore;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Scores
 */
@WebServlet("/Scores")
public class Scores extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Scores() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(getScores());
	}

	private String getScores() {
		String json = new String(
				"{\n"
						+ "\"topten1\":\""
						+ HighScore.getHighScore(this.getServletContext()
								.getRealPath("WEB-INF"), "NM")
						+ "\",\n"
						+ "\"topten2\":\""
						+ HighScore.getHighScore(this.getServletContext()
								.getRealPath("WEB-INF"), "LA")
						+ "\",\n"
						+ "\"topten3\":\""
						+ HighScore.getHighScore(this.getServletContext()
								.getRealPath("WEB-INF"), "PDLL") + "\"\n" + "}");
		return json;
	}

}
