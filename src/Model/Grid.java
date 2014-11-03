package Model;

import java.io.Serializable;

/**
 * The Grid class that holds the information about how the grid looks by using characters to represent
 * location of fruit and gerbil. The default location of avatar is bottom left and 
 * water can (the goal) is top right. 
 * 
 * Index of characters on grid:
 * k = Pumpkin, p for pear, a for apple, w for Wall, t 
 * 
 * Gerbil object already has it's own locations but if Gerbil does step
 * on a square with fruit, the character for the fruit will become upper 
 * case as to not lose data for the fruit's location
 * 
 * We will use bottom left as starting point so it is 0,0!!
 * @author Amulya
 * */
public class Grid implements Serializable{
	
	/**
	 * 17x17 grid size because the outer edges will have walls  
	 * Grid does not have gerbil location = gerbil object has the locaiton info.
	 */
	private char[][] grid = new char[16][16];

	
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
		while(!hasValidPath(grid)){
			randomGrid();
		}
	}
	/**
	 * Only need to convert Y values (row values), X values (column numbers) are already good
	 * Takes an integer and converts it from the norm we use as 0,0 being bottom left 
	 * to an integer that works with java's array which has 0,0 at top left
	 * 
	 * 
	 * @assumes We need to access Y coordinates in grid but using Y values from the Play Screen. 
	 * @exception none
	 * @postcondition returns a converted Y value with 0,0 at top left rather than bottom left. 
	 * 
	 * @param i Integer to convert 
	 * @return Integer converted to work with java's arrays
	 */
	private int ConvertY(int i){
		return 0;
		//17-i-1?
	}
	
	/**
	 * Creates a random grid while trying to make sure the grid is playble (i.e. no walls 
	 * completely surround or obstruct path of gerbil from start to end)
	 * Also places food on grid
	 * 
	 * @assumes random grid is needed
	 * @exception none
	 * @postcondition returns randomized grid with food and walls and water placed and water can at top left
	 * 
	 * @return character array of grid with food and walls placed on it. 
	 */
	public char[][] randomGrid(){
		return grid;
		//MAKE SURE THAT WHEN CREATING GRID = the bottom left is 0,0 so where gerbil starts is 
		//at 0,0 so nothing else should be there!!!
	}
	
	/**
	 * Get's gerbil's x coordinate
	 * 
	 * @assumes valid X coordinate 
	 * @exception none
	 * @postcondition returns x coordinate of gerbil.
	 * 
	 * @return integer for gerbil's x coordinate = column
	 */
	public int getGerbilX(){
		return 0;
	}
	
	/**
	 * Get's gerbil's y coordinate
	 *
	 * @assumes valid Y coordinate
	 * @exception none
	 * @postcondition returns y coordinate of gerbil
	 * 
	 * @return integer for gerbil's y coordinate = row
	 */
	public int getGerbilY(){
		return 0;
	}
	
	/**
	 * Get's the contents of grid's x and y location
	 * 
	 * 
	 * @assumes assumes valid square content according to characters that can go on grid
	 * @exception none
	 * @postcondition returns character that is valid
	 * 
	 * @param x the column specified
	 * @param y the row specified
	 * @return the character at the location if special, else returns " "
	 */
	public char getSquareContent(int x, int y){
		return 'a';
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
	 * @return True if the grid created does have a runnable/completable course, else false
	 */
	public boolean hasValidPath(char[][] grid){
		return false;
	}

	/**
	 * Prints the grid
	 * 
	 * 
	 * @assumes debugging reasons
	 * @exception none
	 * @postcondition nothing
	 */
	public void printGrid(){
		
	}
}

