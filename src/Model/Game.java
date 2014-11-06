package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Game class that holds all the information for a game. 
 * Games can be saved for users and they can be loaded to continue to be played. 
 * A game consists of an obstacle course through which the the user must
 * navigate the avatar to get to the goal of the water drinking area. 
 * In the Fancy features, the User can create the obstacle course 
 * @author Amulya
 */
public class Game implements Serializable{
	/**User written code for which each node shows up as a block of code in the GUI*/
	private ArrayList<Block> blocks; 
	/**Grid user is playing on for this game*/
	private Grid grid; 
	/**User created functions for this game that appear in drop down list for user to select*/
	private ArrayList<Function> functions; 
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
		this.grid = new Grid();
		this.functions= new ArrayList<Function>();
		this.blocks= new ArrayList<Block>();	
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
	public ArrayList<Block> getBlocks(){
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
	public ArrayList<Function> getfunction(){
		return functions;
	}
	
}

