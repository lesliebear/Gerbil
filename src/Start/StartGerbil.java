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
		controller.createBlocks(6, 2, 0, null); //a while
		
		controller.createBlocks(3, 4, 0, null);// if statement
		controller.createBlocks(0, 6, 0, null); //add eat
		controller.createBlocks('e', 6, 1, null); //add eat
		controller.createBlocks('e', 4, 4, "There'sFood"); //end if
		controller.createBlocks(4,8,0,null);
		controller.createBlocks(2,10,0,null);
		controller.createBlocks('e',10,1,null);
		controller.createBlocks('e',8,4,"There'sNoFood");
		controller.createBlocks(5,12,0,null); //else
		controller.createBlocks(3,14,0,null);  //if inside else
		controller.createBlocks(2, 16, 0, null); //move inside if inside else
		controller.createBlocks('e', 16, 2, null); 
		controller.createBlocks('e',14,4,"There'sNoFood"); //end if inside else
		controller.createBlocks(0,18,0,null); //eat
		controller.createBlocks('e',18,1,null);//end eat
		controller.createBlocks('e',19,4,null); //end else
		controller.createBlocks('e', 2, 22, "There'sNoWall");

		controller.createBlocks(1, 1, 0, null); //turnleft = put infront of everything!
		controller.createBlocks('e', 1, 1, null);
//		System.out.println("____________________________AFTER TURN LEFT AT 0____________________");
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
		//System.out.println(controller.deleteBlock(16));
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