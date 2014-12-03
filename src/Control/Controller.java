package Control;

import View.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import Model.Backend;
import Model.Block;
import Model.Function;
import Model.Game;
import Model.Gerbil;
import Model.Grid;
import Model.User;

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
	/**Holds the list of built in functions = eat move, turn left
	 * And user created are added to the end of this arraylist when game is initialized first
	 * and then reloaded to the backend when finished game*/
	ArrayList<Function> functions;
	//Note eat fruit must be for that fruit only!! else error popup.
	Backend backend= new Backend();
	Play play;
	ArrayList<String> finalblocks= new ArrayList<String>();
	HashMap<Integer,Boolean> visited;

	char[][] tempgrid= new char[17][17];
	Gerbil tempgerbil= new Gerbil();
	boolean isFunction=false;

	/**assumes, returns, exceptions**/
	/**
	 * Constructor
	 */
	public Controller() {
		gamePlaying = new Game("Test");
		initTempGrid();

	}

	/**
	 * initializes temp grid by copying values of game grid
	 */
	public void initTempGrid(){
		for(int i=0; i<17; i++){
			for(int j=0; j<17; j++){
				tempgrid[i][j]= gamePlaying.getGrid().getSquareContent(i, j);
			}
		}
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
		Function moveAhead= new Function("Move Forward");
		Block moveAheadBlock= new Block();
		moveAheadBlock.setType(2);
		moveAhead.getBlockInstructions().put(-2, moveAheadBlock);

		Function eat= new Function("Eat");
		Block eatBlock= new Block();
		eatBlock.setType(0);
		eat.getBlockInstructions().put(0, eatBlock);

		Function turnLeft= new Function("Turn Left");
		Block turnLeftBlock= new Block();
		turnLeftBlock.setType(1);
		turnLeft.getBlockInstructions().put(-1, turnLeftBlock);

		functions.add(moveAhead);
		functions.add(eat);
		functions.add(turnLeft);
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
		this.functions = new ArrayList<Function>();
		initBuiltIn();
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
		int temp = validGameName(name);
		if(temp==3){
			Game newgame= new Game(name);
			return newgame;		
		}else if(temp==1){
			/////////////////Error: Game names must consist of letters and numbers only//////////

			return null;
		}else{
			//Error: Cannot create Game because game name exists////////////////////////
			return null;
		}
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
	public int validGameName(String name){
		for(int i=0; i<name.length(); i++){
			char c= name.charAt(i);
			if(!Character.isLetterOrDigit(c)){
				return 1;

			}
		}
		ArrayList<Game> gamelist= userPlaying.getGameList();
		for(int j=0; j<gamelist.size();j++){
			if(gamelist.get(j).getName().equals(name)){
				return 2;
			}
		}
		return 3;
	}


	/**
	 * sort key values in ascending order
	 */
	public ArrayList<Integer> sortKeys(ArrayList<Integer> keylist){
		Collections.sort(keylist);
		return keylist;
	}
	/**
	 * resets the tempgrid to original values and tempgerbil to original position
	 * @assumes 
	 */
	public void resetTempGrid(){
		initTempGrid();
		this.tempgerbil= new Gerbil();
	}
	/**
	 * runs the user written code by first
	 * compiling blocks (finalblocks arraylist) using tempgrid and tempgerbil
	 * to show what is happening step by step
	 * until error/ends at goal
	 * @return true-if run was successful and reached water container, false-if run was not successful
	 * 
	 */
	public boolean runBlocks(){
		compileBlocks();
		String command;
		for(int i=0; i<finalblocks.size(); i++){
			command=finalblocks.get(i);
			if(command.equals("Eat")){
				boolean eat= eat(tempgerbil.getX(), tempgerbil.getY());
				if(eat==false){
					//errorEat() dialogue box??;
					return false;
				}
			}else if(command.equals("Turn Left")){
				boolean turnleft= turnLeft(tempgerbil);
				if(turnleft==false){
					return false;
				}
			}else if(command.equals("Move Forward")){
				boolean moveforward= moveForward(tempgerbil);
				if(moveforward==false){
					//errorWall() dialogue box??
					return false;
				}else{
					if(isthereWater(tempgerbil.getX(), tempgerbil.getY())){
						//YOU WIN THE GAME dialogue box??
						return true;
					}
				}
			}else{
				return false;
			}
		}
		return false;

	}

	/**
	 * goes through user coded blocks and stores commands in arraylist finalblocks in the order and
	 * number of times they will be executed for play
	 * (compiles blocks)
	 */
	public boolean compileBlocks(){
		//clears finalblocks arraylist in case it's not empty
		finalblocks.clear();
		HashMap<Integer,Block> blocklist= gamePlaying.getBlocks();
		ArrayList<Integer> keylist= new ArrayList<Integer>();

		//initialize visited hashmap and arraylist of keys
		visited= new HashMap<Integer,Boolean>();
		for(Entry<Integer,Block> entry: blocklist.entrySet()){
			visited.put(entry.getKey(), false);
			keylist.add(entry.getKey());

		}
		//get sort list of keys	
		ArrayList<Integer> sortedkeys= sortKeys(keylist);
		//parse unvisited blocks
		for(int i=0; i<sortedkeys.size(); i++){
			if(visited.get(sortedkeys.get(i))){
				continue;
			}else{
				Block block= blocklist.get(i);
				boolean success=parseBlock(block);
				if(success==false){
					return false;
					//error in parsing
				}
			}
		}
		return true;
	}

	/**
	 * Will parse blocks created by the user and store in ArrayList finalblocks for play
	 * 
	 * @assumes Block data may be incorrect
	 * @exception none
	 * @postcondition stored ArrayList of order of commands "move,eat,turnleft"
	 * 
	 * @return false/true; false if parsing fails, true if parsing succeeds
	 */
	public boolean parseBlock(Block block){
		if(!isFunction){
			int num= block.getlineBegin();
			visited.remove(block.getlineBegin());
			visited.put(num,true);
		}
		HashMap<Integer,Block> nestedblocklist= block.getNestedBlocks();
		//initialize and sort nested keys
		ArrayList<Integer> nestedkeylist= new ArrayList<Integer>();
		for(Entry<Integer,Block> entry: nestedblocklist.entrySet()){
			nestedkeylist.add(entry.getKey());
		}
		ArrayList<Integer> nestedsortedkeys= sortKeys(nestedkeylist);


		String line= block.getCond();
		StringTokenizer st= new StringTokenizer(line);


 		if(block.getType()==3){//"if"
 		 	if(block.getCond().equals("there's Wall Ahead")){
 		 		if(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 		 				Block nestedblock= nestedblocklist.get(i);
 		 				boolean success=parseBlock(nestedblock);
 		 				if(success==false){
 		 					return false;
 		 				}
 		 			}
 		 			return true;
 		 		}else{
 		 			return true; 					
 		 		}
 		 	}else if(block.getCond().equals("there's Food")){
 		 		if(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 		 				Block nestedblock= nestedblocklist.get(i);
 		 				boolean success=parseBlock(nestedblock);
 		 				if(success==false){
 		 					return false;
 		 				}
 		 			}	
 		 			return true;
 		 		}else{
 		 			return true;		
 		 		}
 		 	}else if(block.getCond().equals("there's No Wall Ahead")){
 		 		if(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 		 				Block nestedblock= nestedblocklist.get(i);
 		 				boolean success=parseBlock(nestedblock);
 		 				if(success==false){
 		 					return false;
 		 				}
 		 			}
 		 			return true;
 		 		}else{
 		 			return true;		
 		 		}
 		 				
 		 	}else if(block.getCond().equals("there's No Food")){
 		 		if(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 		 				Block nestedblock= nestedblocklist.get(i);
 		 				boolean success=parseBlock(nestedblock);
 		 				if(success==false){
 		 					return false;
 		 				}
 		 			}
 		 			return true;
 		 		}else{
 		 			return true;		
 		 		}
 		 				
 		 	}
 		 	return false;
 		 	}else if(block.getType()==5){//"else"
 		 		for(int i=0; i<nestedsortedkeys.size(); i++){
		 				Block nestedblock= nestedblocklist.get(i);
		 				boolean success=parseBlock(nestedblock);
		 				if(success==false){
 		 					return false;
 		 				}
		 		}
 		 		return true;
 		 	}else if(block.getType()==4){//"else if"
 		 		if(block.getCond().equals("there's Wall Ahead")){
 	 		 		if(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
 	 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 	 		 				Block nestedblock= nestedblocklist.get(i);
 	 		 				boolean success=parseBlock(nestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}
 	 		 			return true;
 	 		 		}else{
 	 		 			return true; 					
 	 		 		}
 	 		 	}else if(block.getCond().equals("there's Food")){
 	 		 		if(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
 	 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 	 		 				Block nestedblock= nestedblocklist.get(i);
 	 		 				boolean success=parseBlock(nestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}
 	 		 			return true;
 	 		 		}else{
 	 		 			return true;		
 	 		 		}
 	 		 	}else if(block.getCond().equals("there's No Wall Ahead")){
 	 		 		if(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
 	 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 	 		 				Block nestedblock= nestedblocklist.get(i);
 	 		 				boolean success=parseBlock(nestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}	
 	 		 			return true;
 	 		 		}else{
 	 		 			return true;		
 	 		 		}
 	 		 				
 	 		 	}else if(block.getCond().equals("there's No Food")){
 	 		 		if(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
 	 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 	 		 				Block nestedblock= nestedblocklist.get(i);
 	 		 				boolean success=parseBlock(nestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}	
 	 		 			return true;
 	 		 		}else{
 	 		 			return true;		
 	 		 		}
 	 		 				
 	 		 	}
 		 		return false;
 		 	}else if(block.getType()==6){//"while"
 		 		if(block.getCond().equals("there's Wall Ahead")){
 	 		 		while(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
 	 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 	 		 				Block nestedblock= nestedblocklist.get(i);
 	 		 				boolean success= parseBlock(nestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}
 	 		 		}
 	 		 			return true; 					
 	 		 	}else if(block.getCond().equals("there's Food")){
 	 		 		while(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
 	 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 	 		 				Block nestedblock= nestedblocklist.get(i);
 	 		 				boolean success=parseBlock(nestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}
 	 		 		}
 	 		 			return true;		
 	 		 	}else if(block.getCond().equals("there's No Wall Ahead")){
 	 		 		while(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
 	 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 	 		 				Block nestedblock= nestedblocklist.get(i);
 	 		 				boolean success=parseBlock(nestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}	
 	 		 		}
 	 		 			return true;			
 	 		 	}else if(block.getCond().equals("there's No Food")){
 	 		 		while(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
 	 		 			for(int i=0; i<nestedsortedkeys.size(); i++){
 	 		 				Block nestedblock= nestedblocklist.get(i);
 	 		 				boolean success=parseBlock(nestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}	
 	 		 		}
 	 		 			return true;					
 	 		 	}
 		 		return false;	
 		 	}else if(block.getType()==7){//"repeat"
 		 		int repeat= block.getRepeat();
 		 		if(repeat==-1){
 		 			return false;
 		 		}
 		 		for(int i=0; i<repeat; i++){
 		 			for(int j=0; j<nestedsortedkeys.size(); j++){
		 				Block nestedblock= nestedblocklist.get(j);
		 				boolean success=parseBlock(nestedblock);
		 				if(success==false){
 		 					return false;
 		 				}
 		 			}
 		 			return true;
 		 		}
 		 		return true;	
 		 	}else if(block.getType()==0){//"eat"
 				finalblocks.add("Eat");
 				return true;
 	 		}else if(block.getType()==1){//"turn left"
 		 		finalblocks.add("Turn Left");	
 		 		return true;
 	 		}else if(block.getType()==2){//"move forward"
 		 		finalblocks.add("Move Forward");	
 		 		return true;
 		 	}else{
 		 		String key= st.nextToken();
 		 		while(st.hasMoreTokens()){
 		 			key= key + " " + st.nextToken();
 		 		}
 		 			
 		 		HashMap<Integer,Function> functionlist= gamePlaying.getfunction();
 				for(Entry<Integer, Function> entry: functionlist.entrySet()){
 					if(entry.getValue().getName().equals(key)){
 						HashMap<Integer,Block> fnestedblocklist= entry.getValue().getBlockInstructions();
 						ArrayList<Integer> fnestedkeylist= new ArrayList<Integer>();
 				 		for(Entry<Integer,Block> fentry: fnestedblocklist.entrySet()){
 							fnestedkeylist.add(fentry.getKey());
 						}
 				 		ArrayList<Integer> fnestedsortedkeys= sortKeys(fnestedkeylist);				
 	 		 			for(int i=0; i<fnestedsortedkeys.size(); i++){
 	 		 				Block fnestedblock= fnestedblocklist.get(i);
 	 		 				this.isFunction=true;
 	 		 				boolean success=parseBlock(fnestedblock);
 	 		 				if(success==false){
 	 		 					return false;
 	 		 				}
 	 		 			}
 	 		 			this.isFunction=false;
 	 		 			return true;
 					}
 				}
 				return false;
 		 	}
		//Will not call other functions/classes


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
	 * @param instructionblocks new nested block of instructions to be placed in block
	 * @return
	 */
	public void editBlock(int pos, HashMap<Integer,Block> instructionblocks){
		HashMap<Integer,Block> blocklist= gamePlaying.getBlocks();
		Block block=blocklist.get(pos);
		//clear nested blocks and replace with new nested block instructions
		block.getNestedBlocks().clear();
		for(Entry<Integer,Block> entry: instructionblocks.entrySet()){
			block.getNestedBlocks().put(entry.getKey(), entry.getValue());
		}
		
		//cascade here??
		
		if(!parseBlock(block)){
			//ERROR
		}
		
		//Will call parseBlock - must reparse the block to see if valid change has been made
		//will cascade the changes if prevDiff != currDiff (see cascadeNumberingChanges method)
	}	


	/**
	 * Will delete a block of code at a given index/position selected by the user 
	 * 
	 * @assumes Block to delete exists
	 * @exception none
	 * @postcondition Deletes block of data
	 * 
	 * @param pos index/position of block to be deleted by user = line number in the play screen
	 * @return true/false; false if failure to delete, true if deletion succeeds
	 */
	public boolean deleteBlock(int pos){
		return false;

		//Will call parseBlock - must reparse the block to see if deletion invalidates a block - i.e. if statement
		//Question: should we have something that asks them if they want to delete
		//if invalidates = do not delete code...


	}



	/**
	 * Will insert instruction to an already created Block of code
	 * 
	 * @assumes Block to insert to exists and has been validated. 
	 * @exception none
	 * @postcondition Inserts instruction to Block
	 * 
	 * @param id id of Block to add instruction to so it is parent's line begin
	 * @param b Block that we are adding
	 * 
	 */
	public boolean insertToBlock(HashMap<Integer,Block> nested,int pos, Block b){
		//find the block that has the line begin = pos and inserts b in the right place
		//cascades the changes in line numbers 
		ArrayList<Integer> keylist= new ArrayList<Integer>();
		for(Entry<Integer,Block> entry: gamePlaying.getBlocks().entrySet()){
			keylist.add(entry.getKey());
		}
		keylist= sortKeys(keylist);

		int currdiff= keylist.get(keylist.size()-1)  -  keylist.get(0);
		Block tempblock= gamePlaying.getBlocks().get(pos);
		cascadeNumberingChanges(pos, currdiff, tempblock);

		gamePlaying.getBlocks().put(pos, b);


		return false;
	}


	/**
	 * Will insert a NEW block of code into a user specified index/position
	 *
	 * @assumes Index to insert block is valid
	 * @exception none
	 * @postcondition Inserts new block of code
	 * 
	 * @param nested HashMap to recurse on for nesting initial value is the one in gamePlaying
	 * @param pos index/position of block to insert = it is the line number 
	 * @param b block to be inserted
	 * @return false/ true; false if inserting the Block fails, true if it succeeds
	 */
	public void insertBlock(int id, Block b){
		Block parent = searchForBlock(id, gamePlaying.getBlocks()); //get parent since we know parent's line number
		//note: even if the nested blocks has that key already, we need to move it down by calling this funciton again with b's begin+end 
		if(parent.getNestedBlocks().keySet().contains(b.getlineBegin())){
			Block temp = parent.getNestedBlocks().get(b.getlineBegin());
			parent.getNestedBlocks().put(b.getlineBegin(), b); 
			//Will call searchForBlock to find block of the given id and insert insert b to it
			int currDiff = b.getlineEnd()-b.getlineBegin();
			cascadeNumberingChanges(b.getlineBegin(),currDiff,b);
			insertBlock(b.getlineBegin()+b.getlineEnd(), temp);
		}
		parent.getNestedBlocks().put(b.getlineBegin(), b); 
		//Will call searchForBlock to find block of the given id and insert insert b to it
		int currDiff = b.getlineEnd()-b.getlineBegin();
		cascadeNumberingChanges(b.getlineBegin(),currDiff,b);
	}

	/**
	 * Figures out the given line number's parent block's line number = good for highlighting
	 * 
	 * @assumes the line number exists so we search for it and then see the type to return parent or current block
	 * @param id line number clicked upon
	 * @param b the hashmap of blocks to search for the parent block
	 * @return the parent block is returned
	 */
	public Block getHighlighting(int line, HashMap<Integer,Block> blocks){
		Block b = searchForBlock(line, blocks); //gets the block the line is in
		int type = b.getType();
		int lineNum = b.getlineBegin();
		if(lineNum==line && type>2){ //cannot return terminals like that so go down to continue computation
				return b;
		}
		//eat(0),turnleft(1),move(2),if(3),elseif(4),else(5),while(6),repeat(7), function(8),
		if(type>=0 && type <=2){ //[0-2]
			//terminal so see if they belong in larger block. no parent, then return them
			if (b.getParent()!=null){
				return b.getParent();
			}else{
				return b;
			}
		}else if (type==3|| type==6 || type==7 || type==8){
			return b;
		}else if(type==4 || type==5){ //else if and else so return their parent which should be "if"
			return b.getParent();
		}

		return null;
	}

	/**
	 * Cascades the line number changes to the rest of the code after insert, delete or edit
	 * @param lineBegin The block that was changed, inserted, deleted etc's line begin. 
	 * @param currDiff Current/new difference in end - start
	 * @param b Block that the change occured in
	 * @assumes have checked if prevDiff==currDiff to make sure we don't use this method if it is
	 */
	public void cascadeNumberingChanges(int lineBegin, int currDiff, Block b){
		if(b.getParent()==null){
			return; //no more higher level to get to
		}
		HashMap<Integer,Block> nb = b.getParent().getNestedBlocks();
		Block temp=null;
		int tempDiff =0;
		for(int key: nb.keySet()){
			if (key>lineBegin){ //cascade the difference to the blocks after b!
				temp=nb.get(key); //get the object
				tempDiff=temp.getlineEnd()-temp.getlineBegin(); //calculate the difference before hand
				temp.setlineBegin(temp.getlineBegin()+currDiff); //change line begin with the difference
				temp.setLineEnd(temp.getlineBegin()+tempDiff); //did this with temp diff just in case
			}
		}
		cascadeNumberingChanges(lineBegin,currDiff,b.getParent()); //recurse to go higher
	}

	/**
	 * Searches for a Block by id field
	 * 
	 * @assumes Block to search for must exist!!!
	 * @exception none
	 * @postcondition Finds block being searched for
	 * 
	 * @param id id of Block to be searched for so it is the line number 
	 * @return Block with the given id if found, else returns null
	 */
	public Block searchForBlock(int id, HashMap<Integer,Block> block){
		if(block.keySet().isEmpty()){ //no more nesting
			return null;
		}
		for (int curr: block.keySet()){
			if(curr==id){
				return block.get(curr);
			}else if(block.get(curr).getlineBegin()<id && block.get(curr).getlineEnd()>id){ 
				//the block contains the line number in it so search inside
				return searchForBlock(id,block.get(curr).getNestedBlocks());
			}else if (curr>id){ //too big and not before this
				return null;
			}
		}
		return null; //did not find.
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
	public boolean isthereFood(int x, int y){ //checks if square has food or not
		if(gamePlaying.getGrid().getSquareContent(x, y)=='k'
				|| gamePlaying.getGrid().getSquareContent(x,y)=='p'
				|| gamePlaying.getGrid().getSquareContent(x, y)=='a'){
			return true;
		}
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
	public boolean isthereWall(int x, int y){
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
		if(gamePlaying.getGrid().getSquareContent(y, x)=='t'){
			return true;
		}
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
	public Function createFunction(String name,HashMap<Integer,Block> instruction){
		int temp = validFunctionName(name);
		if(temp==1){
			/////////////Error: Functions names can only consists of letters or numbers///////////
			return null;
		}else if(temp==2){
			///////////////////////////Error: Function name already exists////////////////
			return null;
		}
		Function newfunction= new Function(name);
		newfunction.setBlockInstructions(instruction);
		return newfunction;
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
	public int validFunctionName(String name){
		for(int i=0; i<name.length(); i++){
			char c= name.charAt(i);
			if(!Character.isLetterOrDigit(c)){
				return 1;
			}
		}
		HashMap<Integer,Function> functionlist= gamePlaying.getfunction();
		for(Entry<Integer, Function> entry: functionlist.entrySet()){
			if(entry.getValue().getName().equals(name)){
				return 2;
			}
		}
		return 3;
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
		gamePlaying.addFunction(functionToAdd);
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
		HashMap<Integer,Function> functionlist= gamePlaying.getfunction();
		for(Entry<Integer,Function> entry: functionlist.entrySet()){
			if(entry.getValue().getName().equals(name)){
				functionlist.remove(entry.getKey());
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
	public ArrayList<Function> getFunctions(){

		ArrayList<String> functionnames= new ArrayList<String>();
		HashMap<Integer,Function>list=gamePlaying.getfunction();
		for(Entry<Integer,Function> entry: list.entrySet()){
			functionnames.add(entry.getValue().getName());
		}

		ArrayList<String> functionlist= sortAlphabetical(functionnames);
		ArrayList<Function>functions= new ArrayList<Function>();

		for(int i=0; i<functionlist.size(); i++){
			for(Entry<Integer,Function> entry: list.entrySet()){
				if(entry.getValue().getName().equals(functionlist.get(i))){
					functions.add(entry.getValue());
				}
			}
		}
		return functions;
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

		Comparator<String> ALPHABETICAL_ORDER = new Comparator<String>(){
			public int compare(String str1, String str2){
				int res= String.CASE_INSENSITIVE_ORDER.compare(str1, str2);
				if(res==0){
					res= str1.compareTo(str2);
				}
				return res;
			}
		};
		List<String> list= new ArrayList<String>();
		for(int i=0; i<toSort.size();i++){
			list.add(toSort.get(i));
		}
		Collections.sort(list, ALPHABETICAL_ORDER);
		return (ArrayList<String>) list;
	}

	/*Movement stuff*/
	//////go through array list that consists move, eat, or turn left and see after each movement if valid or not!!!!
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
	 * Will change the position of the gerbil to one cell forward
	 * 
	 * @assumes Move may be invalid => (ex. there is wall there so after motion)
	 * @exception none
	 * @postcondition Makes move iff move is valid
	 * @param gerbil the gerbil object to move forward
	 * @return false/true; false if the move was unsuccessful, true if the move was successful 
	 */
	public boolean moveForward(Gerbil gerbil){
		if(gamePlaying.getGrid().getSquareContent(gerbil.getFrontY(), gerbil.getFrontX())=='w'){
			return false;
		}
		int diffX = gerbil.getFrontX()-gerbil.getX();
		int diffY = gerbil.getFrontY()-gerbil.getY();
		gerbil.setX(gerbil.getFrontX());
		gerbil.setY(gerbil.getFrontY());
		gerbil.setFrontX(gerbil.getFrontX()+diffX); //resets frontX and y accordingly
		gerbil.setFrontY(gerbil.getFrontY()+diffY);
		return true;
	}


	/**
	 * Will change the orientation of the Gerbil left 
	 * 
	 * @assumes Gerbil exists
	 * @exception none
	 * @postcondition Orientation of the Gerbil will be changed
	 * @param gerbil the gerbil object to turn left
	 * @return false/true; false if the Gerbil orientation was not changed, true otherwise
	 */
	public boolean turnLeft(Gerbil gerbil){

		//determine if gerbil is facing South --> will face East
		if(gerbil.getFrontX()==gerbil.getX() && gerbil.getFrontY()==gerbil.getY()+1){
			gerbil.setFrontX(gerbil.getX()+1);
			gerbil.setFrontY(gerbil.getY());
			return true;
		}
		//determine if gerbil is facing North --> will face West
		if(gerbil.getFrontX()==gerbil.getX() && gerbil.getFrontY()==gerbil.getY()-1){
			gerbil.setFrontX(gerbil.getX()-1);
			gerbil.setFrontY(gerbil.getY());
			return true;
		}
		//determine if gerbil is facing East --> will face North
		if(gerbil.getFrontX()==gerbil.getX()+1 && gerbil.getFrontY()==gerbil.getY()){
			gerbil.setFrontX(gerbil.getX());
			gerbil.setFrontY(gerbil.getY()-1);
			return true;
		}
		//determine if gerbil is facing West --> will face South
		if(gerbil.getFrontX()==gerbil.getX()-1 && gerbil.getFrontY()==gerbil.getY()){
			gerbil.setFrontX(gerbil.getX());
			gerbil.setFrontY(gerbil.getY()+1);
			return true;
		}

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
		//create pointer to grid
		if(tempgrid[y][x]=='k' //if food
				|| tempgrid[y][x]=='p'
				|| tempgrid[y][x]=='a'){
			tempgrid[y][x]='0'; //eat
			return true;
		}
		//will need grid from Grid.java
		return false;
	}


	/**
	 * This method will delete a given game
	 * @param gameName Name of the game to delete
	 * @return True if deletion is successful, otherwise False
	 */
	public boolean deleteGame(String gameName) {
		return backend.deleteGame(gameName);
	}


	/**
	 * This method will load a given game
	 * @param gameName Name of the game to load
	 * @return True if loading is successful, otherwise False
	 */
	public boolean loadGame(String gameName) {
		this.gamePlaying= backend.getGame(gameName);
		backend.deleteGame(gameName);
		if(!gamePlaying.getfunction().isEmpty()){
			ArrayList<Integer> keylist= new ArrayList<Integer>();
			for(Entry<Integer,Function> entry: gamePlaying.getfunction().entrySet()){
				keylist.add(entry.getKey());
			}
			keylist=sortKeys(keylist);
			for(int i=0; i<keylist.size(); i++){
				this.functions.add(gamePlaying.getfunction().get(keylist.get(i)));
			}

		}
		return true;
	}

	/**
	 * Save current game
	 * @return True if save is successful, otherwise False 
	 */
	public boolean saveGame() {
		for(int i=0; i<functions.size();i++){
			gamePlaying.getfunction().put(i, functions.get(i));
		}  
		backend.addGame(this.gamePlaying);
		return true;
	}


	/**
	 * This method will return a list of instructions of the current game
	 * @return List of instructions
	 */
	public ArrayList<Block> getInstructions() {
		ArrayList<Block> blocklist= new ArrayList<Block>();
		ArrayList<Integer> keylist= new ArrayList<Integer>();
		for(Entry<Integer,Block> entry: gamePlaying.getBlocks().entrySet()){
			keylist.add(entry.getKey());
		}
		keylist=sortKeys(keylist);
		for(int i=0; i<keylist.size(); i++){
			Block block= gamePlaying.getBlocks().get(keylist.get(i));
			blocklist.add(block);
		}
		return blocklist;
	}


	/**
	 * This method will add a function to current block
	 * @param function
	 * @return True if add is successful, otherwise False
	 */
	public boolean addFunctionToBlock(int begin, Function function, Block block) {
		//Note: A function is a one liner!! thus i set currDifference in cascadeNumbering Changes to 1
		Block functionblock= new Block();
		functionblock.setType(8); 
		functionblock.setlineBegin(begin);
		functionblock.setLineEnd(begin+1);
		functionblock.setParent(block);
		int funcNum = findFunction(function.getName());
		functionblock.setFunctionNum(funcNum);
		gamePlaying.getBlocks().get(block.getlineBegin()).getNestedBlocks().put(begin, functionblock);
		cascadeNumberingChanges(begin,1,functionblock);
		return true;

	}

	/**
	 * Finds functions in array list of functions. First built in functions exist in that arraylist
	 * then user built functions so search through via name.
	 * 
	 * @assumes The function exists in arraylist of functions
	 * @exception none
	 * @postcondition none
	 *  
	 * @param name Name of the function we are searching for. 
	 * @return The index of the function.
	 */
	private int findFunction(String name) {
		for (int i =0; i<functions.size(); i++){
			if (functions.get(i).getName().equals(name)){
				return i;
			}
		}
		return -1;
	}

}