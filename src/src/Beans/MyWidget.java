package src.Beans;

/**
 * Le principe de ce widget est d'avoir uniquement les informations de base, à
 * savoir la couleur de fond, une taille minimum, le titre 'humain' du widget et
 * son id, C'est le contenu qui aura toutes les autres infos (potentiellement
 * constructible à partir des autres infos
 * 
 * @author seb
 * 
 */
public class MyWidget {

	String titre;
	String contenu;
	String id;
	String couleur;
	int taille;
	String imageFond;

	public MyWidget(String titre, String contenu, String id, String couleur,
			int taille) {
		this.titre = titre;
		this.contenu = contenu;
		this.id = id;
		this.couleur = couleur;
		this.taille = taille;
	}

	public MyWidget(String titre, String contenu, String id) {
		this.titre = titre;
		this.contenu = contenu;
		this.id = id;
	}

	public MyWidget(String titre, String id) {
		this.titre = titre;
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public int getTaille() {
		return taille;
	}

	public void setTaille(int taille) {
		this.taille = taille;
	}

	public String getImageFond() {
		return imageFond;
	}

	public void setImageFond(String imageFond) {
		this.imageFond = imageFond;
	}

}
