package View;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 * Abstract class that includes 4 methods: createScreen, show, hide, addEventListeners. All must be implemented.
 *
 */
@SuppressWarnings("serial")
public abstract class Screen {

	/**
	 * Method to be implemented: Creates the screen.
	 * 
	 */
	protected abstract void createScreen();
	
	/**
	 * Method to be implemented: Shows the screen.
	 * 
	 */
	public abstract void show();
		
	/**
	 * Method to be implemented: Hides the screen.
	 * 
	 */
	public abstract void hide();
	
	/**
	 * Method to add event handlers
	 */
	public abstract void addEventListeners(ActionListener listener);
}
