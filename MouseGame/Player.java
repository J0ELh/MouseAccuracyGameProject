package MouseGame;


/**
 * 
 * @author Joel Hempel
 * Player that takes in a nickname and a score
 */
public class Player {
	private int score;
	private String name;
	
	public Player(String n, int s) {
		if (n == null) {
			throw new NullPointerException("Nickname cannot be null");
		}
		if (s <= 0) {
			throw new IllegalArgumentException("s needs to be grater than 0");
		}
		score = s;
		name = n;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return name+ " " + score;
	}
}
