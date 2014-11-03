package Model;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Kernel/Standard version of backend. 
 * The backend contains the way to save information once a user 
 * exits the program but does not wish to loose his/her work.
 * In the fancy system, users would be the arraylist<user> 
 * since each user's games would be stored in their object.
 * @author Amulya
 * */
public class Backend implements Serializable {

	/**Array List that holds games of the user */
	private ArrayList<Game> games = new ArrayList<Game>();
	
	
	/**
	 * Adds a game to the arraylist of the games for the single user (in kernel/standard)
	 * 
	 * @assumes Game to be added is unique
	 * @exception none
	 * @postcondition Adds game to games list
	 * 
	 * @param g The game object to add to the array list of games in backend. 
	 * @return True if successfully added, else false
	 */
	public boolean addGame(Game g){
		 games.add(g);
		 return true;
	}
	
	/**
	 * Deletes a game from the arraylist of the games for the single user (in kernel/standard)
	 * 
	 * @assumes Game to be deleted may not exist
	 * @exception none
	 * @postcondition Deletes game from games list iff it exists
	 * 
	 * @param in_name Name for the game given by the user. 
	 * @return True if successfully deleted, else false
	 */
	public boolean deleteGame(String in_name){
		return true;
		
	}
	
	/**
	 * Loads the games arraylist in backend for the single user from serialized file(in kernel/standard)
	 * 
	 * @assumes There are games to be loaded
	 * @assumes There is a current player
	 * @exception IOException, ClassNotFoundException
	 * @postcondition loads saved games
	 * 
	 * @return True if successful, else false 
	 * @throws IOException If error encountered with the serialized file
	 * @throws ClassNotFoundException If error encountered with class not found
	 */
	public boolean loadSavedGames()throws IOException, ClassNotFoundException {
		return true; 
	//creates arraylist of games to store here in backend.	
	}
	
	/**
	 * Saves the games array list from backend to a serialized file for the single user (in kernel/standard)
	 * 
	 * @assumes There are games to be saved
	 * @exception IOException
	 * @postcondition Saves games using serialization
	 * 
	 * @throws IOException If error encountered with the serialized file
	 */
	public void saveGames() throws IOException { 
		
	}
	
	/**
	 * Gets the game from the arraylist of user's games based on the name the user gave for the game
	 * when he/she saved the game. 
	 * 
	 * @assumes game may not exist
	 * @exception none
	 * @postcondition gets game using name iff it exists
	 * 
	 * @param in_name Name of the game the user gave when he/she saved the game
	 * @return The game the user had saved before if it exists, else return null 
	 */
	public Game getGame(String in_name){
		return null;
	}
	
	/**
	 * Returns the games arraylist
	 * 
	 * @assumes Games list exists
	 * @exception none
	 * @postcondition gets games list iff it exists
	 * 
	 * @return The array list stored in backend that has all the games. 
	 */
	public ArrayList<Game> getGameList(){
		return this.games;
	}
	
	/**
	 * Sets the game list to backend. 
	 * 
	 * @assumes Games list space exists
	 * @exception none
	 * @postcondition sets game list
	 * 
	 * @param games The games list that you want to use in the program
	 */
	public void setGameList(ArrayList<Game> games){
		this.games = games;
	}
	
//////////////////////////////////////FANCY SYSTEM//////////////////////////////////////////////////////////////////////////////////
	
	/**Array List that holds games of the user */
	ArrayList<User> users = new ArrayList<User>();
	
	
	/**
	 * Adds a user to the arraylist of the users(in Fancy)
	 * 
	 * @assumes List to hold users already exists
	 * @exception none
	 * @postcondition  Places user into user list
	 * 
	 * @param u User object to add to the list
	 * @return True if successful, else false
	 */
	public boolean addUser(User u){
		 users.add(u);
		 return true;
	}
	
	/**
	 * Deletes a user from the arraylist of the users(in Fancy) 
	 * 
	 * @assumes User may not exist
	 * @exception none
	 * @postcondition Deletes a user
	 * 
	 * @param u User's name to get user from the arraylist of the users to delete
	 * @return True if successful, else false
	 */
	public boolean deleteUser(String name){
		return true;
		
	}
	
	/**
	 * Loads the users arraylist in backend(in Fancy)
	 * 
	 * @assumes Users have been previously saved
	 * @exception IOException, ClassNotFoundException
	 * @postcondition Loads Saved users into the system
	 * 
	 * @return True if successful, else false 
	 * @throws IOException If error encountered with the serialized file
	 * @throws ClassNotFoundException If error encountered with class not found
	 */
	public boolean loadSavedUsers()throws IOException, ClassNotFoundException {
		return true; 
	//creates arraylist of users to store here in backend.	
	}
	
	
	/**
	 * Saves the users array list from backend to a serialized file (in Fancy)
	 * 
	 * @assumes Users list exists
	 * @exception IOException
	 * @postcondition Saves users to file using serialization
	 * 
	 * @throws IOException If error encountered with the serialized file
	 */
	public void saveUsers() throws IOException { 
		
	}
	
	/**
	 * Gets the User from the arraylist of users based on the name the user gave for him/herself
	 * 
	 * @assumes User may not exist
	 * @exception none
	 * @postcondition User object retrieved iff user exists
	 * 
	 * @param in_name Name of the the user gave when he/she created an account
	 * @return The user object if named user does  exist, else return null 
	 */
	public User getUser(String name){
		return null;
	}
	
	/**
	 * Returns the users arraylist
	 * 
	 * @assumes user list exists
	 * @exception none
	 * @postcondition Retrieves user list
	 * 
	 * @return The array list stored in backend that has all the users. 
	 */
	public ArrayList<User> getUserList(){
		return this.users;
	}
	
	/**
	 * Sets the user list to backend. 
	 * 
	 * @assumes User list space exists
	 * @exception none
	 * @postcondition User list is set
	 * 
	 * @param u The user list that you want to use in the program is set in the backend.
	 */
	public void setUsersList(ArrayList<User> u){
		this.users= u;
	}

	
}
