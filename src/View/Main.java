package View;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * This class creates a GUI for the Gerbil Screen (Opening Screen).
 *
 */
public class Main extends JPanel implements Screen {
	
	public JButton button1, button2;
	private JPanel panel;
	private BufferedImage image;
	private JFrame frame;

	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	@SuppressWarnings("serial")
	public Main() {
		
		frame = new JFrame("Test");
		 try {                
	          image = ImageIO.read(new File("10619975_10204553653617548_7614187758233835955_o.jpg"));
	       } catch (IOException ex) {
	            
	       }
		 
		 createScreen();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	public void createScreen() {	
		
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.setPreferredSize(new Dimension(300,300));
		frame.setSize(new Dimension(300,300));
		frame.setVisible(true);
		
	}

	/**
	 * Shows the screen.
	 * 
	 */
	public void show() {
	
	}
	
	/**
	 * Hides the screen.
	 * 
	 */
	public void hide() {
	
	}

	public void addEventListeners(ActionListener listener) {
		// TODO Auto-generated method stub
		
	}
}