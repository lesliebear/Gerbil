package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * User class that holds information about the users including the games they have played, saved or made
 *
 */
public class User implements Serializable{
	/**The username of the user that identifies the user*/
	private String userName;
	/**The password user uses to log in*/
	private String password;
	
	/**Array List that holds games of the user */
	private ArrayList<Game> games = new ArrayList<Game>();
	
	/**
	 * Constructor to create a user object
	 * @param uN Username entered by the user
	 * @param pass Password for the user account
	 */
	public User(String uN, String pass){
		this.userName=uN;
		this.password=pass;
	}

	/**
	 * Gets the user name of the user
	 * @return The user name of the user
	 */
	public String getUserName(){
		return userName;
	}
	
	/**
	 * Gets the passwrod of the user. This is for troubleshooting only!!!!
	 * @return The password of the user 
	 * */
	public String getPassword(){
		return this.password;
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
