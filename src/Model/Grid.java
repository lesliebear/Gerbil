package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.Grid.Node;

/**
 * The Grid class that holds the information about how the grid looks by using characters to represent
 * location of fruit and gerbil. The default location of avatar is bottom left and 
 * water can (the goal) is top right. 
 * 
 * Index of characters on grid:
 * k = Pumpkin, p for pear, a for apple, w for Wall, t for water container
 * 
 * Gerbil object already has it's own locations but if Gerbil does step
 * on a square with fruit, the character for the fruit will become upper 
 * case as to not lose data for the fruit's location
 * 
 * Note: In Java, for 2d Arrays (0,0) is in the top left of array.
 * @author Amulya
 * */
public class Grid implements Serializable{

	/**17x17 grid size because the outer edges will have walls  
	 * Grid does not have gerbil location = gerbil object has the location info.*/
	private char[][] grid;
	HashMap<Double,Node> visited;
	HashMap<String,Node> fruitCoordinates = new HashMap<String, Node>(); // made this
	ArrayList<Node> fruitLocTest = new ArrayList<Node>();

	/**
	 * Creates a random grid that can still be completed (i.e. no walls blocking path 
	 * of gerbil from all sides) for standard version 
	 * In Fancy, we will have the user built screen that will allow user to 
	 * customize and build the grid.
	 * 
	 * 
	 * @assumes random grid needs to be created
	 * @exception none
	 * @postcondition creates a grid we can access and can still play
	 * 
	 */
	public Grid(int rows, int columns){	

		grid = new char[rows][columns];
		initGrid();
	}

	/**
	 * Initializes the grid field in this class by making sure the grid is playable
	 * meaning the gerbil has ways to get to every fruit and water can. 
	 * 
	 * @assumes grid object has been created but is empty
	 * @exception none
	 * @postcondition populates the grid object with walls, fruits, and water can placed
	 * in random location with a valid path to them making the grid playable
	 * 
	 */
	public void initGrid(){
		//do{//put walls all around and init 0s = empty grid area.
		for (int i = 0; i<grid.length;i++){ //have to clear the grid before randomizing it
			for (int j = 0; j<grid[0].length;j++){ 
				if((j==0)||(j==grid[0].length-1) || (i==0)||(i==grid.length-1)){
					grid[i][j]='w'; //wall surrounds entire grid. 
				}else{
					grid[i][j] = '0'; //empty grid area.
				}
			}
		}
		//	grid[1][1]='a';
		//grid[2][1]='w';
		//grid[2][2]='w';
		//grid[1][2]='w';
		//	grid[1][13]='w';
		//grid[3][15]='w';
		//grid[3][14]='w';
		//grid[3][13]='w';
		//grid[2][13]='w';


		randomGrid(); //places walls and fruit
		int size;
		size = fruitCoordinates.size();
		grid[1][this.grid[0].length-2]='t'; //place water can
		
		grid[1][1] = 'k';	//
		grid[2][1] = 'w';	//
		grid[1][2] = 'w';	//
		fruitCoordinates.remove(Integer.toString(1) + Integer.toString(1));	//
		fruitCoordinates.put(Integer.toString(1) + Integer.toString(1), new Node(1, 1)); //
		printGrid();
		System.out.println("Valid Grid: " + hasValidPath(grid.length-2, 1));
		checkFruitsTopRight(grid.length - 2, 1);
		checkFruitsDownLeft(1, grid[0].length - 2);
		size = fruitCoordinates.size();
		if(size == 0) {
			System.out.println("Fruits have valid paths");
		}
		else {
			System.out.println("At least one fruit does not have valid path");
		}
		//	}while(((hasValidPath(this.grid.length-2,1))==false) //start from bottom left corner = gerbil location 
		//		&& (!fruitsHaveValidPath())); //reach all fruit

	}

	/**
	 * Ensures that the grid has valid fruit placement with fruit in locations reachable by Gerbil
	 * 
	 * @assumes Fruit locations have not been validated and might be surrounded by walls
	 * making it not possible for the Gerbil to reach
	 * @exception none
	 * @postcondition validates or invalidates graph if fruit cannot be reached.
	 * 
	 * @return True if all fruits can be reached by Gerbil else false.
	 */
	public boolean fruitsHaveValidPath(){
		for (Node n: fruitLocTest){
			if((getToFruitUpRight(grid.length-2,1,n.row,n.col, getSquareContent(n.row, n.col))&&
					getToFruitDownLeft(grid.length-2,1,n.row,n.col,getSquareContent(n.row, n.col))==false)){
				System.out.println("location it fails: "+n.row+" , "+n.col);
				return false;
			}
		}

		return true;
	}

	/**
	 * Creates a random grid while trying to make sure the grid is playble (i.e. no walls 
	 * completely surround or obstruct path of gerbil from start to end)
	 * Also places food on grid
	 * 
	 * @assumes random grid is needed and just changes the field
	 * @exception none
	 * @postcondition returns randomized grid with food and walls and water placed and water can at top left
	 */
	public void randomGrid(){
		for (int b = 0; b <2*(this.grid.length-2); b++){//put in 30 walls as obstacles 
			int R = (int)(Math.random()*(grid.length-2)) + 1;  //gets random row number between 1 and the number of rows-1
			int S = (int)(Math.random()*(grid[0].length-2)) + 1;  // gets random col number between 1 and the number of columns-1
			if (((R!=grid[0].length-2)&&(S!=1) &&(R!=1) && (S!=grid.length-2)) && (grid[R][S]=='0')){ 
				//if empty and not in location of water can or gerbil
				this.grid[R][S]='w';
			} 
			else{
				b--; //do not increment b without placing the total walls needed!
			}
		}
		placeFruitsRandomly('k');//pumpkin
		placeFruitsRandomly('p'); //pear
		placeFruitsRandomly('a'); //apple
	}

	/**
	 * Places Fruit represented by character c onto the grid location that are empty. 
	 * The number of fruit is (2 * grid row that can be accessed by avatar )/ 3)
	 * 
	 * @assumes Valid character is entered and there are empty locations on grid
	 * @exception none
	 * @postcondition grid now has fruits placed onto it with character c representing the fruit
	 * @param c The character representing the fruit to place onto the grid.
	 */
	public void placeFruitsRandomly(char c){

		int numberOfFruit = 10;//(int)((2.0*(this.grid.length-2.0)/3.0));
		int count = 0;
		while(count != numberOfFruit){
			int R = (int)(Math.random()*(grid.length-2)) + 1;  //gets random row number between 1 and the number of rows-1
			int S = (int)(Math.random()*(grid[0].length-2)) + 1;  // gets random col number between 1 and the number of columns-1
			if (grid[R][S]=='0'){ //if it is empty, add the fruit
				grid[R][S]=c;
				fruitCoordinates.put(Integer.toString(R) + Integer.toString(S), new Node(R,S));
				count++;
			}
		}
	}

	/**
	 * Get's the contents of grid's x and y location
	 * 
	 * @assumes assumes valid square content according to characters that can go on grid
	 * @exception none
	 * @postcondition returns character that is valid
	 * 
	 * @param y the row specified
	 * @param x the column specified
	 * @return the character at the location if special, else returns " "
	 */
	public char getSquareContent(int y, int x){
		return this.grid[y][x]; //y is row and x is column!
	}

	/**
	 * Tests if all fruit are reachable else it would be not a valid program
	 * 
	 * @param Y The row to check in
	 * @param X The column to check in
	 * @param goalY The row to reach
	 * @param goalX The column to reach
	 * @return check if we reach the location of the fruit. 
	 */
	public boolean getToFruitUpRight(int Y, int X, int goalY, int goalX, char c){
		if ((Y==goalY) && (X == goalX) && (this.getSquareContent(Y, X)==c)){ //location o the fruit
			return true;
		}else if (this.getSquareContent(Y, X)=='w'){ //wall so cannot move more in that direction
			return false;
		}else{
			//4 normal spots...not corners
			return (getToFruitUpRight(Y-1,X,goalY,goalX,c) ||
					getToFruitUpRight(Y,X+1,goalY,goalX,c));
		}
	}

	/**
	 * Tests if all fruit are reachable else it would be not a valid program
	 * 
	 * @param Y The row to check in
	 * @param X The column to check in
	 * @param goalY The row to reach
	 * @param goalX The column to reach
	 * @return check if we reach the location of the fruit. 
	 */
	public boolean getToFruitDownLeft(int Y, int X, int goalY, int goalX, char c){
		if ((Y==goalY) && (X == goalX) && (this.getSquareContent(Y, X)==c)){ //location o the fruit
			return true;
		}else if (this.getSquareContent(Y, X)=='w'){ //wall so cannot move more in that direction
			return false;
		}else{
			//4 normal spots...not corners
			return (getToFruitDownLeft(Y,X-1,goalY,goalX,c) ||
					getToFruitDownLeft(Y+1,X,goalY,goalX,c));
		}
	}

	/* Did this */
	public void checkFruitsTopRight(int Y, int X) {
		
		if (grid[Y][X]=='w'){ //wall so cannot move more in that direction
			return;
		}
		else {
			if(grid[Y][X] == 'k' || grid[Y][X] == 'p' || grid[Y][X] == 'a') {//get to water container so has valid path
				fruitCoordinates.remove(Integer.toString(Y) + Integer.toString(X));
			}
			checkFruitsTopRight(Y-1,X);  
			checkFruitsTopRight(Y,X+1); 
		}
	}
	
	/* Did this */
	public void checkFruitsDownLeft(int Y, int X) {
		
		if (grid[Y][X]=='w'){ //wall so cannot move more in that direction
			return;
		}
		else {
			if(grid[Y][X] == 'k' || grid[Y][X] == 'p' || grid[Y][X] == 'a') {//get to water container so has valid path
				fruitCoordinates.remove(Integer.toString(Y) + Integer.toString(X));
			}
			checkFruitsDownLeft(Y+1,X);  
			checkFruitsDownLeft(Y,X-1); 
		}
	}
	/**
	 * Checks if grid created in randomGrid is valid. ie. valid path exists
	 * from start to finish of course. The course has to have a path from start to finish
	 * to be completable. 
	 * 
	 * @assumes assumes grid has been made but we don't know if it is playable
	 * @exception none
	 * @postcondition grid is playable so validated as such
	 * 
	 * @param Y The Row to check 
	 * @param X The Column to check
	 * @return True if the grid created does have a runnable/completable course, else false
	 */
	public boolean hasValidPath(int Y, int X){
		if (grid[Y][X]=='w'){ //wall so cannot move more in that direction
			return false;
		}
		else if((this.getSquareContent(Y, X) == 't') ){//get to water container so has valid path
			return true;
		}else {
			return hasValidPath(Y-1,X) ||  
					//hasValidPath(Y+1,X+1) ||
					hasValidPath(Y,X+1); 
		}
	}

	/**
	 * Prints the grid 
	 * 
	 * @assumes debugging reasons
	 * @exception none
	 * @postcondition nothing
	 */
	public void printGrid(){
		for (int i =0; i<this.grid.length;i++){
			for (int j = 0; j<this.grid[0].length;j++){
				System.out.print(this.grid[i][j]+" "); //prints out row in one line
			}
			System.out.println(); //new line for new row. 
		}
	}//close print board

	public class Node {
		int row, col;
		public Node(int row, int col){
			this.row = row;
			this.col=col;
		}
	}

}

