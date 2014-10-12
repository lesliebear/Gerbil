package Model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;


public class Backend implements Serializable {

	/*Chose arraylist, but could be hashmap I guess*/
	ArrayList<Game> games = new ArrayList<Game>();
	
	public boolean addGame(Game g){
		games.add(g);
	}
	
	public boolean deleteGame(String in_name){
		
	}
	
	public ArrayList<Game> loadSavedGames()throws IOException, ClassNotFoundException { 
		
	}
	
	
	public void saveGames(ArrayList<Game> games) throws IOException { 
		
	}
	
	public Game getGame(String in_name){
		
	}
	
	public ArrayList<Game> getGameList(){
		return this.games;
	}
	
	public void setGameList(ArrayList<Game> games){
		this.games = games;
	}
}
