package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Function class to hold user created functions
 * Cannot edit functions. can only create and use them. If 
 * a function is deleted, then we parse the code to see if the function
 * deleted will be used. 
 * @author Amulya
 */
public class Function implements Serializable{
	
	/**Name of the funcion*/
	String name;
	/**Instructions in the function/body of the function */
	ArrayList<Block> instruction;
	
	/**
	 * Creates the user created function
	 * 
	 * @assumes valid name and blocks of instructions. name must be verified to not exist
	 * @exception none
	 * @postcondition creates function object that has code.
	 * 
	 * @param name Name of the function
	 * @param instruction Instructions in the function so the function body
	 */
	public Function(String name, ArrayList<Block> instruction){
		this.name=name;
		this.instruction=instruction;
	}
	
	/**
	 * Gets the name of the function that the user gave to create it
	 * 
	 * @assumes name of function exists
	 * @exception none
	 * @postcondition returns function name that is unique
	 * 
	 * @return Name of the function
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the instructions/body of the function
	 * 
	 * @assumes blocks of instructions exist
	 * @exception none
	 * @postcondition Retrieves arraylist of blocks that are called instruction
	 * 
	 * @return Body of the function so the instructions ArrayList<Blocks>
	 */
	public ArrayList<Block> getInstruction(){
		return instruction;
	}
}

