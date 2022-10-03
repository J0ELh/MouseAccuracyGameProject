package MouseGame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author Joel Hempel
 * Leaderboard object that takes in Players 
 */
public class Leaderboard {
	private ArrayList<Player> playerList;
	private File fileInput;
	
	public Leaderboard(String filePath){
		playerList = new ArrayList<Player>();
		if (filePath == null) {
			throw new NullPointerException("Filepath cannot be null");
		}
		
		fileInput = new File(filePath);
		
		try (Scanner input = new Scanner(fileInput)){
			
			while (input.hasNext()) {
				playerList.add(new Player(input.next(), input.nextInt()));
//				input.nextLine();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	public void addPlayer(Player p) {
		//NEEDS TO BE IMPLEMENTED
	}
	
	public void sortList() {
		
		//Selection sort in ascending order--> might improve to bubble sort or something else
		for (int i = 0; i < playerList.size(); i++) {
			Player max = playerList.get(i);
			int maxIndex = i;
			for (int j = i+1; j < playerList.size(); j++) {
				if (max.getScore() < playerList.get(j).getScore()) {
					max = playerList.get(j);
					maxIndex = j;
				}
				
			}
			Player temp = playerList.get(i);
			playerList.set(i, max);
			playerList.set(maxIndex, temp);
		}
	}
	
	@Override
	public String toString() {
		String output = "Leaderboard\n\n";
		
		sortList();//might be inefficient
		
		for (int i = 0; i < playerList.size(); i++) {
			output += playerList.get(i).toString() + "\n";
		}
		
		
		return output;
		
	}
}
