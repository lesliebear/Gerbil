package View;

/**
 * Interface that includes 3 methods: CreateScreen, show, and hide. All must be implemented before being called.
 *
 */
public interface Screen {

	/**
	 * Method to be implemented: Creates the screen.
	 * 
	 */
	public void CreateScreen();
	
	/**
	 * Method to be implemented: Shows the screen.
	 * 
	 */
	public void show();
	
	/**
	 * Method to be implemented: Hides the screen.
	 * 
	 */
	public void hide();
}
