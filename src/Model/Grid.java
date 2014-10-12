package Model;

public class Grid {
	
	/*Grid: 16x16 - wall perimeter*/
	char[][] grid = new char[16][16];
	
	public Grid(){
		while(!hasValidPath(grid)){
			randomGrid();
		}
	}
	
	public char[][] randomGrid(){
		return grid;
	}
	
	/**
	 * Checks if grid created in randomGrid is valid. ie. valid path exists
	 * 
	 * @return
	 */
	public boolean hasValidPath(char[][] grid){
		return false;
	}


	public void printGrid(){
		
	}
}

