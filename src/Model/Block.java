package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Block holds instructions which are the strings of the information user enters for 
 * the gerbil to do
 * @author Amulya
 *
 */
public class Block implements Serializable{
	/**Id of the block*/
	private int id; 
	/**Instructions list in the block*/
	private ArrayList<String> instructions; //all instructions are strings.
	
	/*Blocks may have other blocks within them, if selected for deletion, 
	 * all internal should be deleted too*/
	/**Nested instructions go in this adjacency list*/
	private ArrayList<Block> nestedBlocks = new ArrayList<Block>();
	//the nested blocks is for the adjacency => if statements or nested info uses!!!
	
	/**
	 * Gets the id number of the block. 
	 * 
	 * @return the id number of the block
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Gets the arraylist of instructions for the block
	 * @return ArrayList of instructions for the block. 
	 */
	public ArrayList<String> getInstruction(){
		return this.instructions;
	}
	
	/**
	 * Gets the arraylist of nested blocks. This structure is useful 
	 * for the loop structure and the conditional statements
	 * @return ArrayList of blocks which are for nesting other blocks inside a block.
	 */
	public ArrayList<Block> getNestedBlocks(){
		return this.nestedBlocks;
	}
}


