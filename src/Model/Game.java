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
	 * @param name The name of the game
	 */
	Game(String name){
		this.name=name;
		this.gerbil= new Gerbil();
		this.grid = new Grid();
		this.functions= new ArrayList<Function>();
		this.blocks= new ArrayList<Block>();
		
	}
	

	/**
	 * Gets the gerbil object
	 * @return The gerbil object for the game
	 */
	public Gerbil getGerbil(){
		return gerbil;
	}
	/**
	 * Gets the ArrayList of user written code that shows up in the GUI
	 * @return The Arraylist of user written code in GUI
	 */
	public ArrayList<Block> getBlocks(){
		return blocks;
	}
	/**
	 * Gets the Grid object of the game
	 * @return The Grid with fruit, water can, and wall placements
	 */
	public Grid getGrid(){
		return grid;
	}
	/**
	 * Gets the name of the game
	 * @return Name of the name as entered by the user
	 */
	public String getName(){
		return name;
	}
	/**
	 * Gets the user defined functions arraylist 
	 * @return User defined functions arraylist
	 */
	public ArrayList<Function> getfunction(){
		return functions;
	}
	
}

