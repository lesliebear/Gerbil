package Model;

import java.io.Serializable;
import java.util.HashMap;


/**
 * Game class that holds all the information for a game. 
 * Games can be saved for users and they can be loaded to continue to be played. 
 * A game consists of an obstacle course through which the the user must
 * navigate the avatar to get to the goal of the water drinking area. 
 * In the Fancy features, the User can create the obstacle course 
 * @author Amulya
 */
@SuppressWarnings("serial")
public class Game implements Serializable{
	/**User written code for which each node shows up as a block of code in the GUI*/
	private HashMap<Integer,Block> blocks; 
	/**Grid user is playing on for this game*/
	private Grid grid; 
	/**User created functions for this game that appear in drop down list for user to select*/
	private HashMap<Integer,Function> functions; 
	/**Gerbil object to give gerbil's position*/
	private Gerbil gerbil; 
	/**Name of the game that user designates when saving*/
	private String name =""; 
	

	/**
	 * Constructor to create the game 
	 * 
	 * @assumes assumes name has been varified to be unique
	 * @exception none
	 * @postcondition creates a unique (due to name) game object 
	 * 
	 * @param name The name of the game
	 */
	public Game(String name){
		this.name=name;
		this.gerbil= new Gerbil();
		this.grid = new Grid(17,17);
		this.functions= new HashMap<Integer,Function>();
		this.blocks= new HashMap<Integer,Block>();
		
	}
	

	/**
	 * Gets the gerbil object
	 * 
	 * @assumes assumes gerbil object exists
	 * @exception none
	 * @postcondition returns gerbil object that has been instantiated.
	 * 
	 * @return The gerbil object for the game
	 */
	public Gerbil getGerbil(){
		return gerbil;
	}
	/**
	 * Gets the ArrayList of user written code that shows up in the GUI
	 * 
	 * @assumes assumes blocks arraylist object exists
	 * @exception none
	 * @postcondition returns blocks arraylist object that has been instantiated.
	 * 
	 * @return The Arraylist of user written code in GUI
	 */
	public HashMap<Integer,Block> getBlocks(){
		return blocks;
	}
	/**
	 * Gets the Grid object of the game
	 * 
	 * @assumes assumes grid object exists
	 * @exception none
	 * @postcondition returns grid object that has been instantiated.
	 * 
	 * @return The Grid with fruit, water can, and wall placements
	 */
	public Grid getGrid(){
		return grid;
	}
	/**
	 * Gets the name of the game
	 * 
	 * @assumes assumes unique name exists
	 * @exception none
	 * @postcondition returns unique name that has been instantiated.
	 * 
	 * @return Name of the name as entered by the user
	 */
	public String getName(){
		return name;
	}
	/**
	 * Gets the user defined functions arraylist 
	 * 
	 * @assumes assumes user can create functions
	 * @exception none
	 * @postcondition returns user created functions
	 * 
	 * @return User defined functions arraylist
	 */
	public HashMap<Integer,Function> getfunction(){
		return functions;
	}
	
	/**
	 * Adds a function to the functions list
	 * 
	 * @assumes Function is valid function so name not already in array list is checked
	 * @exception none
	 * @postcondition Adds function to function list
	 * 
	 * @return boolean: true if successful add, false otherwise
	 * 
	 * @param functionToAdd function to be added to Functions list
	 */
	public boolean addFunction(Function functionToAdd){
		for (Function f: this.functions.values()){
			if (f.name.equals(functionToAdd.name)){
				return false;
			}
		}
		this.functions.put(this.functions.keySet().size(),functionToAdd);//means index 0 +
		return true;
	}
	
	
	/**
	 * Deletes a function from the functions list
	 * 
	 * @assumes Function with the parameter name exists within the function list
	 * @exception none
	 * @postcondition Deletes function
	 * 
	 * @param functionToDelete name of function to be deleted
	 * @return true if successful deletion, false otherwise
	 */
	public boolean deleteFunction(String funcNameToDelete){
		for (Function f: this.functions.values()){
			if (f.name.equals(funcNameToDelete)){
				this.functions.remove(f);
			}
		}
		return false;
	}
	
}

