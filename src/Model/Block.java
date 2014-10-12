package Model;

import java.util.LinkedList;

public class Block {
	
	/*Should the ins be strings???? */
	String instruction;
	
	/*Blocks may have other blocks within them, if selected for deletion, all internal should be deleted too*/
	LinkedList<Block> nestedBlocks = new LinkedList<Block>();
}
