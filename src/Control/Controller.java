package Control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import Model.*;


/**
 * Controller class will make all necessary modifications to data in order to send it to the control. 
 * It will concern itself with the data of one user and one game at any given point, provided
 *  by the Backend.
 */
public class Controller {
	/**Holds the current game being played */
	public Game gamePlaying;
	
	/**Holds the list of built in functions = eat move, turn left
	 * And user created are added to the end of this arraylist when game is initialized first
	 * and then reloaded to the backend when finished game*/
	//public ArrayList<Function> functions = new ArrayList<Function>();
	//Note eat fruit must be for that fruit only!! else error popup.
	//Backend backend= new Backend();

	ArrayList<String> finalblocks= new ArrayList<String>();
	//HashMap<Integer,Boolean> visited;
	HashMap<Integer,Block> tempFunctionBlockInstructions= new HashMap<Integer,Block>();
	Block parent = null;
	Block userCodingNow = null;
	Block userCodingNowEdit= null;
	Block parentEdit = null;
	Block parentFunction = null;
	Block userCodingNowFunction = null;


	char[][] tempgrid= new char[17][17];
	Gerbil tempgerbil= new Gerbil(); //Gerbil used only for "parsing/compiling"
	boolean isFunction=false;

	int countblocks=1;

	/**assumes, returns, exceptions**/

	public void testingStuff() { // not sure what this is for.. Kat
		tempgerbil = gamePlaying.getGerbil();
		initTempGrid();
	}

	public String[] getUnFinIns(){ // unfinished insertion
		Block tempPar=null;
		ArrayList<String> ins = new ArrayList<String>();

		for(Block p = this.userCodingNow; p!=null; p=p.getParent()){
			tempPar = p;
		}//get to main nesting level
		if(tempPar==null){
			return ins.toArray(new String[ins.size()]);
		}else{
			printNotDoneBlock(0,tempPar.getNestedBlocks(), ins);
			return ins.toArray(new String[ins.size()]);
		}
	}

	public String[] FunctionUnFin(){
		Block tempPar=null;
		ArrayList<String> ins = new ArrayList<String>();
		for(Block p = this.userCodingNowFunction; p!=null; p=p.getParent()){
			tempPar = p;
		}//get to main nesting level
		
		if(tempPar!=null){
			printNotDoneBlock(0,tempPar.getNestedBlocks(), ins);
		}
			
		return ins.toArray(new String[ins.size()]);

	}

	public void printNotDoneBlock(int tab, HashMap<Integer,Block> blocks, ArrayList<String> list){
		int type;
		String tabStr="";
		for(int i =0; i<tab*5; i++){
			tabStr+=" ";
		} 
		ArrayList<Integer> kList = new ArrayList<Integer>();
		for(Integer z: blocks.keySet()){
			kList.add(z);
		}
		Collections.sort(kList);
		for(Integer b: kList){
			Block block = blocks.get(b);
			String lBeg = Integer.toString(block.getlineBegin());
			//list.add(Integer.toString(block.getlineBegin()));
			type = block.getType();
			if(type==0){ //eat = terminal so no nesting
				list.add(lBeg +" "+tabStr+"Eat");
			}else if(type==1){ //turn left  = terminal so no nesting
				list.add(lBeg +" "+tabStr+"TurnLeft");
			}else if(type==2){ //move = terminal so no nesting
				list.add(lBeg +" "+tabStr+"Move");
			}else if(type==3){ //if
				list.add(lBeg +" "+tabStr+"If "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==4){ //else if
				list.add(lBeg +" "+tabStr+"ElseIf "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==5){//else
				list.add(lBeg +" "+tabStr+"Else ");
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==6){//while
				list.add(lBeg +" "+tabStr+"While "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");;
				}
			}else if(type==7){//repeat
				list.add(lBeg +" "+tabStr+"Repeat "+block.getRepeat());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printNotDoneBlock(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==8){//function = CANNOT HAVE NESTED BLOCKS!!!
				Function f = gamePlaying.functions.get(block.getFunctionNum());
				list.add(lBeg +" "+tabStr+f.getName());
			}	
			tabStr="";
			for(int j =0; j<tab*5; j++){//reset the tabs
				tabStr+=" ";
			}
		}
	}

	public String[] JListString(){
		ArrayList<String> temp = new ArrayList<String>(); 
		getJList(0,this.gamePlaying.getBlocks(),temp);
		String [] toReturn = new String[temp.size()];

		for(int i=0; i< toReturn.length; i++){
			toReturn[i] = temp.get(i);
		}

		return toReturn;
	}
	
	public String[] JListFunctions(){ 
		String [] toReturn = new String[this.gamePlaying.functions.size()];

		for(int i=0; i< this.gamePlaying.functions.size(); i++){
			toReturn[i] = this.gamePlaying.functions.get(i).getName();
		}

		return toReturn;
	}

	/*public ArrayList<String> getUserDefinedFunctionsStringArray(){
		ArrayList<String> toReturn = new ArrayList<String>(); 

		for(int i=0; i<functions.size(); i++){
			toReturn.add(i, functions.get(i).getName());
		}

		return toReturn;
	}*/

	/**
	 * Prints the hashmap of the blocks based on the indentation level(nesting level)
	 * @param tab The indentation level of the block to be printed out
	 * @param blocks Takes hashmap to print out the contents of the hashmap
	 * @return String array of instructions from the blocks to display in view
	 *  
	 */
	public int getJList(int tab, HashMap<Integer,Block> blocks, ArrayList<String> list){
		int type;
		String tabStr="";
		for(int i =0; i<tab*5; i++){
			tabStr+=" ";
		} 
		ArrayList<Integer> kList = new ArrayList<Integer>();
		for(Integer z: blocks.keySet()){
			kList.add(z);
		}
		Collections.sort(kList);
		for(Integer b: kList){
			Block block = blocks.get(b);
			String lBeg = Integer.toString(block.getlineBegin());
			//list.add(Integer.toString(block.getlineBegin()));
			type = block.getType();
			if(type==0){ //eat = terminal so no nesting
				list.add(lBeg+" "+tabStr+"Eat");
			}else if(type==1){ //turn left  = terminal so no nesting
				list.add(lBeg+" "+tabStr+"TurnLeft");
			}else if(type==2){ //move = terminal so no nesting
				list.add(lBeg+" "+tabStr+"Move");
			}else if(type==3){ //if
				list.add(lBeg+" "+tabStr+"If "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==4){ //else if
				list.add(lBeg+" "+tabStr+"ElseIf "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==5){//else
				list.add(lBeg+" "+tabStr+"Else ");
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==6){//while
				list.add(lBeg+" "+tabStr+"While "+block.getCond());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");;
				}
			}else if(type==7){//repeat
				list.add(lBeg+" "+tabStr+"Repeat "+block.getRepeat());
				for(int i =0; i<(tab+1)*5; i++){
					tabStr+=" ";
				} 
				list.add(block.getlineBegin()+1+" "+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					getJList(tempTab,block.getNestedBlocks(),list);
				}
				if(block.getlineEnd()!=-1){
					list.add(block.getlineEnd()+" "+tabStr+"end");
				}
			}else if(type==8){//function = CANNOT HAVE NESTED BLOCKS!!!
				Function f = gamePlaying.functions.get(block.getFunctionNum());
				list.add(lBeg+" "+tabStr+f.getName());
			}	
			tabStr="";
			for(int j =0; j<tab*5; j++){//reset the tabs
				tabStr+=" ";
			}
		}
		return 0;
	}

	//////////////////////////////////DEBUGGIN METHODS BEGIN/////////////////////////////////////
	/**
	 * returns finalblocks arraylist
	 */
	public ArrayList<String> getFinalBlocks(){
		return this.finalblocks;
	}
	/**
	 * Prints the tempgrid 
	 * 
	 * @assumes debugging reasons
	 * @exception none
	 * @postcondition nothing
	 */
	public void printTempGrid(){
		for (int i =0; i<tempgrid.length;i++){
			for (int j = 0; j<tempgrid[0].length;j++){
				System.out.print(tempgrid[i][j]+" "); //prints out row in one line
			}
			System.out.println(); //new line for new row. 
		}
	}


	public Game getCurrGame(){
		return this.gamePlaying;
	}


	/**
	 * Prints the hashmap of the blocks based on the indentation level(nesting level)
	 * @param tab The indentation level of the block to be printed out
	 * @param blocks Takes hashmap to print out the contents of the hashmap
	 * eat(0),turnleft(1),move(2),if(3),elseif(4),else(5),while(6),repeat(7), function(8)
	 */
	public void printBlocks(int tab, HashMap<Integer,Block> blocks){
		int type;
		String tabStr="";
		for(int i =0; i<tab; i++){
			tabStr+='\t';
		}
		ArrayList<Integer> kList = new ArrayList<Integer>();
		for(Integer z: blocks.keySet()){
			kList.add(z);
		}
		Collections.sort(kList);
		for(Integer b: kList){
			Block block = blocks.get(b);
			System.out.print(block.getlineBegin());
			type = block.getType();
			if(type==0){ //eat = terminal so no nesting
				System.out.println(tabStr+"Eat");
			}else if(type==1){ //turn left  = terminal so no nesting
				System.out.println(tabStr+"TurnLeft");
			}else if(type==2){ //move = terminal so no nesting
				System.out.println(tabStr+"Move");
			}else if(type==3){ //if
				System.out.println(tabStr+"If "+block.getCond());
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");
			}else if(type==4){ //else if
				System.out.println(tabStr+"ElseIf "+block.getCond());
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");
			}else if(type==5){//else
				System.out.println(tabStr+"Else ");
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");
			}else if(type==6){//while
				System.out.println(tabStr+"While "+block.getCond());
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");;
			}else if(type==7){//repeat
				System.out.println(tabStr+"Repeat "+block.getRepeat());
				tabStr+='\t';
				System.out.println(block.getlineBegin()+1+tabStr+"begin");
				if(!(block.getNestedBlocks().isEmpty())){
					int tempTab = tab+1;
					printBlocks(tempTab,block.getNestedBlocks());
				}
				System.out.println(block.getlineEnd()+tabStr+"end");
			}else if(type==8){//function = CANNOT HAVE NESTED BLOCKS!!!
				Function f = gamePlaying.functions.get(block.getFunctionNum());
				System.out.println(tabStr+f.getName());
			}	
			tabStr="";
			for(int j =0; j<tab; j++){//reset the tabs
				tabStr+='\t';
			}
		}

	}
	//////////////////////////////////DEBUGGIN METHODS END////////////////////////////////////////////
	/**
	 * First View calls this, and then when user has entered the information, they will call
	 * finishCreateBlocks method if the user clicks ok, otherwise, click cancelBlock, if user clicks cancel
	 * @param type Enumerated type of the object
	 * @param begin The beginLine fo the object so the line number it starts at
	 * @param numLines is the number of lines of the code entered since this method is called several times
	 * @param cond This is for while and if statements AND it also sends the integer for Repeat!!
	 * @assumes if same function is called with c, the function was cancelled at some point so we ignore what we have currently 
	 * @assuems if same function is called with e, the function was finished so we add to the list
	 * 
	 * 0 is good
	 * 1 is ///////////////////ERROR: Number of repetitions was not selected!//////////////
	 * 2 is ///////////ERROR: Function not selected////////////////////////////
	 * 3 is ///////////////ERROR: Illegal funciton entered!!!!!/////////////
	 * 4 is //////////////////////////Error: "If" has to exist in order to use "Else If" or "Else"////////
	 * 5 is ////////////////////////////Error: Need to insert "Else If" or "Else" after an "If" statement
	 */
	public int createBlocks(int type, int begin, int numLines, String cond){
		if(type=='c'){//tried to create block but canceled so cancel the block we have currently
			this.userCodingNow=this.parent;
			return 0;
		}else if((type=='e') && (this.userCodingNow!=null)){//finished coding for the block so put into the correct spot
			this.userCodingNow.setLineEnd(begin+numLines-1);	
			int currType= this.userCodingNow.getType();
			if(currType==7){//repeat block so turn cond into int and store in repeat
				int repeat=-1;
				if(cond==null){
					
					///////////////////ERROR: Number of repetitions was not selected!//////////////
					return 1;
				}else{ //no need to check if cond is int or not since view will provide int for it 
					repeat =Integer.valueOf(cond);
				}
				this.userCodingNow.setRepeat(repeat);

			}else if(currType==8){//user-defined FUNCTION block so find int for cond and store int in functionNum
				int functionNum=-1;
				if(cond==null){
					
					///////////ERROR: Function not selected////////////////////////////
					return 2;
				}else{
					for(int i=0; i<gamePlaying.functions.size(); i++){
						if(gamePlaying.functions.get(i).getName().equals(cond)){
							functionNum= i;
							break;
						}
					}
				}
				if(functionNum==-1){ //despite searching for it!! 
				
					///////////////ERROR: Illegal funciton entered!!!!!/////////////
					return 3;
				}
				this.userCodingNow.setFunctionNum(functionNum);	
			}else if(currType==3 || currType==6){ //if and while loops
				this.userCodingNow.setCond(cond);
			}else if(currType==4){ //for else if, we need to check if parent == if => parent cannot be null
				this.userCodingNow.setCond(cond);
			}

			if(parent==null){ //insert into gamePlaying.blocks and cascade!!!
				/*So insert only happens to main, the rest are edit and delete so 
				we first check if the begin line we are given already exsits in the 
				main, if it does, we cascade, then insert to not delete the current 
				block at that number. else we simply add = works for both between lines 
				and end of code.*/
				for (int key: this.gamePlaying.getBlocks().keySet()){
					if(key==begin){
						cascadeNumberingChanges(begin, this.userCodingNow.getlineEnd()-this.userCodingNow.getlineBegin()+1, this.userCodingNow, gamePlaying.getBlocks(),true);
						this.gamePlaying.getBlocks().put(begin, this.userCodingNow);
						this.userCodingNow=null;
						return 0;
					}
					
				}//get past this means, end of lines!
				this.gamePlaying.getBlocks().put(begin, this.userCodingNow);
			} //we ended this so parent is now the currBlock coded
			this.userCodingNow=parent;
			if(this.parent!=null){
				this.parent=this.userCodingNow.getParent();
			}
			return 0;
		}else{ //first time making a block
			Block b = new Block();
			b.setlineBegin(begin);
			b.setType(type);
			if(type=='e'){
				return 0;
			}
			if((type==4) || (type==5)){ //SPECIAL FOR ELSE IF AND ELSE!!!
				Block parIf = null; 
				if(this.userCodingNow==null){ //find in main level = no nesting
					for(int k: this.gamePlaying.getBlocks().keySet()){
						if(this.gamePlaying.getBlocks().get(k).getType()==3 && k<begin){//after checking all of them it sets it to the last if just less than the current line
							parIf = gamePlaying.getBlocks().get(k);
						}
					}
				}else{ //find in parent's level!
					for(int k: this.userCodingNow.getNestedBlocks().keySet()){
						int tempTP = this.userCodingNow.getNestedBlocks().get(k).getType();
						if((tempTP==3 || tempTP==4) && k<begin){//after checking all of them it sets it to the last if just less than the current line
							parIf = this.userCodingNow.getNestedBlocks().get(k);
						}
					}
				}
				if(parIf==null){
				
					//////////////////////////Error: "If" has to exist in order to use "Else If" or "Else"////////
					//not valid cuz the parent for else if and else has to be if!!! so tell them not valid code
					return 4;
				}else if(parIf.getlineEnd()+1!=begin){
					
					//So we are trying to insert the else if or else after the if for else if OR if/else if for ELSE!!!
					////////////////////////////Error: Need to insert "Else If" or "Else" after an "If" statement
					return 5;
				}else{
					if(this.userCodingNow!=null){ //curr not null so we need to set current to user playing and parent to curr
						b.setParent(parIf.getParent()); //set else if or else stuff's parent to the parent of if block!!
						this.parent=parIf.getParent();
						this.userCodingNow=b;
						if(parent!=null){ //insert into parent's block
							if(parent.getNestedBlocks().get(begin)==null){ //nothing there put
								parent.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
							}else{ 
								HashMap<Integer,Block> tempHash = new HashMap<Integer,Block>();
								cascadeNumberingChanges(begin, 1, b, gamePlaying.getBlocks(), true); //cascade first then put into it!!
								parent.getNestedBlocks().put(begin, b);
							}
						}
					}else{
						b.setParent(parIf.getParent());
						this.userCodingNow=b; //don't put in if in main;s nesting
					}
				}
			}else{
				if(this.userCodingNow!=null){ //curr not null so we need to set current to user playing and parent to curr
					b.setParent(this.userCodingNow);
					this.parent=this.userCodingNow;
					this.userCodingNow=b;
					if(parent!=null){ //insert into parent's block
						if(parent.getNestedBlocks().get(begin)==null){ //nothing there put
							parent.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
						}else{ 
							HashMap<Integer,Block> tempHash = new HashMap<Integer,Block>();
							cascadeNumberingChanges(begin, 1, b, gamePlaying.getBlocks(), true); //cascade first then put into it!!
							parent.getNestedBlocks().put(begin, b);
						}
					}
				}else{
					this.userCodingNow=b; //don't put in if its in main's nesting
				}
			}
			return 0;
		}
	}
	/**
	 * Deletes/clears all blocks in the main
	 */
	public void clearBlocks(){
		gamePlaying.setBlocks(new HashMap<Integer,Block>());
	}

	/**
	 * Goes through the userCodingTemp blocks to find the parent by finding the innermost block
	 * that does not have a line end. 
	 * @param begin line number of the current block we are trying to insert into the parent we are looking for
	 * @return the parent block to search through for nesting purposes
	 */
	private Block findCurrParent(int begin, Block parent) {
		if(parent==null || parent.getNestedBlocks().isEmpty()){//case when there is no parent
			return null;
		}else if(parent.getNestedBlocks().isEmpty() ==false){ //go through nested blocks to see if b is the nested block
			for(int b: parent.getNestedBlocks().keySet()){
				if (b==begin){
					return parent;
				}
			}
		}else{ //recurse on each nested block
			for(int b: parent.getNestedBlocks().keySet()){
				return findCurrParent(begin,parent.getNestedBlocks().get(b));
			}
		}
		return null;
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
	/*public void initBuiltIn (){
		Function moveAhead= new Function("Move Forward");
		Block moveAheadBlock= new Block();
		moveAheadBlock.setType(2);
		moveAhead.getBlockInstructions().put(2, moveAheadBlock);

		Function eat= new Function("Eat");
		Block eatBlock= new Block();
		eatBlock.setType(0);
		eat.getBlockInstructions().put(0, eatBlock);

		Function turnLeft= new Function("Turn Left");
		Block turnLeftBlock= new Block();
		turnLeftBlock.setType(1);
		turnLeft.getBlockInstructions().put(1, turnLeftBlock);
	}*/



	/**
	 * Creates and stores the builtIn functions in the controller
	 * 
	 * @assumes nothing
	 * @return nothing
	 * @exception none
	 * @postcondition stores built in functions in the controller
	 * 
	 */
	/*public void createBuiltIn(){
		if(this.functions.isEmpty()){
			this.functions = new ArrayList<Function>();
			initBuiltIn();
		}
	}*/

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
	public void createGame(String name){
		this.gamePlaying = new Game(name);
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
		ArrayList<Game> gamelist= Start.StartGerbil.backend.getGameList();
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
		this.tempgerbil = new Gerbil();
	}
	/**
	 * returns tempgerbil
	 */
	public Gerbil getTempGerbil(){
		return this.tempgerbil;
	}
	/**
	 * runs the user written code by first
	 * compiling blocks (finalblocks arraylist) using tempgrid and tempgerbil
	 * to show what is happening step by step
	 * until error/ends at goal
	 * @return int- (0)-win the game 
	 * (1)-error eating/no food on square trying to eat
	 * (2)-error wall/cannot move forward bc there is wall
	 * (3)-error turning left OR miscellaneous error on runBlocks()
	 * (4)-error failure to reach goal/water
	 * 
	 * (-1)-error in parsing/could not parse
	 * (-2)-error INFINITE LOOP 
	 */
	public int runBlocks(){
		int success=compileBlocks();
		resetTempGrid();
		if(success==-1){// error in parsing
			return -1;
		}
		if(success==-2){ // infinite loop error
			return -2;
		}
		ArrayList<String> templist= new ArrayList<String>();
		String command;
		for(int i=0; i<finalblocks.size(); i++){
			command=finalblocks.get(i);
			if(command.equals("Eat")){
				boolean eat= eat(tempgerbil.getX(), tempgerbil.getY(),tempgrid);
				if(eat==false){
					//errorEat() dialogue box??;
					for(int j=0; j<i; j++){
						templist.add(finalblocks.get(j));
					}
					finalblocks= templist;
					return 1;
				}
			}else if(command.equals("Turn Left")){
				boolean turnleft= turnLeft(tempgerbil);
				if(turnleft==false){
					//error turning left, this shouldn't ever happen
					for(int j=0; j<i; j++){
						templist.add(finalblocks.get(j));
					}
					finalblocks= templist;
					return 3;
				}
			}else if(command.equals("Move Forward")){
				boolean moveforward= moveForward(tempgerbil);
				if(moveforward==false){
					//errorWall() dialogue box??
					for(int j=0; j<i; j++){
						templist.add(finalblocks.get(j));
					}
					finalblocks= templist;
					return 2;
				}else{
					if(isthereWater(tempgerbil.getX(), tempgerbil.getY())){
						//YOU WIN THE GAME dialogue box??
						for(int j=0; j<=i; j++){
							templist.add(finalblocks.get(j));
						}
						finalblocks= templist;
						return 0;
					}
				}
			}else{
				for(int j=0; j<i; j++){
					templist.add(finalblocks.get(j));
				}
				finalblocks= templist;
				return 3;
			}
		}
		//error Did not reach water
		return 4;

	}

	/**
	 * goes through user coded blocks and stores commands in arraylist finalblocks in the order and
	 * number of times they will be executed for play
	 * (compiles blocks)
	 */
	public int compileBlocks(){
		//clears finalblocks arraylist in case it's not empty
		finalblocks.clear();
		//resets tempgrid and tempgerbil to original states
		resetTempGrid();
		HashMap<Integer,Block> blocklist= gamePlaying.getBlocks();
		ArrayList<Integer> keylist= new ArrayList<Integer>();

		//initialize visited hashmap and arraylist of keys
		//visited= new HashMap<Integer,Boolean>();
		for(Entry<Integer,Block> entry: blocklist.entrySet()){
			//visited.put(entry.getKey(), false);
			keylist.add(entry.getKey());

		}
		//get sort list of keys	
		ArrayList<Integer> sortedkeys= sortKeys(keylist);
		//parse unvisited blocks
		for(int i=0; i<sortedkeys.size(); i++){
			//if(visited.get(sortedkeys.get(i))){
			//continue;
			//}else{
			Block block= blocklist.get(sortedkeys.get(i));
			int success=parseBlock(block);
			if(success==-1){
				System.out.println("error in parsing");
				return -1;   //error in parsing
			}
			if(success==-2){//infinite loop error
				System.out.println("infinite loop error");
				return -2;
			}
			if(success==1){//if or else if so skip rest of else if or else statements
				if(i+1<sortedkeys.size()){
					int j;
					for(j=i+1; j<sortedkeys.size(); j++){
						if(blocklist.get(sortedkeys.get(j)).getType()!=4 || blocklist.get(sortedkeys.get(j)).getType()!=5){
							break;
						}
					}
					i=j;
					if(blocklist.get(sortedkeys.get(i)).getType()==4 || blocklist.get(sortedkeys.get(i)).getType()==5){
						break; //this means last block is else if or else block
					}
				}
			}
			//}
		}
		return 0;
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
	public int parseBlock(Block block){
		//if(!isFunction){
		//int num= block.getlineBegin();
		//visited.remove(block.getlineBegin());
		//visited.put(num,true);
		//}
		if(this.finalblocks.size()>1000){
			boolean loop=true;
			String command= finalblocks.get(finalblocks.size()-1);
			for(int i=finalblocks.size()-2; i>finalblocks.size()-900; i--){
				if(!finalblocks.get(i).equals(command)){
					loop=false;
					break;
				}
			}
			if(loop){
				//ERROR infiniteLoop() error
				this.finalblocks= new ArrayList<String>(); //clear finalblocks arraylist
				return -2;
			}
		}
		HashMap<Integer,Block> nestedblocklist= block.getNestedBlocks();
		//initialize and sort nested keys
		ArrayList<Integer> nestedkeylist= new ArrayList<Integer>();
		for(Entry<Integer,Block> entry: nestedblocklist.entrySet()){
			nestedkeylist.add(entry.getKey());
		}
		ArrayList<Integer> nestedsortedkeys= sortKeys(nestedkeylist);

		if(block.getType()==3){//"if"
			if(block.getCond().equals("There'sWall?")){
				if(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0; 					
				}
			}else if(block.getCond().equals("There'sFood")){
				if(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
					return 1;
				}else{
					return 0;		
				}
			}else if(block.getCond().equals("There'sNoWall")){
				if(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0;		
				}

			}else if(block.getCond().equals("There'sNoFood")){
				if(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0;		
				}

			}
			return -1;
		}else if(block.getType()==5){//"else"
			for(int i=0; i<nestedsortedkeys.size(); i++){
				Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
				int success=parseBlock(nestedblock);
				if(success==-1){
					return -1;
				}
				if(success==1){//if or else if so skip rest of else if or else statements
					if(i+1<nestedsortedkeys.size()){
						int j;
						for(j=i+1; j<nestedsortedkeys.size(); j++){
							if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
								break;
							}
						}
						i=j;
						if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
							break; //this means last block is else if or else block
						}
					}
				}
			}
			return 0;
		}else if(block.getType()==4){//"else if"
			if(block.getCond().equals("There'sWall?")){
				if(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0; 					
				}
			}else if(block.getCond().equals("There'sFood")){
				if(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
					return 1;
				}else{
					return 0;		
				}
			}else if(block.getCond().equals("There'sNoWall")){
				if(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
					return 1;
				}else{
					return 0;		
				}

			}else if(block.getCond().equals("There'sNoFood")){
				if(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
					return 1;
				}else{
					return 0;		
				}

			}
			return -1;
		}else if(block.getType()==6){//"while"
			if(block.getCond().equals("There'sWall?")){
				while(isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success= parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
				}
				return 0; 					
			}else if(block.getCond().equals("There'sFood")){
				while(isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}
				}
				return 0;		
			}else if(block.getCond().equals("There'sNoWall")){
				while(!isthereWall(tempgerbil.getFrontX(),tempgerbil.getFrontY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
				}
				return 0;			
			}else if(block.getCond().equals("There'sNoFood")){
				while(!isthereFood(tempgerbil.getX(),tempgerbil.getY())){
					for(int i=0; i<nestedsortedkeys.size(); i++){
						Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(i));
						int success=parseBlock(nestedblock);
						if(success==-1){
							return -1;
						}
						if(success==1){//if or else if so skip rest of else if or else statements
							if(i+1<nestedsortedkeys.size()){
								int j;
								for(j=i+1; j<nestedsortedkeys.size(); j++){
									if(nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(j)).getType()!=5){
										break;
									}
								}
								i=j;
								if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
									break; //this means last block is else if or else block
								}
							}
						}
					}	
				}
				return 0;					
			}
			return -1;	
		}else if(block.getType()==7){//"repeat"
			int repeat= block.getRepeat();
			if(repeat==-1){
				return -1;
			}
			for(int i=0; i<repeat; i++){
				for(int j=0; j<nestedsortedkeys.size(); j++){
					Block nestedblock= nestedblocklist.get(nestedsortedkeys.get(j));
					int success=parseBlock(nestedblock);
					if(success==-1){
						return -1;
					}
					if(success==1){//if or else if so skip rest of else if or else statements
						if(i+1<nestedsortedkeys.size()){
							int k;
							for(k=i+1; k<nestedsortedkeys.size(); k++){
								if(nestedblocklist.get(nestedsortedkeys.get(k)).getType()!=4 || nestedblocklist.get(nestedsortedkeys.get(k)).getType()!=5){
									break;
								}
							}
							i=k;
							if(nestedblocklist.get(nestedsortedkeys.get(i)).getType()==4 || nestedblocklist.get(nestedsortedkeys.get(i)).getType()==5){
								break; //this means last block is else if or else block
							}
						}
					}
				}
			}
			return 0;	
		}else if(block.getType()==0){//"eat"
			finalblocks.add("Eat");
			eat(tempgerbil.getX(),tempgerbil.getY(),tempgrid);
			return 0;
		}else if(block.getType()==1){//"turn left"
			finalblocks.add("Turn Left");
			turnLeft(tempgerbil);
			return 0;
		}else if(block.getType()==2){//"move forward"
			finalblocks.add("Move Forward");
			moveForward(tempgerbil);
			return 0;
		}else if(block.getType()==8){//user defined function
			ArrayList<Function> functionlist= gamePlaying.functions;
			Function function= functionlist.get(block.getFunctionNum());
			HashMap<Integer,Block> fnestedblocklist= function.getBlockInstructions();
			ArrayList<Integer> fnestedkeylist= new ArrayList<Integer>();
			for(Entry<Integer,Block> fentry: fnestedblocklist.entrySet()){
				fnestedkeylist.add(fentry.getKey());
			}
			ArrayList<Integer> fnestedsortedkeys= sortKeys(fnestedkeylist);				
			for(int i=0; i<fnestedsortedkeys.size(); i++){
				Block fnestedblock= fnestedblocklist.get(fnestedsortedkeys.get(i));
				//this.isFunction=true;
				int success=parseBlock(fnestedblock);
				if(success==-1){
					return -1;
				}
				if(success==1){//if or else if so skip rest of else if or else statements
					if(i+1<fnestedsortedkeys.size()){
						int j;
						for(j=i+1; j<fnestedsortedkeys.size(); j++){
							if(fnestedblocklist.get(fnestedsortedkeys.get(j)).getType()!=4 || fnestedblocklist.get(fnestedsortedkeys.get(j)).getType()!=5){
								break;
							}
						}
						i=j;
						if(fnestedblocklist.get(fnestedsortedkeys.get(i)).getType()==4 || fnestedblocklist.get(fnestedsortedkeys.get(i)).getType()==5){
							break; //this means last block is else if or else block
						}
					}
				}
			}
			//this.isFunction=false;
			return 0;
		}
		return -1;
		//ERROR block does not have a valid type
	}	

	/**
	 * Will edit a block at a given index/position selected by the user with a new
	 * HashMap containing blocks of newly provided instruction 
	 * 
	 * @assumes Newly entered data must be valid/nested block instructions must be valid
	 * 			based on guiding system
	 * @exception none
	 * @postcondition Will edit a block of data
	 * 
	 * @param type Type of block = same as create blocks = can be 'e','c' or enumeration
	 * @param begin line that was selected via highlighting for editing = can be terminal 
	 * or non terminal => that is the block that will be found and added to!!
	 * @param numLines Lines modified so it is line end - line begin +1 so ex. terminals are 1
	 * @param cond conditional if if,while,else if stuff inserted, else it is integer for repeat 
	 */
	public char editBlock(int type, int begin, int numLines, String cond){
		Block par = searchForBlock(begin, gamePlaying.getBlocks()); //gets the block we want to edit/insert into

		if(type=='c'){//tried to create block but canceled so cancel the block we have currently
			this.userCodingNowEdit=null;//this is all that needs to be done here!
			this.userCodingNowEdit=this.parentEdit;
			return 'g';
		}else if(type=='e'){//finished coding for the block so put into the correct spot
			this.userCodingNowEdit.setLineEnd(begin+numLines-1);	
			int currType= this.userCodingNowEdit.getType();
			if(currType==7){//repeat block so turn cond into int and store in repeat
				int repeat=-1;
				if(cond==null){
					///////////////////ERROR: Number of repetitions was not selected!//////////////
					return 'n';
				}else{ //no need to check if cond is int or not since view will provide int for it 
					repeat =Integer.valueOf(cond);
				}
				this.userCodingNowEdit.setRepeat(repeat);

			}else if(currType==3 || currType==6){ //if and while loops
				this.userCodingNowEdit.setCond(cond);
			}else if(currType==4){ //for else if, we need to check if parent == if => parent cannot be null
				if(parentEdit==null || this.userCodingNowEdit.getParent().getType()!=3){
					//////////////ERROR: Illegal Else if entered. If statement has to exist for else if to exist////////
					return 'n';
				}
			}else if(currType== 5){ //for else, we need to check if parent==if or else if! else error
				if(parentEdit==null || (this.userCodingNowEdit.getParent().getType()!=3)){
					//////////////ERROR: Illegal Else if entered. If statement has to exist for else if to exist////////
					return 'n';
				}
			}

			if(parentEdit.equals(par)){ //insert into gamePlaying.blocks and cascade!!!
				/*So insert only happens to main, the rest are edit and delete so 
				we first check if the begin line we are given already exsits in the 
				main, if it does, we cascade, then insert to not delete the current 
				block at that number. else we simply add = works for both between lines 
				and end of code.*/
				for (int key: this.gamePlaying.getBlocks().keySet()){
					if(key==begin){
						cascadeNumberingChanges(begin, this.userCodingNowEdit.getlineEnd()-this.userCodingNowEdit.getlineBegin()+1, this.userCodingNowEdit, gamePlaying.getBlocks(), true);
						this.gamePlaying.getBlocks().put(begin, this.userCodingNowEdit);
						this.userCodingNowEdit=null;
						return 'g';
					}
				}//get past this means, end of lines!
				this.gamePlaying.getBlocks().put(begin, this.userCodingNowEdit);
			} //we ended this so parent is now the currBlock coded
			this.userCodingNowEdit=parentEdit;
			if(parentEdit!=null){
				this.parentEdit=this.userCodingNowEdit.getParent();
			}

			return 'g';
		}else{ //first time making a block
			Block b = new Block();
			b.setlineBegin(begin);
			b.setType(type);
			if((type==4 || type==5) && (this.parentEdit==null||this.parentEdit.getType()!=3)){
				return 'n'; //not valid cuz the parent for else if and else has to be if!!! so tell them not valid code
			}
			if(this.userCodingNowEdit!=null){ //curr not null so we need to set current to user playing and parent to curr
				b.setParent(this.userCodingNowEdit);
				this.parentEdit=this.userCodingNowEdit;
				this.userCodingNowEdit=b;
				if(this.parentEdit!=null){ //inserting into parent's block
					parentEdit.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
				}
			}else{
				this.userCodingNowEdit=b;
				this.parentEdit=par;
			}
			return 'g';

		}
	}
	public Block getBlockByLineMain(int line){
		for(int k: this.gamePlaying.getBlocks().keySet()){
			Block temp = this.gamePlaying.getBlocks().get(k);
			if(line>=temp.getlineBegin()&& line <=temp.getlineEnd()){
				return temp;
			}
		}
		return null;
	}
	
	public Block getBlockByLineUserFunction(int lineS) {
		for(int k: this.tempFunctionBlockInstructions.keySet()){
			Block temp = this.tempFunctionBlockInstructions.get(k);
			if(lineS>=temp.getlineBegin()&& lineS <=temp.getlineEnd()){
				return temp;
			}
		}
		return null;
	}

	
	/**
	 * For any line number selected, it will return the block in that position
	 * @param line Line Number
	 * @return Block at that line Number in the main
	 */
	public Block getBlockByLine(int line){
		for(int k: this.gamePlaying.getBlocks().keySet()){
			Block temp = this.gamePlaying.getBlocks().get(k);
			if(line>=temp.getlineBegin()&& line <=temp.getlineEnd()){
				for(int z: temp.getNestedBlocks().keySet()){
					Block b = temp.getNestedBlocks().get(z);
					if(line>=b.getlineBegin() && line <=b.getlineEnd()){
						return b;
					}
				}
				return temp;
			}
		}
		return null;
	}
	
	public void deleteBlock(Block toDel) {
		
		if(toDel.getParent()==null){ //in main nesting 
			int currDiff = toDel.getlineEnd()-toDel.getlineBegin()+1;
			this.gamePlaying.getBlocks().remove(toDel.getlineBegin());
			cascadeNumberingChanges(toDel.getlineBegin(),-1*currDiff, toDel, gamePlaying.getBlocks(), true);//MAKE SURE -1*currDIFF!!!!!
		}else{ //some other blocks's nesting
			Block p = toDel.getParent();
			p.getNestedBlocks().remove(toDel.getlineBegin());
			int currDiff = toDel.getlineEnd()-toDel.getlineBegin()+1;
			cascadeNumberingChanges(toDel.getlineBegin(),-1*currDiff, toDel, gamePlaying.getBlocks(), true);//MAKE SURE -1*currDIFF!!!!!
		}
		
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
	 
	public boolean deleteBlock(Block b){
		//Block b = searchForBlock(pos, gamePlaying.getBlocks()); //gets the block we want to delete
		
		if(b==null){
			return false; //failure to find block
		}
		int currDiff = b.getlineEnd()-b.getlineBegin()+1;
		Block pare = b.getParent();
		HashMap<Integer,Block> nested = null;
		//UPDATE: new cascade method updates line numbers, and creates new hashmap for each
		//    level based on all existing blocks(before and after pos), so should delete first, then cascade

		if(pare==null){ //no nesting level
			nested = gamePlaying.getBlocks();
		}else{ 
			nested = pare.getNestedBlocks();
		}
		nested.remove(b.getlineBegin());
		cascadeNumberingChanges(b.getlineBegin(),-1*currDiff, b, gamePlaying.getBlocks());//MAKE SURE -1*currDIFF!!!!!
		if(b.getType()==3){//if statement so remove all subsequent ifs and elses
			java.util.Iterator<Integer> it =nested.keySet().iterator();
			int k;
			while(it.hasNext()){
				k = it.next();
				Block temB = nested.get(k);
				int temTp =temB.getType(); 
				if((temTp==4) || (temTp==5)){
					deleteBlock(temB); 
				}else {///if(temTp==3){//different if block so exit loop
					break;
				}
			}
		}

		return true;
		//Will call parseBlock - must reparse the block to see if deletion invalidates a block - i.e. if statement
		//Question: should we have something that asks them if they want to delete = view asks for sure or not
		//if invalidates = do not delete code...
	}*/



	/**
	 * Will insert instruction to an already created Block of code
	 * 
	 * @assumes Block to insert to exists and has been validated. 
	 * @exception none
	 * @postcondition Inserts instruction to Block
	 * 
	 * @param key id/key/linebegin of Block to add instruction to
	 * @param instruction nested block instructions to add to block with key
	 * 
	 */

	//ISN'T THIS THE SAME AS EDIT BLOCK??? USER CAN ONLY INSERT INSTRUCTION BY EDITING BLOCK
	/*public boolean insertInstructionToBlock(int key, HashMap<Integer,Block> instruction){
		//find the block that has the line begin = key and inserts instruction in the right place
		//cascades the changes in line numbers 
		Block b = searchForBlock(key, gamePlaying.getBlocks()); //gets the block we want


		int currdiff= keylist.get(keylist.size()-1)  -  keylist.get(0);
		Block tempblock= gamePlaying.getBlocks().get(key);
		cascadeNumberingChanges(key, currdiff, tempblock);

		//gamePlaying.getBlocks().put(key, instruction);


		return false;
	}*/

	/**
	 * Will insert a NEW block of code into a user specified index/position
	 *
	 * @assumes Index to insert block is valid
	 * @exception none
	 * @postcondition Inserts new block of code
	 * 
	 * @param pos index/position of block to insert = it is the line number 
	 * @param b block to be inserted
	 * @return false/ true; false if inserting the Block fails, true if it succeeds
	 */
	public void insertBlockToMain(int id, Block b){
		//Block parent = findParentInMain(id); = parent is null! => cannot find parent
		Block reference = null;
		int temp;
		if(b.getParent()==null){//no parents, parent is MAIN => can insert to main 
			for(int key: gamePlaying.getBlocks().keySet()){
				if(id<key){ //if key is bigger than id 
					reference = gamePlaying.getBlocks().get(key);//find sibling block as reference when cascade
					temp = key;
					//break;
				}
			}
		}else{ //parent block not null
			for(int key: parent.getNestedBlocks().keySet()){
				reference = parent.getNestedBlocks().get(key);//find sibling block as reference when cascade
				break;
			}
		}
		if(reference==null){
			//no existing blocks in game
			gamePlaying.getBlocks().put(id, b);
			return;
		}
		int currdiff= this.countblocks; //number of blocks/lines in block to insert
		this.countblocks=1; //reset countblocks
		cascadeNumberingChanges(id,currdiff, reference, gamePlaying.getBlocks(), true);
		//after cascade, should have a free spot in hashmap with key=id since moved original id block down to diff key
		parent.getNestedBlocks().put(id, b);

	}

	/**
	 * counts the total number of lines(blocks) in a block (including nested blocks)
	 */
	public void countBlocks(Block b){
		if(b.getNestedBlocks().isEmpty()){
			return;
		}
		for(int key: b.getNestedBlocks().keySet()){
			this.countblocks++;
			countBlocks(b.getNestedBlocks().get(key));
		}
	}

	/**
	 * Search for possible parent of block to insert in MAIN
	 * @param pos line number of block whose parent we are finding
	 */
	public Block findParentInMain(int pos){
		int n=pos+1;
		Block b;
		while(n>0){
			b= searchForBlock(n,gamePlaying.getBlocks());
			if(b.getlineEnd()==pos && b.getlineEnd()-b.getlineBegin()!=0){
				//return block found at n if block lineEnd==pos AND is not a function block
				return b;
			}
			n=n-1; //traverse upwards in line numbers
		}
		//at this point, no parent was found, so no parent exists
		return null;	
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
	public void insertToBlock(int id, Block b){
		Block parent = searchForBlock(id, gamePlaying.getBlocks()); //get parent since we know parent's line number
		//note: even if the nested blocks has that key already, we need to move it down by calling this funciton again with b's begin+end 
		if(parent.getNestedBlocks().keySet().contains(b.getlineBegin())){
			Block temp = parent.getNestedBlocks().get(b.getlineBegin());
			parent.getNestedBlocks().put(b.getlineBegin(), b); 
			//Will call searchForBlock to find block of the given id and insert insert b to it
			int currDiff = b.getlineEnd()-b.getlineBegin();
			cascadeNumberingChanges(b.getlineBegin(),currDiff,b, gamePlaying.getBlocks(), true);
			insertBlockToMain(b.getlineBegin()+b.getlineEnd(), temp);
		}
		parent.getNestedBlocks().put(b.getlineBegin(), b); 
		//Will call searchForBlock to find block of the given id and insert insert b to it
		int currDiff = b.getlineEnd()-b.getlineBegin();
		cascadeNumberingChanges(b.getlineBegin(),currDiff,b, gamePlaying.getBlocks(), true);
	}

	public int[] callHighlight(int line){
		int[] bInfo;
		Block b = getHighlighting(line,this.gamePlaying.getBlocks());
		bInfo = new int[b.getlineEnd()-b.getlineBegin()+1];
		for(int index =0, i =b.getlineBegin();index<bInfo.length;index++,i++){
			bInfo[index]=i;
		}
		return bInfo;
	}


	/**
	 * Figures out the given line number's parent block's line number = good for highlighting
	 * 
	 * @assumes the line number exists so we search for it and then see the type to return parent or current block
	 * @param b the hashmap of blocks to search for the parent block
	 * @return the parent block is returned
	 */
	public Block getHighlighting(int line, HashMap<Integer,Block> blocks){
		Block b = searchForBlock(line, blocks); //gets the block the line is in
		
		int type = b.getType();

		//eat(0),turnleft(1),move(2),if(3),elseif(4),else(5),while(6),repeat(7), function(8),
		if(type>=0 && type <=2){ //[0-2]
			return b;

			//below is for terminal so see if they belong in larger block. no parent, then return them
			/**if (b.getParent()!=null){
				return b.getParent();
			}else{
				return b;
			}*/
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
	 * 			/IMPORTANT: lineBegin is the line number above SELECTED line number in view
	 * @param currDiff Current/new difference in end - start
	 * @param b Block that the change occurred in
	 * @assumes have checked if prevDiff==currDiff to make sure we don't use this method if it is
	 */
	public void cascadeNumberingChanges(int lineBegin, int currDiff, Block b, HashMap<Integer,Block> mainblocks, boolean mainmap){
		if(b.getParent()!=null){
			b.getParent().setLineEnd(b.getParent().getlineEnd()+currDiff);
		}
		HashMap<Integer,Block> nb;
		HashMap<Integer,Block> tempnb= new HashMap<Integer,Block>();
		boolean lastBlocks=false;
		if(b.getParent()==null){
			nb= mainblocks;
			//nb= gamePlaying.getBlocks();
			lastBlocks=true;
		}else{
			nb = b.getParent().getNestedBlocks();//get hashmap containing b and sister blocks
		}
		Block temp=null;
		for(int key: nb.keySet()){
			if (key>=lineBegin){ //cascade the difference to the blocks after b!
				temp=nb.get(key); //get the object
				if(!temp.getNestedBlocks().isEmpty()){
					for(int nestedkey: temp.getNestedBlocks().keySet()){
						cascadeInward(lineBegin,currDiff,temp.getNestedBlocks().get(nestedkey));
						break;
					}
				}
				temp.setlineBegin(temp.getlineBegin()+currDiff); //change line begin with the difference
				temp.setLineEnd(temp.getlineEnd()+currDiff); 
				tempnb.put(temp.getlineBegin(), temp); //put each updated block in temp hashmap with new key
			}else{ //put each un-updated block in temp hashmap with original key
				tempnb.put(key, nb.get(key));
			}
		} 
		if(lastBlocks==true){
			if(mainmap){
				gamePlaying.setBlocks(tempnb);
				return;
			}
			mainblocks=tempnb; //replace original MAIN hashmap with new/updated MAIN hashmap
			return; //no more higher level to get to
		}
		b.getParent().setNestedBlocks(tempnb); //replace original nested hashmap with new/updated nested hashmap
		cascadeNumberingChanges(lineBegin,currDiff,b.getParent(),mainblocks,mainmap); //recurse to go higher
	}
	
	

	/**
	 * cascadesNumberingChanges Inward to nested blocks
	 * @param lineBegin The block that was changed, inserted, deleted etc's line begin. 
	 * 			/IMPORTANT: lineBegin is the line number above SELECTED line number in view
	 * @param currDiff Current/new difference in end - start
	 * @param b Block that the change occurred in
	 */
	public void cascadeInward(int lineBegin, int currDiff, Block b){
		HashMap<Integer,Block> nb;
		HashMap<Integer,Block> tempnb= new HashMap<Integer,Block>();
		nb = b.getParent().getNestedBlocks();//get hashmap containing b and sister blocks
		Block temp=null;
		for(int key: nb.keySet()){
			if (key>=lineBegin){ //cascade the difference to the blocks after b!
				temp=nb.get(key); //get the object
				if(!temp.getNestedBlocks().isEmpty()){
					for(int nestedkey: temp.getNestedBlocks().keySet()){
						cascadeInward(lineBegin,currDiff,temp.getNestedBlocks().get(nestedkey));
						break;
					}
				}
				temp.setlineBegin(temp.getlineBegin()+currDiff); //change line begin with the difference
				temp.setLineEnd(temp.getlineEnd()+currDiff); 
				tempnb.put(temp.getlineBegin(), temp); //put each updated block in temp hashmap with new key
			}else{ //put each un-updated block in temp hashmap with original key
				tempnb.put(key, nb.get(key));
			}	
		} 
		b.getParent().setNestedBlocks(tempnb);
	}

	//original cascadeNumberingChanges
	/**
	 * Cascades the line number changes to the rest of the code after insert, delete or edit
	 * @param lineBegin The block that was changed, inserted, deleted etc's line begin. 
	 * @param currDiff Current/new difference in end - start
	 * @param b Block that the change occured in
	 * @assumes have checked if prevDiff==currDiff to make sure we don't use this method if it is
	 */
	/*	public void cascadeNumberingChanges(int lineBegin, int currDiff, Block b){
		if(b.getParent()==null){
			return; //no more higher level to get to
		}
		HashMap<Integer,Block> nb = b.getParent().getNestedBlocks();//get hashmap containing b and sister blocks
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
	}*/

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
	public Block searchForBlock(int id, HashMap<Integer,Block> blocks){
		if(blocks.keySet().isEmpty()){ //no more nesting
			return null;
		}
		int tempCurr=0; //its ok if set to 0 because the check for is empty is already done.
		for (int curr: blocks.keySet()){
			if(curr==id){
				return blocks.get(curr);
			}else if(blocks.get(curr).getlineBegin()<=id && blocks.get(curr).getlineEnd()>=id){ 
				//the block contains the line number in it so search inside
				return blocks.get(curr);
			}
		}
		//exit out without finding it meaning it is not a particular block's begin line.
		//meaning we have to find the inside the closest inside and that is temp curr 
		return null;
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
		if(gamePlaying.getGrid().getSquareContent(y, x)=='k'
				|| gamePlaying.getGrid().getSquareContent(y, x)=='p'
				|| gamePlaying.getGrid().getSquareContent(y, x)=='a'){
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
		if(gamePlaying.getGrid().getSquareContent(y, x)=='w'){
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
	 * 	 * First View calls this, and then when user has entered the information, they will call
	 * finishCreateBlocks method if the user clicks ok, otherwise, click cancelBlock, if user clicks cancel
	 * @param type Enumerated type of the object
	 * @param begin The beginLine fo the object so the line number it starts at
	 * @param numLines is the number of lines of the code entered since this method is called several times
	 * @param cond This is for while and if statements AND it also sends the integer for Repeat!!
	 * @assumes if same function is called with c, the function was cancelled at some point so we ignore what we have currently 
	 * @assuems if same function is called with e, the function was finished so we add to the list
	 * @assumes function name may not be unique
	 * @exception none
	 * @postcondition Creates function iff function name is unique
	 * 
	 * @param name User provided function name, must be unique/valid
	 * @return newly instantiated Function object
	 * 
	 * 
	 */
	public int createFunctionBlocks(int type, int begin, int numLines, String cond){
		if(type=='c'){//tried to create block but canceled so cancel the block we have currently
			this.userCodingNowFunction=this.parentFunction;
			return 0;
		}else if((type=='e') && (this.userCodingNowFunction!=null)){//finished coding for the block so put into the correct spot
			this.userCodingNowFunction.setLineEnd(begin+numLines-1);	
			int currType= this.userCodingNowFunction.getType();
			if(currType==7){//repeat block so turn cond into int and store in repeat
				int repeat=-1;
				if(cond==null){
					
					///////////////////ERROR: Number of repetitions was not selected!//////////////
					return 1;
				}else{ //no need to check if cond is int or not since view will provide int for it 
					repeat =Integer.valueOf(cond);
				}
				this.userCodingNowFunction.setRepeat(repeat);

			}else if(currType==8){//user-defined FUNCTION block so find int for cond and store int in functionNum
				int functionNum=-1;
				if(cond==null){
					
					///////////ERROR: Function not selected////////////////////////////
					return 2;
				}else{
					for(int i=0; i<gamePlaying.functions.size(); i++){
						if(gamePlaying.functions.get(i).getName().equals(cond)){
							functionNum= i;
							break;
						}
					}
				}
				if(functionNum==-1){ //despite searching for it!! 
				
					///////////////ERROR: Illegal funciton entered!!!!!/////////////
					return 3;
				}
				this.userCodingNowFunction.setFunctionNum(functionNum);	
			}else if(currType==3 || currType==6){ //if and while loops
				this.userCodingNowFunction.setCond(cond);
			}else if(currType==4){ //for else if, we need to check if parent == if => parent cannot be null
				this.userCodingNowFunction.setCond(cond);
			}

			if(parentFunction==null){ //insert into gamePlaying.blocks and cascade!!!
				/*So insert only happens to main, the rest are edit and delete so 
				we first check if the begin line we are given already exsits in the 
				main, if it does, we cascade, then insert to not delete the current 
				block at that number. else we simply add = works for both between lines 
				and end of code.*/
				for (int key: this.tempFunctionBlockInstructions.keySet()){
					if(key==begin){
						cascadeNumberingChanges(begin, this.userCodingNowFunction.getlineEnd()-this.userCodingNowFunction.getlineBegin()+1, this.userCodingNowFunction, this.tempFunctionBlockInstructions,true);
						this.tempFunctionBlockInstructions.put(begin, this.userCodingNowFunction);
						this.userCodingNowFunction=null;
						return 0;
					}
					
				}//get past this means, end of lines!
				this.tempFunctionBlockInstructions.put(begin, this.userCodingNowFunction);
			} //we ended this so parent is now the currBlock coded
			this.userCodingNowFunction=parentFunction;
			if(this.parentFunction!=null){
				this.parentFunction=this.userCodingNowFunction.getParent();
			}
			return 0;
		}else{ //first time making a block
			Block b = new Block();
			b.setlineBegin(begin);
			b.setType(type);
			if(type=='e'){
				return 0;
			}
			if((type==4) || (type==5)){ //SPECIAL FOR ELSE IF AND ELSE!!!
				Block parIf = null; 
				if(this.userCodingNowFunction==null){ //find in main level = no nesting
					for(int k: this.tempFunctionBlockInstructions.keySet()){
						if(this.tempFunctionBlockInstructions.get(k).getType()==3 && k<begin){//after checking all of them it sets it to the last if just less than the current line
							parIf = this.tempFunctionBlockInstructions.get(k);
						}
					}
				}else{ //find in parent's level!
					for(int k: this.userCodingNowFunction.getNestedBlocks().keySet()){
						int tempTP = this.userCodingNowFunction.getNestedBlocks().get(k).getType();
						if((tempTP==3 || tempTP==4) && k<begin){//after checking all of them it sets it to the last if just less than the current line
							parIf = this.userCodingNowFunction.getNestedBlocks().get(k);
						}
					}
				}
				if(parIf==null){
				
					//////////////////////////Error: "If" has to exist in order to use "Else If" or "Else"////////
					//not valid cuz the parent for else if and else has to be if!!! so tell them not valid code
					return 4;
				}else if(parIf.getlineEnd()+1!=begin){
					
					//So we are trying to insert the else if or else after the if for else if OR if/else if for ELSE!!!
					////////////////////////////Error: Need to insert "Else If" or "Else" after an "If" statement
					return 5;
				}else{
					if(this.userCodingNowFunction!=null){ //curr not null so we need to set current to user playing and parent to curr
						b.setParent(parIf.getParent()); //set else if or else stuff's parent to the parent of if block!!
						this.parentFunction=parIf.getParent();
						this.userCodingNowFunction=b;
						if(parentFunction!=null){ //insert into parent's block
							if(parentFunction.getNestedBlocks().get(begin)==null){ //nothing there put
								parentFunction.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
							}else{ 
								HashMap<Integer,Block> tempHash = new HashMap<Integer,Block>();
								cascadeNumberingChanges(begin, 1, b, this.tempFunctionBlockInstructions, true); //cascade first then put into it!!
								parentFunction.getNestedBlocks().put(begin, b);
							}
						}
					}else{
						b.setParent(parIf.getParent());
						this.userCodingNowFunction=b; //don't put in if in main;s nesting
					}
				}
			}else{
				if(this.userCodingNowFunction!=null){ //curr not null so we need to set current to user playing and parent to curr
					b.setParent(this.userCodingNowFunction);
					this.parent=this.userCodingNowFunction;
					this.userCodingNowFunction=b;
					if(parentFunction!=null){ //insert into parent's block
						if(parentFunction.getNestedBlocks().get(begin)==null){ //nothing there put
							parentFunction.getNestedBlocks().put(begin, b);//put into parent's nesting blocks
						}else{ 
							HashMap<Integer,Block> tempHash = new HashMap<Integer,Block>();
							cascadeNumberingChanges(begin, 1, b, this.tempFunctionBlockInstructions, true); //cascade first then put into it!!
							parentFunction.getNestedBlocks().put(begin, b);
						}
					}
				}else{
					this.userCodingNowFunction=b; //don't put in if its in main's nesting
				}
			}
			return 0;
		}
	}
	

	/**
	 * Creates a new Function object, stores createdFunctionBlocks in this function, and adds it to functions list
	 */
	public int createFunction(String name){
		int temp = validFunctionName(name);
		if(temp==1){
			/////////////Error: Functions names can only consists of letters or numbers///////////
			return 1;
		}else if(temp==2){
			///////////////////////////Error: Function name already exists////////////////
			return 2;
		}
		Function newfunction= new Function(name);
		newfunction.setBlockInstructions(this.tempFunctionBlockInstructions);
		this.tempFunctionBlockInstructions= new HashMap<Integer,Block>();//reset to empty
		addFunction(newfunction); //add to this.functions list
		return 0;
	}
	/**
	 * Deletes the tempFunctionBlockInstructions
	 */
	public void clearTempFunctionBlockInstructions(){
		this.tempFunctionBlockInstructions= new HashMap<Integer,Block>();
	}

	/**
	 * Will determine whether user provided function name is unique and contains valid characters
	 * 
	 * @assumes function name may not be unique
	 * @exception none
	 * @postcondition Determines if function name is unique/valid
	 * 
	 * @param name User provided name for function
	 * @return int; 1 if function name does not consist of letters or digits, 2 if the function name is not unique && valid, 3 if unique && valid
	 */
	public int validFunctionName(String name){
		for(int i=0; i<name.length(); i++){
			char c= name.charAt(i);
			if(!Character.isLetterOrDigit(c) && c!=' '){
				return 1;
			}
		}
		for(int j=0; j<gamePlaying.functions.size(); j++){
			if(gamePlaying.functions.get(j).getName().equals(name)){
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
		gamePlaying.functions.add(functionToAdd); // +kat
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
		for(int i=0; i<gamePlaying.functions.size(); i++){
			if(gamePlaying.functions.get(i).getName().equals(name)){
				gamePlaying.functions.remove(i);
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
	public String[] getFunctions(){
		ArrayList<String> functionnames= new ArrayList<String>();

		ArrayList<Function> gameFunctions= gamePlaying.getfunction();
		for(int i=0; i<gameFunctions.size(); i++){
			functionnames.add(gameFunctions.get(i).getName());
		}
		ArrayList<String> sortedfunctions= sortAlphabetical(functionnames);
		String[] returnstring= new String[gamePlaying.functions.size()];
		for(int j=0; j<gamePlaying.functions.size(); j++){
			returnstring[j]= sortedfunctions.get(j);
		}
		return returnstring;
	}
	
	/**
	 * Returns the functions of the current game being played
	 * 
	 */
	public ArrayList<String> getFunctionsArrayList(){
		ArrayList<String> functionnames= new ArrayList<String>();
		
		ArrayList<Function> gameFunctions= gamePlaying.getfunction();
		for(int i=0; i<gameFunctions.size(); i++){
			functionnames.add(gameFunctions.get(i).getName());
		}
		ArrayList<String> sortedfunctions= sortAlphabetical(functionnames);
		
		return sortedfunctions;
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
			gerbil.setCompass('e');

			return true;
		}
		//determine if gerbil is facing North --> will face West
		if(gerbil.getFrontX()==gerbil.getX() && gerbil.getFrontY()==gerbil.getY()-1){
			gerbil.setFrontX(gerbil.getX()-1);
			gerbil.setFrontY(gerbil.getY());
			gerbil.setCompass('w');
			return true;
		}
		//determine if gerbil is facing East --> will face North
		if(gerbil.getFrontX()==gerbil.getX()+1 && gerbil.getFrontY()==gerbil.getY()){
			gerbil.setFrontX(gerbil.getX());
			gerbil.setFrontY(gerbil.getY()-1);
			gerbil.setCompass('n');
			return true;
		}
		//determine if gerbil is facing West --> will face South
		if(gerbil.getFrontX()==gerbil.getX()-1 && gerbil.getFrontY()==gerbil.getY()){
			gerbil.setFrontX(gerbil.getX());
			gerbil.setFrontY(gerbil.getY()+1);
			gerbil.setCompass('s');
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
	 * @param x pos of food to eat
	 * @param y pos of food to eat
	 * @return
	 */
	public boolean eat(int x, int y, char[][] grid){
		//create pointer to grid
		if(grid[y][x]=='k' //if food
				|| grid[y][x]=='p'
				|| grid[y][x]=='a'){
			grid[y][x]='0'; //eat
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
		return Start.StartGerbil.backend.deleteGame(gameName);
	}


	/**
	 * This method will load a given game
	 * @param gameName Name of the game to load
	 * @return True if loading is successful, otherwise False
	 */
	public Game loadGame(String gameName) {
		this.gamePlaying= Start.StartGerbil.backend.getGame(gameName);
		if(gamePlaying == null) {
			return null;
		}
		Start.StartGerbil.backend.deleteGame(gameName); // why is this deleting the game??? kat

		return this.gamePlaying;
	}

	/**
	 * Save current game
	 * @return True if save is successful, otherwise False 
	 */
	public boolean saveGame() {

		for(int j=0; j<Start.StartGerbil.backend.getGameList().size(); j++){
			if(Start.StartGerbil.backend.getGameList().get(j).getName().equals(this.gamePlaying.getName())){
				//skip adding the game to backend bc already exists
				return true;
			}
		}
		Start.StartGerbil.backend.addGame(this.gamePlaying);
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
	/*public boolean addFunctionToBlock(int begin, Function function, Block block) {
		//Note: A function is a one liner!! thus i set currDifference in cascadeNumbering Changes to 1
		Block functionblock= new Block();
		functionblock.setType(8); 
		functionblock.setlineBegin(begin);
		functionblock.setLineEnd(begin);
		functionblock.setParent(block);
		int funcNum = findFunction(function.getName());
		functionblock.setFunctionNum(funcNum);
		gamePlaying.getBlocks().get(block.getlineBegin()).getNestedBlocks().put(begin, functionblock);
		cascadeNumberingChanges(begin,1,functionblock);
		return true;

	}*/

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
		for (int i =0; i<gamePlaying.functions.size(); i++){
			if (gamePlaying.functions.get(i).getName().equals(name)){
				return i;
			}
		}
		return -1;
	}

	public void setCurrentGame(Game g) {
		this.gamePlaying=g;

	}
	

}