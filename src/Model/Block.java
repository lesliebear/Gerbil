package Model;

import java.util.ArrayList;

public class Block {
	int id; //NEED THIS FOR DELETE Since we don't want to 
	ArrayList<String> instructions; //all instructions are strings.
	
	/*Blocks may have other blocks within them, if selected for deletion, all internal should be deleted too*/
	ArrayList<Block> nestedBlocks = new ArrayList<Block>();
	//the nested blocks is for the adjacency => if statements or nested info uses!!! 
}


