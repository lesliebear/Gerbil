package Model;

import java.util.ArrayList;

public class Block {
	/**Id of the block*/
	int id; 
	/**Instructions list in the block*/
	ArrayList<String> instructions; //all instructions are strings.
	
	/*Blocks may have other blocks within them, if selected for deletion, 
	 * all internal should be deleted too*/
	/**Nested instructions go in this adjacency list*/
	ArrayList<Block> nestedBlocks = new ArrayList<Block>();
	//the nested blocks is for the adjacency => if statements or nested info uses!!! 
}


