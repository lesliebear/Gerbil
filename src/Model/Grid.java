package Model;

import java.io.Serializable;

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
	public Grid(){
		grid = new char[17][17];
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
		do{//walls all around
			for (int i = 0; i<grid.length;i++){ //have to clear the grid before randomizing it
				for (int j = 0; j<grid[0].length;j++){ 
					if((j==0)||(j==this.grid[0].length-1) || (i==0)||(i==this.grid.length-1)){
						grid[i][j]='w'; //wall surrounds entire grid. 
					}
					grid[i][j] = '0'; //empty grid area.
				}
			}
			randomGrid(); //places walls and fruit
			grid[1][this.grid[0].length-2]='t'; //place water can
		}while((!hasValidPath(this.grid.length-2,1)) //start from bottom left corner = gerbil location 
				&& (!fruitsHaveValidPath())); //reach all fruit 
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
		for (int i = 1; i<this.grid.length-2; i++){
			for (int j = 1; j<this.grid[0].length-2;j++){
				if((grid[i][j]!='0') && (grid[i][j]!='t') && (grid[i][j]!='w')){
					if(!getToFruit(this.grid.length-2,1,i,j, grid[i][j])){
						return false;
					}
				}
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
			int R = (int)(Math.random()*(this.grid.length-2)) + 1;  //gets random row number between 1 and the number of rows-1
			int S = (int)(Math.random()*(this.grid[0].length-2)) + 1;  // gets random col number between 1 and the number of columns-1
			if (((R!=this.grid[0].length-2)&&(S!=0) &&(R!=1) && (S!=this.grid.length-2)) && (this.grid[R][S]=='0')){ 
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
		for (int b = 0; b <(2*(this.grid.length-2))/3; b++){//put in grid to eat and floor due to integer division
			int R = (int)(Math.random()*(this.grid.length-2)) + 1;  //gets random row number between 1 and the number of rows-1
			int S = (int)(Math.random()*(this.grid[0].length-2)) + 1;  // gets random col number between 1 and the number of columns-1
			if (this.grid[R][S]=='0'){ //if it is empty, add the fruit
				this.grid[R][S]=c;
			}
			else{
				b--; //do not increment b without placing the total fruit needed!
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
	public boolean getToFruit(int Y, int X, int goalY, int goalX, int c){
		if ((Y==goalY) && (X == goalX) && (grid[Y][X]==c)){ //location o the fruit
			return true;
		}else if ((Y<1) || (X<1)|| (Y>this.grid.length-2) || (X >this.grid[0].length-2)){ //make sure within bounds of grid
			return false; 
		}else if (grid[Y][X]=='w'){ //wall so cannot move more in that direction
			return false;
		}else{
			getToFruit(Y-1,X,goalY,goalX,c); //4 normal spots...not corners
			getToFruit(Y,X-1,goalY,goalX,c);
			getToFruit(Y+1,X,goalY,goalX,c);
			getToFruit(Y,X+1,goalY,goalX,c);

			getToFruit(Y+1,X+1,goalY,goalX,c); //corners
			getToFruit(Y-1,X-1,goalY,goalX,c);
			getToFruit(Y+1,X-1,goalY,goalX,c);
			getToFruit(Y-1,X+1,goalY,goalX,c);
			return false; //could not get to fruit by now => false
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
		if((Y==1) && (X==this.grid[0].length-2) ){//get to water container so has valid path
			return true;
		}
		else if ((Y<1) || (X<1)|| (Y>this.grid.length-2) || (X >this.grid[0].length-2)){ //make sure within bounds of grid
			return false; 
		}
		else if (grid[Y][X]=='w'){ //wall so cannot move more in that direction
			return false;
		}
		else {
			hasValidPath(Y-1,X); //4 normal spots...not corners
			hasValidPath(Y,X-1);
			hasValidPath(Y+1,X);
			hasValidPath(Y,X+1);

			hasValidPath(Y+1,X+1); //corners
			hasValidPath(Y-1,X-1);
			hasValidPath(Y+1,X-1);
			hasValidPath(Y-1,X+1);
			return false; //could not get to water container by now => false
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

}

