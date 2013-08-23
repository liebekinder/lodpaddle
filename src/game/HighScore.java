package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HighScore {

	public HighScore() {
	}

	public static String getHighScore(InputStream path) {
		ArrayList<String> scores = parseHighScore(path);
		scores = ordonneListe(scores);
		int cpt = 0;
		String retour = new String();
		while (cpt < scores.size() && cpt < 10) {
			retour += "<tr><td>" + cpt + "</td><td>"
					+ scores.get(cpt).split(" &$ ")[0] + "</td><td>"
					+ scores.get(cpt).split(" &$ ")[1] + "</td></tr>";
		}
		return retour;
	}

	public static boolean addHighScore(String nom, String score, String path) {
		FileWriter out;
		try {
			out = new FileWriter(path + "/highscore.txt");
			out.write(score + " &$ " + nom + "\n");
			out.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static int isHighScore(String score, InputStream path) {
		int highScore = -1;
		ArrayList<String> scores = parseHighScore(path);
		scores = ordonneListe(scores);
		int cpt = 0;
		while (cpt < scores.size() && cpt < 10) {
			// si le score courant est sup au score de la liste
			if (Integer.valueOf(score.split(" &$ ")[0]).compareTo(
					Integer.valueOf(scores.get(cpt).split(" &$ ")[0])) > 0) {
				highScore = cpt;
				cpt = 11;
			}
			cpt++;
		}
		return highScore;

	}

	private static ArrayList<String> ordonneListe(ArrayList<String> scores) {
		System.out.println(scores.size());
		ArrayList<String> blabla = new ArrayList<String>();
		int cpt1 = 0;
		while (cpt1 < scores.size()) {
			boolean ajoute = false;
			if (blabla.size() == 0) {
				blabla.add(scores.get(cpt1));
				ajoute = true;
			} else {
				int cpt2 = 0;
				while (cpt2 < blabla.size()) {
					if (Integer.valueOf(scores.get(cpt1).split(" &$ ")[0])
							.compareTo(
									Integer.valueOf(blabla.get(cpt2).split(
											" &$ ")[0])) > 0) {
						blabla.add(cpt2, scores.get(cpt1));
						cpt2 = blabla.size() + 1;
						ajoute = true;
					} else {
						cpt2++;
					}
				}
				if (!ajoute)
					blabla.add(scores.get(cpt1));
			}
			cpt1++;
		}
		return null;
	}

	private static ArrayList<String> parseHighScore(InputStream path) {
		ArrayList<String> scores = new ArrayList<String>();

		InputStreamReader isr;
		try {
			isr = new InputStreamReader(path, "UTF-8");
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while ((line = reader.readLine()) != null) {
				scores.add(line);
			}

			// BufferedReader reader;
			// try {
			// reader = new BufferedReader(new FileReader(path +
			// "/highscore.txt"));
			// String line;
			// while ((line = reader.readLine()) != null) {
			// scores.add(line);
			// }
			// reader.close();
			// } catch (FileNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			System.out.println("parse " + scores.size());
			return scores;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
