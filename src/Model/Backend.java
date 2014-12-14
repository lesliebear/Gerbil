package Model;
 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
@SuppressWarnings("serial")
public class Backend implements Serializable {

	///**Directory in which user data file is stored*/
	//public static final String storeDir = "data"; 
	/**File to store user data*/
	public static final String storeFile = "games.dat"; 
	/**Array List that holds games of the user */
	private static ArrayList<Game> games = new ArrayList<Game>();


	/**
	 * Adds a game to the arraylist of the games for the single user (in kernel/standard)
	 * 
	 * @assumes Game name to be added is unique
	 * @exception none
	 * @postcondition Adds game to games list
	 * 
	 * @param g The game object to add to the array list of games in backend. 
	 * @return True if successfully added, else false
	 */
	public boolean addGame(Game g){
		for (Game game: this.games){
			if (game.getName().equals(g.getName())){
				return false;
			}
		}
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
	public boolean deleteGame(String name){
		for (int i = 0; i<games.size();i++){
			if (games.get(i).getName().compareTo(name)==0){
				this.games.remove(i);
				return true;
			}
		}
		return false;
	}
	
	public static boolean gameExists(String gameIn){
		for(int i=0; i< games.size(); i++){
			if(games.get(i).getName().equalsIgnoreCase(gameIn)){ // not sure if this works - kat
				return true;
			}
		}
		
		return false;
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
	 
	@SuppressWarnings({ "unchecked", "resource" })
	public boolean loadSavedGames(){
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(new FileInputStream(storeFile));
			ArrayList<Game> temp = ((ArrayList<Game>)ois.readObject());
			this.setGameList(temp);
			return true;
		}catch (Exception e1){
			Game newGame =new Game("FileEmpty"); //if serializable file is empty, causes problems
			this.addGame(newGame); //add new game
			this.saveGames(); //save that game
			this.deleteGame("FileEmpty"); //delete added game
			this.saveGames(); //save the empty list
		}
		return false;
		//creates arraylist of games to store here in backend.	
	}  */

	@SuppressWarnings("unchecked")
	public ArrayList<Game> loadSavedGames() // kat
			throws IOException, ClassNotFoundException { 
		@SuppressWarnings("resource")
		ObjectInputStream ois = new ObjectInputStream( 
				new FileInputStream(storeFile)); 
		return (ArrayList<Game>)ois.readObject(); 
	} 
	
	
	public void saveGames(ArrayList<Game> users) throws IOException { 
		@SuppressWarnings("resource")
		ObjectOutputStream oos = new ObjectOutputStream( 
				new FileOutputStream(storeFile)); 
		oos.writeObject(users); 
		return;
	} 
	
	/**
	 * Saves the games array list from backend to a serialized file for the single user (in kernel/standard)
	 * 
	 * @assumes There are games to be saved
	 * @exception IOException, FileNotFoundException
	 * @postcondition Saves games using serialization
	 * 
	 * @return True if successful in saving games else false
	 * @throws IOException If error encountered with the serialized file
	 * @throws FileNotFoundException If file to store the file could not been found.
	 
	@SuppressWarnings("resource")
	public boolean saveGames() { 
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(storeFile));
			oos.writeObject(games);
			return true;
		} catch (Exception e) {return false;} 
	}*/
	

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
	public Game getGame(String name){
		for(Game g: this.games){
			if((g.getName().compareTo(name))==0){
				return g;
			}
		}
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

	
	
	public ArrayList<String> getGamesStringArray(){
		ArrayList<String> toReturn= new ArrayList<String>(); 
		
		for(int i=0; i<games.size(); i++){
			toReturn.add(i, games.get(i).getName());
		}
		
		return toReturn;
	}
	
}
