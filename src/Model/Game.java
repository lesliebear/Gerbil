package Model;

import java.util.ArrayList;


/**
 * Game class that holds all the information for a game. 
 * Games can be saved for users and they can be loaded to continue to be played. 
 * A game consists of an obstacle course through which the the user must
 * navigate the avatar to get to the goal of the water drinking area. 
 * In the Fancy features, the User can create the obstacle course 
 */
public class Game {
	/**User written code for which each node shows up as a block of code in the GUI*/
	ArrayList<Block> blocks; 
	/**Grid user is playing on for this game*/
	Grid grid; 
	/**User created functions for this game that appear in drop down list for user to select*/
	ArrayList<Function> functions; 
	/**Gerbil object to give gerbil's position*/
	Gerbil gerbil; 
	/**Name of the game that user designates when saving*/
	String name =""; 
	
	

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
	
}

