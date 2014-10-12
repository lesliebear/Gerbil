package Control;

import java.util.ArrayList;
import java.util.LinkedList;

import Model.Block;
import Model.Function;
import Model.Game;


/*Sends stuff to Model*/
public class Controller {

	LinkedList<Block> instructionBlocks = new LinkedList<Block>();
	ArrayList<Function> functions = new ArrayList<Function>();

	public Game createGame(String name){
		return null;
	}
	
 	public boolean parseBlock(int pos){
		return false;
	}

	public boolean editBlock(int pos){
		return false;
	}

	public boolean deleteBlock(int pos){
		return false;
	}

	public boolean insertBlock(int pos){
		return false;
	}
	
	
	/*Checks for conditionals*/
	
	public boolean isthereFood(int x, int y){
		return false;
	}
	
	public boolean isthereWall(int x, int y){
		return false;
	}
	
	public boolean isthereWater(int x, int y){
		return false;
	}
	
	/*Function stuff*/
	public Function createFunction(String name){
		return null;
	}
	
	public boolean deleteFunction(String name){
		return false;
	}
	
	/*Movement stuff*/
	
	public boolean pathclearofWalls(LinkedList<Block> instructions){
		return false;
	}
	
	public boolean move(int oldX, int oldY, int newX, int newY){
		return false;
	}
	
	public boolean turnLeft(){
		return false;
	}
	
	/**
	 * Will remove item from x, y, call move()
	 * @param x pos of food to eat ; newX for gerbil
	 * @param y pos of food to eat ; newY for gerbil
	 * @return
	 */
	public boolean eat(int x, int y){
		return false;
	}
}