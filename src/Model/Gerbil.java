package Model;

import java.io.Serializable;

/**
 * The Gerbil Avatar object that stores the information about where the Gerbil is located
 * @author Amulya
 * */
public class Gerbil implements Serializable{
	
	/**
	 * The column in front of the gerbil (used to help find the direction gerbil is facing)
	 */
	private int frontX;  //use this to compare with currX and currY to figure out which direciton gerbil is facing
	/**
	 * The row in front of the gerbil (used to help find the direction gerbil is facing)
	 */
	private int frontY;
	
	/**
	 * The column of the gerbil (used to help find the location and direction gerbil is facing)
	 */
	private int currX; 
	/**
	 * The row of the gerbil (used to help find the location and direction gerbil is facing)
	 */
	private int currY;
	
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
	
	/**
	 * Gets the current X value of Gerbil
	 * @return X value of Gerbil's location
	 */
	public int getX(){
		return currX;
	}
	/**Gets the current Y value of Gerbil
	 * @return Y value of Gerbil's Location
	 */
	public int getY(){
		return currY;
	}
	/**Gets the block in front of gerbil's X value 
	 * @return X value of the block in front of Gerbil
	 */
	public int getFrontX(){
		return frontX;
	}
	/**Gets the block in front of gerbil's Y value 
	 * @return Y value of the block in front of Gerbil
	 */
	public int getFrontY(){
		return frontY;
	}
	
	/**
	 * Sets Gerbil's X location
	 * @param i The integer to set Gerbil's X current location
	 */
	public void setX(int i){
		this.currX= i;
	}
	/**
	 * Sets Gerbil's Y location
	 * @param i The integer to set Gerbil's Y current location
	 */
	public void setY(int i){
		this.currY= i;
	}
	/**
	 * Sets Gerbil's front block's X value
	 * @param i The integer to set Gerbil's front block's X value
	 */
	public void setFrontX(int i){
		this.frontX= i;
	}
	/**
	 * Sets Gerbil's front block's Y value
	 * @param i The integer to set Gerbil's front block's Y value
	 */
	public void setFrontY(int i){
		this.frontY= i;
	}


	

}

