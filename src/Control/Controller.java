package Control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import Model.Block;
import Model.Function;
import Model.Game;
import Model.User;
import Model.Gerbil;


/**
 * Controller class will make all necessary modifications to data in order to send it to the control. 
 * It will concern itself with the data of one user and one game at any given point, provided
 *  by the Backend.
 */
public class Controller {
	/**Holds the current user */
	User userPlaying;
	/**Holds the current game being played */
	Game gamePlaying;
	/**Holds the list of built in functions = eat Apple, eat pear, eat pumpking, move, turn left*/
	ArrayList<Function> builtIn;
	//Note eat fruit must be for that fruit only!! else error popup.
	/** Singleton instance of controller */
	private static Controller controller;
	
	
/**assumes, returns, exceptions**/
	
	/**
	 * Constructor
	 */
	private Controller(){
		builtIn= new ArrayList<Function>();
	}
	
	/** Returns singleton instance of controller
	 * 
	 *  @assumes nothing
	 *  @return single instance of the Controller
	 *  @exception none
	 *  @postcondition creates an instance of Controller iff it has not already been created
	 *  
	 *  */
	public static Controller getInstance() {
		 
		return controller;
	}
	
	/**Creates and initializes built in functions
	 * 
	 * @assumes nothing
	 * @return nothing
	 * @exception none
	 * @postcondition initializes built in functions into the program
	 * 
	 * */
	public void initBuiltIn (){
		
		
	}
	
	/**
	 * Creates and stores the builtIn functions in the controller
	 * 
	 * @assumes nothing
	 * @return nothing
	 * @exception none
	 * @postcondition stores built in functions in the controller
	 * 
	 */
	public void createBuiltIn(){
		
	}
	
	/**
	 * Creates a new Game object
	 * 
	 * @assumes Provided name must be validated for uniqueness/validity
	 * @exception none
	 * @postconditions Iff unique/valid game name provided, new Game object created
	 * 
	 * @param name User provided game name, must be unique/valid
	 * @return newly created and instantiated Game object
	 */
	public Game createGame(String name){
		if(validGameName(name)){
			Game newgame= new Game(name);
			
		}else{
			//error??? what to do for error...
		}
		
		
		//Will call validGameName to check if valid game name
		//Will utilize Game.java constructor
	}
	
	
	
	/**
	 * Determines whether or not game name provided is unique and contains valid characters
	 * 
	 * @assumes Provided data must be validated
	 * @exception none
	 * @postconditions Determines if data provided is valid
	 * 
	 * @param name user provided game name
	 * @return false/true; false if invalid game name, true if valid game name
	 */
	public boolean validGameName(String name){
		for(int i=0; i<name.length(); i++){
			char c= name.charAt(i);
			if(!Character.isLetterOrDigit(c)){
				return false;
			}
		}
		ArrayList<Game> gamelist= userPlaying.getGameList();
		for(int j=0; j<gamelist.size();j++){
			if(gamelist.get(j).getName().equals(name)){
				return false;
			}
		}
				
		return true;
		
	}
	
 	/**
 	 * Will parse block at a given index/position created by the user for syntactical correctness
 	 * 
 	 * @assumes Block data may be incorrect
 	 * @exception none
 	 * @postcondition Determines if block data is syntactically correct
 	 * 
 	 * @param pos index/position of block to be parsed for syntactical correctness
 	 * @return false/true; false if parsing fails, true if parsing succeeds
 	 */
 	public boolean parseBlock(int pos){
 		ArrayList<Block> blocklist= gamePlaying.getBlocks();
 		Block block= blocklist.get(pos);
 		
 		
 		
 		
		return false;
		
		//Will not call other functions/classes
		
		at position i
			if instruction block at i is syntactically valid
				return true
				else
					return false
		
	}

	
	/**
	 * Will edit a block at a given index/position selected by the user with a newly 
	 * provided instruction 
	 * 
	 * @assumes Newly entered data may be invalid
	 * @exception none
	 * @postcondition Will edit a block of data
	 * 
	 * @param pos  index/position of block to be edited by user
	 * @param instruction new instruction to be placed in block
	 * @return
	 */
	public boolean editBlock(int pos, String instruction){
		ArrayList<Block> blocklist= gamePlaying.getBlocks();
		blocklist.get(pos).
		//need to add setInstructions in Block Class
		
		if(!parseBlock(pos)){
			//ERROR 
		}
		
		//Will call parseBlock - must reparse the block to see if valid change has been made
		
		at position i
			if block at i != null
				edit block instructions
				
				if(call parseBlock(i))
					return true
					else 
					return false
					else
						return false
	}	
		

	/**
	 * Will delete a block of code at a given index/position selected by the user 
	 * 
	 * @assumes Block to delete exists
	 * @exception none
	 * @postcondition Deletes block of data
	 * 
	 * @param pos index/position of block to be deleted by user
	 * @return true/false; false if failure to delete, true if deletion succeeds
	 */
	public boolean deleteBlock(int pos){
		return false;
		
		//Will call parseBlock - must reparse the block to see if deletion invalidates a block - i.e. if statement
		//Question: should we have something that asks them if they want to delete even if the code will become invalid ?
		
		ArrayList<Block> blocks = gamePlaying.getBlocks(); 
		
		if blocks is not empty
			get block at position i 
				delete block at i 
				else
					return false
		
	}

	/**
	 * Will insert a NEW block of code into a user specified index/position
	 *
	 * @assumes Index to insert block is valid, instruction may be invalid
	 * @exception none
	 * @postcondition Inserts new block of code
	 * 
	 * @param pos index/position of 
	 * @param instruction Instruction to be inserted into a block
	 * @return false/ true; false if inserting the Block fails, true if it succeeds
	 */
	public boolean insertBlock(int pos, String instruction){
		
		//Will call the Block.java class to initialize a new block
		return false;
		
		get list of blocks	
		position i 
		for list of blocks, find position i
			if(insert new Block at i)	
				return true
				else
					return false
			
	}
	
	
	/**
	 * Will insert instruction to an already created Block of code
	 * 
	 * @assumes Block to insert to exists, instruction may be invalid
	 * @exception none
	 * @postcondition Inserts instruction to Block
	 * 
	 * @param id id of Block to add instruction to 
	 * @param instruction instruction to be added to Block
	 * @return false/true; false if inserting the instruction to the given block fails, true if it succeeds
	 */
	public boolean insertToBlock(int id, String instruction){
		
		//Will call searchForBlock to find block of the given id
		return false;
		get block by id
		edit Block instructions
		
		if(parseBlock)
			return true
			else 
				return false
		
		
	}
	
	/**
	 * Searches for a Block by id field
	 * 
	 * @assumes Block to search for may not exist
	 * @exception none
	 * @postcondition Finds block being searched for iff it exists
	 * 
	 * @param id id of Block to be searched for 
	 * @return Block with the given id if found, else returns null
	 */
	public Block searchForBlock(int id){
		
		//Will not call any other function/class
		
		return null;
		
		get list of block
		int i = 0; 
		while block_id[i] != provided id
			
			if block id = provided id
				return block
			
			i++
			
		
	}
	
	/*Checks for conditionals*/
	
	/**
	 * Determines whether there is food in the selected coordinates (x,y), regardless of food type
	 * 
	 * @assumes int x, y are valid coordinates
	 * @exception none
	 * @postcondition Determines if there's food at a given (x,y)
	 * 
	 * @param x X position in the grid to check if there is food, regardless of food type
	 * @param y Y position in the grid to check if there is food, regardless of food type
	 * @return false/true; false if there is no food in the given (x,y) coordinates, true if there is food in the selected coordinates
	 */
	public boolean isthereFood(int x, int y){
		if(gamePlaying.getGrid().getSquareContent(x, y)=='k'
				|| gamePlaying.getGrid().getSquareContent(x, y)=='p'
				|| gamePlaying.getGrid().getSquareContent(x, y)=='a'){
			return true;
		}
		
		//Will not call any other function/class
		return false;
	}
	
	/**
	 * Determines if there is a wall in the selected (x,y) coordinates
	 * 
	 * @assumes int x,y are valid coordinates
	 * @exception none
	 * @postcondition Determines if there's a wall  at a given (x,y)
	 * 
	 * @param x X position in the grid to check if there is a wall
	 * @param y Y position in the grid to check if there is a wall
	 * @return false/true; false if there is no wall in the given (x,y) coordinates, true if there is a wall in the selected coordinates
	 */
	public boolean isthereWallAhead(int x, int y){
		if(gamePlaying.getGrid().getSquareContent(x, y)=='w'){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Determines if the water container is at position (x,y)
	 * 
	 * @assumes int x,y are valid coordinates
	 * @exception none
	 * @postcondition Determines if there's water  at a given (x,y)
	 * 
	 * @param x X position in the grid to check if the water container is there
	 * @param y Y position int the grid to check if the water container is there
	 * @return false/true; false if water container not found at (x,y), true if water container found at (x,y)
	 */
	public boolean isthereWater(int x, int y){
		
		//Will not call any other function/class
		
		return false;
	}
	
	/*Function stuff*/
	
	/**
	 * Will create a function to be added to list of functions
	 * 
	 * @assumes function name may not be unique
	 * @exception none
	 * @postcondition Creates function iff function name is unique
	 * 
	 * @param name User provided function name, must be unique/valid
	 * @return newly instantiated Function object
	 */
	public Function createFunction(String name,ArrayList<Block> instruction){
		if(!validFunctionName(name)){
			//ERROR
			return null;
		}
		Function newfunction= new Function(name,instruction);
		return newfunction;
		
		
		//Will call Function.java class constructor
		//Will call validFunctionName

	}

	/**
	 * Will determine whether user provided function name is unique and contains valid characters
	 * 
	 * @assumes function name may not be unique
	 * @exception none
	 * @postcondition Determines if function name is unique/valid
	 * 
	 * @param name User provided name for function
	 * @return false/true; false if the function name is not unique && valid, true if unique && valid
	 */
	public boolean validFunctionName(String name){
		for(int i=0; i<name.length(); i++){
			char c= name.charAt(i);
			if(!Character.isLetterOrDigit(c)){
				return false;
			}
		}
		ArrayList<Function> functionlist= gamePlaying.getfunction();
		for(int j=0; j<functionlist.size();j++){
			if(functionlist.get(j).getName().equals(name)){
				return false;
			}
		}
				
		return true;
	}
	
	
	/**
	 * Will add a function to the function list
	 * 
	 * @assumes function is unique
	 * @assumes function has been parsed and is valid
	 * @exception none
	 * @postcondition Will add function to function list
	 * 
	 * @param functionToAdd function to be added to function list
	 */
	public void addFunction(Function functionToAdd){
		//Will not call any functions/classes
		
		gamePlaying.getfunction().add(functionToAdd);
		
	}
	
	
	/**
	 * Will delete a function a user has selected for deletion
	 * 
	 * @assumes function exists
	 * @exception none
	 * @postcondition Deletes function from function list
	 * 
	 * @param g Game to delete the function from
	 * @param name User selected function to delete
	 * @return false/true, false if deletion could not be performed, true if deletion performed
	 */
	public boolean deleteFunction(String name){
		//Will not call any functions/classes
		
		ArrayList<Function> functionlist= gamePlaying.getfunction();
		for(int i=0; i<functionlist.size(); i++){
			if(functionlist.get(i).getName().equals(name)){
				functionlist.remove(i);
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Will return an alphabetically sorted functions list in order to display in drop down menu of GUI
	 * 
	 * @assumes User and built in functions exist
	 * @exception none
	 * @postcondition Provides string list of all functions in a game
	 * 
	 * @return ArrayList of strings with all functions that have been created in the program
	 */
	public ArrayList<String> getFunctions(){
		ArrayList<String> functionnames= new ArrayList<String>();
		for(int i=0; i<gamePlaying.getfunction().size(); i++){
			functionnames.add(gamePlaying.getfunction().get(i).getName());
		}
		
		ArrayList<String> functionlist= sortAlphabetical(functionnames);
		return functionlist;
	}
	
	/**
	 * Will return an alphabetically sorted ArrayList of strings for the drop down menu items
	 * 
	 * @assumes Provided list has not been sorted alphabetically
	 * @exception none
	 * @postcondition Provides an alphabetically sorted list of strings
	 * 
	 * @param toSort ArrayList of strings to be sorted for the menu items
	 * @return Alphabetically sorted araryList of strings upon success, null upon failure
	 */
	public ArrayList<String> sortAlphabetical(ArrayList<String> toSort){
		Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>() {
		    public int compare(String str1, String str2) {
		        int res = String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
		        if (res == 0) {
		            res = str1.compareTo(str2);
		        }
		        return res;
		    }
		};
		
		List<String> list = new ArrayList();
		for(int i=0; i<toSort.size(); i++){
			list.add(toSort.get(i));
		}
		
		Collections.sort(list, ALPHABETICAL_ORDER);

		return (ArrayList<String>) list;
	}
	
	/*Movement stuff*/
	
	/**
	 * Determines if a path of a given set of instructions is clear of walls
	 * 
	 * @assumes provided set of instructions must be validated
	 * @exception none
	 * @postcondition Determines if path of instructions leads to running into a wall
	 * 
	 * @param instructions instructions to be executed 
	 * @return false/true; false if there is no wall in the instructions to be executed, 
	 * 						true if there is a wall in the path of instructions to be executed
	 */
	public boolean pathclearofWalls(LinkedList<Block> instructions){
		
		//Will use grid from Grid.java
		return false;
	}
	
	
	/**
	 * Will change the position of the gerbil to a new (x,y) location
	 * 
	 * @assumes Move may be invalid
	 * @exception none
	 * @postcondition Makes move iff move is valid
	 * 
	 * @param newX New X position of gerbil
	 * @param newY New Y position of gerbil
	 * @return false/true; false if the move was unsuccessful, trueif the move was successful 
	 */
	public boolean makeMove(int newX, int newY){
		if(gamePlaying.getGrid().getSquareContent(newX,	newY)!='0'){
			return false;
		}
		gamePlaying.getGerbil().setX(newX);
		gamePlaying.getGerbil().setY(newY);
		return true;
		
		//Will need grid from Grid.java
		//ASSUMES that the move being made is CORRECT
		
	}
	
	
	/**
	 * Will change the orientation of the Gerbil left 
	 * 
	 * @assumes Gerbil exists
	 * @exception none
	 * @postcondition Orientation of the Gerbil will be changed
	 * 
	 * @return false/true; false if the Gerbil orientation was not changed, true otherwise
	 */
	public boolean turnLeft(){
		//Will need to change gerbil orientation from Gerbil.java
		return false;
	}
	
	/**
	 * Will remove item from x, y, call move()
	 * 
	 * @assumes item at x,y may not exist
	 * @exception none 
	 * @postcondition removes item from (x,y) iff it exists
	 * 
	 * @param x pos of food to eat ; newX for gerbil
	 * @param y pos of food to eat ; newY for gerbil
	 * @return
	 */
	public boolean eat(int x, int y){
		//will need grid from Grid.java
		return false;
	}
	

	/**
	 * This method will delete a given game
	 * @param gameName Name of the game to delete
	 * @return True if deletion is successful, otherwise False
	 */
	public boolean deleteGame(String gameName) {
		
		return false;
	}
	
	/**
	 * This method will load a given game
	 * @param gameName Name of the game to load
	 * @return True if loading is successful, otherwise False
	 */
	public boolean loadGame(String gameName) {
		
		return false;
	}
	
	/**
	 * Save current game
	 * @param gameName Name of the game to save
	 * @return True if save is successful, otherwise False 
	 */
	public boolean saveGame(String gameName) {
		
		return false;
	}
	
	/**
	 * This method will return a list of instructions of the current game
	 * @return List of instructions
	 */
	public ArrayList<Block> getInstructions() {
		
		return null;
	}
	
	/**
	 * This method will add a function to current block
	 * @param function
	 * @return True if add is successful, otherwise False
	 */
	public boolean addFunctionToBlock(Function function, Block block) {
		
		 return false;
	}

}