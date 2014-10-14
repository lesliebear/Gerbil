package Model;

import java.util.ArrayList;

/**
 * User class that holds information about the users including the games they have played, saved or made
 *
 */
public class User {
	/**The username of the user that identifies the user*/
	String userName;
	/**The password user uses to log in*/
	String password;
	
	/**Array List that holds games of the user */
	ArrayList<Game> games = new ArrayList<Game>();
	

	/**
	 * Gets the user name of the user
	 * @return The user name of the user
	 */
	public String getUserName(){
		return userName;
	}
	
	/**
	 * Adds a game to the arraylist of the games for the user 
	 * @param g The game object to add to the array list of games in user. 
	 * @return True if successfully added, else false
	 */
	public boolean addGame(Game g){
		 games.add(g);
		 return true;
	}
	
	/**
	 * Deletes a game from the arraylist of the games for the user 
	 * @param in_name Name for the game given by the user. 
	 * @return True if successfully deleted, else false
	 */
	public boolean deleteGame(String in_name){
		return true;
		
	}
	
	/**
	 * Gets the game from the arraylist of user's games based on the name the user gave for the game
	 * when he/she saved the game. 
	 * @param in_name Name of the game the user gave when he/she saved the game
	 * @return The game the user had saved before if it exists, else return null 
	 */
	public Game getGame(String in_name){
		return null;
	}
	
	/**
	 * Returns the games arraylist
	 * @return The array list stored in user that has all the games. 
	 */
	public ArrayList<Game> getGameList(){
		return this.games;
	}
}
