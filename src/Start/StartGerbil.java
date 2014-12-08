package Start;
import Control.*;
import Model.*;
import View.*;
 
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
		controller.printTempGrid();
	
		controller.createBlocks(6, 1, 0, null);//while no wall
		controller.createBlocks(2, 3, 0, null);//move
		controller.createBlocks('e', 3, 1, null);//move
		controller.createBlocks(3, 4, 0, null);//if food
		controller.createBlocks(0, 6, 0, null); //eat
		controller.createBlocks('e', 6, 1, null); //eat
		controller.createBlocks('e', 4, 4, "There'sFood");//if food
		controller.createBlocks('e', 1, 8, "There'sNoWall");//while no wall
		controller.printBlocks(0, controller.getCurrGame().getBlocks());
		controller.compileBlocks();
		for(int i=0; i<controller.getFinalBlocks().size(); i++){
			System.out.println(controller.getFinalBlocks().get(i));
		}
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

