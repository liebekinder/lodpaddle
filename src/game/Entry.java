package game;

public class Entry implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int score;
    String name;

    Entry(String name,int score) {
	this.name=name;
	this.score=score;
    }

    int getScore() {
	return score;
    }

    String getName() {
	return name;
    }
}
