package game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HighScore {

	public static final String scoreFile = "highscore";
	public static final String sep = " - ";

	public HighScore() {
	}

	public static String getHighScore(String currentPath, String ext) {
		ArrayList<String> scores = parseHighScore(currentPath, ext);
		if (scores != null) {
			scores = ordonneListe(scores);
			if(scores != null){
			int cpt = 0;
			String retour = new String();
			while (cpt < scores.size() && cpt < 10) {
				retour += "<tr><td>" + (cpt+1) + "</td><td>"
						+ scores.get(cpt).split(sep)[0] + "</td><td>"
						+ scores.get(cpt).split(sep)[1] + "</td></tr>";
				cpt++;
			}
			return retour;
			}
			else{
				System.out.println("erreur lors de l'ordonnancement du fichier");
				return null;
			}
		}
		else{
			System.out.println("erreur lors de la lecture du fichier");
			return null;
		}
	}

	public static boolean addHighScore(String nom, String score, String currentPath, String ext) {
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(currentPath+"/"+scoreFile+ext+".txt", true)));
		    if(nom.length()>12) out.println(score+" - "+nom.substring(0, 12));
		    out.println(score+" - "+nom);
		    out.close();
		} catch (IOException e) {
		    //oh noes!
			return false;
		}
		return true;
	}

	public static int isHighScore(String score,String currentPath, String ext) {
		int highScore = -1;
		ArrayList<String> scores = parseHighScore(currentPath, ext);
		scores = ordonneListe(scores);
		int cpt = 0;
		while (cpt < scores.size() && cpt < 10) {
			// si le score courant est sup au score de la liste
			if (Integer.valueOf(score.split(sep)[0]).compareTo(
					Integer.valueOf(scores.get(cpt).split(sep)[0])) > 0) {
				highScore = cpt;
				cpt = 11;
			}
			cpt++;
		}
		return highScore+1;

	}

	private static ArrayList<String> ordonneListe(ArrayList<String> scores) {
		// System.out.println(scores.size());
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
					if (Integer.valueOf(scores.get(cpt1).split(sep)[0].trim())
							.compareTo(
									Integer.valueOf(blabla.get(cpt2).split(
											sep)[0].trim())) > 0) {
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
		return blabla;
	}

	private static ArrayList<String> parseHighScore(String currentPath, String ext) {
		ArrayList<String> scores = new ArrayList<String>();

		String line = null;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(currentPath+"/"+scoreFile+ext+".txt");

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				scores.add(line);
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + currentPath+"/"+scoreFile+ext+".txt" + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + currentPath+"/"+scoreFile+ext+".txt"+ "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

		return scores;
	}
}
