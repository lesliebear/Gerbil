package Model;

/**
 * The Grid class that holds the information about how the grid looks by using characters to represent
 * location of fruit and gerbil. The default location of avatar is bottom left and 
 * water can (the goal) is top right. 
 * 
 * Index of characters on grid:
 * k = Pumpkin, g for Gerbil, p for pear, a for apple, w for Wall, t 
 * 
 * Gerbil object already has it's own locations but if Gerbil does step
 * on a square with fruit, the character for the fruit will become upper 
 * case as to not lose data for the fruit's location
 * 
 * */
public class Grid {
	
	/**
	 * 17x17 grid size because the outer edges will have walls  
	 */
	char[][] grid = new char[16][16];
	
	/**
	 * Creates a random grid that can still be completed (i.e. no walls blocking path 
	 * of gerbil from all sides) for standard version 
	 * In Fancy, we will have the user built screen that will allow user to 
	 * customize and build the grid.
	 */
	public Grid(){
		while(!hasValidPath(grid)){
			randomGrid();
		}
	}
	
	/**
	 * Creates a random grid while trying to make sure the grid is playble (i.e. no walls 
	 * completely surround or obstruct path of gerbil from start to end)
	 * Also places food on grid
	 * @return character array of grid with food and walls placed on it. 
	 */
	public char[][] randomGrid(){
		return grid;
	}
	
	/**
	 * Get's gerbil's x coordinate
	 * @return integer for gerbil's x coordinate = column
	 */
	public int getGerbilX(){
		return 0;
	}
	
	/**
	 * Get's gerbil's y coordinate
	 * @return integer for gerbil's y coordinate = row
	 */
	public int getGerbilY(){
		return 0;
	}
	
	/**
	 * Get's the contents of grid's x and y location
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
	 * @return True if the grid created does have a runnable/completable course, else false
	 */
	public boolean hasValidPath(char[][] grid){
		return false;
	}

	/**
	 * Prints the grid
	 */
	public void printGrid(){
		
	}
}

