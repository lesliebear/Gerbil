package View;
 
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * This class creates a GUI for the Instruction Screen (Help screen).
 *
 */
public class Instructions extends Screen{

	JFrame frame;
	JButton ok;
	JTextArea gamePlayText, LoadSaveText;
	
	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	public Instructions() {
		 
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	
	protected void createScreen() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Shows the screen.
	 * 
	 */
	public void show() {
	
		frame.setVisible(true);;
	}
	
	/**
	 * Hides the screen.
	 * 
	 */
	public void hide() {
	
		frame.setVisible(false);
	}

	/**
	 * Enable the screen
	 */
	public void enable() {
		
		frame.setEnabled(true);
	}

	/**
	 * Disable to screen
	 */
	public void disable() {

		frame.setEnabled(false);
	}
	
	public void addEventListeners(ActionListener listener) {
		
		ok.addActionListener(listener);
	}
}