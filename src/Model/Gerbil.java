package Model;

/**
 * The Gerbil Avatar object that stores the information about where the Gerbil is located
 * */
public class Gerbil {
	
	/**
	 * The column in front of the gerbil (used to help find the direction gerbil is facing)
	 */
	int frontX;  //use this to compare with currX and currY to figure out which direciton gerbil is facing
	/**
	 * The row in front of the gerbil (used to help find the direction gerbil is facing)
	 */
	int frontY;
	
	/**
	 * The column of the gerbil (used to help find the location and direction gerbil is facing)
	 */
	int currX; 
	/**
	 * The row of the gerbil (used to help find the location and direction gerbil is facing)
	 */
	int currY;
	
	/**
	 * Constructor for the object. The gerbil starts off in the bottom left corner of grid 
	 * and facing upwards. 
	 */
	Gerbil(){
		this.frontX = 0;
		this.frontY=1;
		this.currX=0;
		this.currY=0;
	}
	

}

