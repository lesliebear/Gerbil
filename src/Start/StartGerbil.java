package Start;
import Control.*;
 
/**
 * Main program.
 *
 */
public class StartGerbil {

	/** 
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		//Backend bk = new Backend();
		//System.out.println(bk.saveGames());
		//Main main = new Main();
		//checks{"There'sWall?", "There'sNoWall", "There'sFood","There'sNoFood"};
		Controller controller = new Controller();
		controller.createBlocks(0, 1, 0, null); //eat
		controller.createBlocks('e', 1, 1, null);
		controller.createBlocks(5, 2, 0, null); //add an else without if
		controller.createBlocks('e', 2, 3, null);
		controller.createBlocks(1, 1, 0, null); //turnleft
		controller.createBlocks('e', 1, 1, null);
		/**controller.createBlocks(3, 1, 0, null); //if theres no wall
		controller.createBlocks(2, 3, 0, null); //move
		controller.createBlocks('e', 3, 1, null);
		controller.createBlocks('e', 1, 4, "There'sNoWall");
		controller.createBlocks(4, 5, 0, null);
		controller.createBlocks(0,6,0,null); //add eat to the else if
		controller.createBlocks('e',6,1,null);
		controller.createBlocks('e', 5, 4, "There'sWall?"); //else if inserted!!
		
		controller.createBlocks(1, 0, 0, null); //insert turn left at line 0
		controller.createBlocks('e', 0, 1, null);*/
		controller.printBlocks(0,controller.getCurrGame().getBlocks());
		//System.out.println("_______________deleteblockstuff____________________________");
		//System.out.println(controller.deleteBlock(0));
		//controller.printBlocks(0,controller.getCurrGame().getBlocks());
		
		//Conditionals conditionals = new Conditionals("if");
		//Grid g = new Grid(17,17);		
		//SavedGames sg = new SavedGames();
		//UserFunction uf = new UserFunction();
		//Play play  = new Play();
		//DeleteFunction df = new DeleteFunction();
		//Finish f = new Finish();
		//ActionListenersControl ac = new ActionListenersControl();
		//ErrorDialog ed = new ErrorDialog("<html>You have no saved games.<br> Please select \"New Game\" to start a new game.<html>");
	}

	/**
	 * Creates all screens necessary for the program.
	 */
	public void CreateAllScreens() {

	}
}