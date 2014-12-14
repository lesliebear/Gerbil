package Start;
import Control.*;
import Model.Backend;


/**
 * Main program.
 * 
 * 
 */
public class StartGerbil {
	public static Backend backend;
	public static Controller controller;
	public static ActionListenersControl alc;
	
	/** 
	 * Main method.
	 * @param args
	 */
	public static void main(String[] args) {
		backend = new Backend();
		//alc = new ActionListenersControl();

		try{
			backend.setGameList(backend.loadSavedGames());
			
		}catch (Exception e1) { //if txt file stuff is cleared, it has issues so make a dummy user, and delete the dummy user for it to work
			System.out.println("There are no saved games.");
		}

		controller = new Controller();
		alc = new ActionListenersControl();

	}

	/**
	 * Creates all screens necessary for the program.
	 */
	public void CreateAllScreens() {

	}
}