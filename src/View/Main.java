package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class creates a GUI for the Gerbil Screen (Opening Screen).
 *
 */
public class Main extends Screen {

	public JButton play, instructions, exit;
	private JPanel panel;
	private BufferedImage image;
	private JFrame frame;

	/**
	 * Constructor that creates all necessary GUI components.
	 * 
	 */
	@SuppressWarnings("serial")
	public Main() {

		frame = new JFrame("Gerbil");
		play = new JButton("Play");
		instructions = new JButton("Instructions");
		exit = new JButton("Exit");
		try {
			image = ImageIO.read(new File("10619975_10204553653617548_7614187758233835955_o.jpg"));
		} catch (Exception ex) {
			System.out.println("coudln't load image");
		}
		panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			}
		};
		createScreen();
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {

		Dimension dimension = new Dimension(1024, 668);
		Dimension buttonDimension = new Dimension(100,100);
		play.setSize(buttonDimension);
		play.setMinimumSize(buttonDimension);
		play.setFont(new Font(null, Font.BOLD,20));
		instructions.setSize(buttonDimension);
		instructions.setMinimumSize(buttonDimension);
		instructions.setFont(new Font(null, Font.BOLD,20));
		exit.setSize(buttonDimension);
		exit.setMinimumSize(buttonDimension);
		exit.setFont(new Font(null, Font.BOLD,20));
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(450,25,0,70);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		c.ipadx = 20;
		panel.add(play, c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 40;
		panel.add(instructions, c);
		c.gridx = 2;
		c.gridy = 0;
		c.ipady = 40;
		c.ipadx = 20;
		panel.add(exit, c);
		frame.add(panel);
		frame.setSize(dimension);
		frame.setMinimumSize(dimension);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}

	/**
	 * Shows the screen.
	 * 
	 */
	public void show() {

		frame.setVisible(true);
	}

	/**
	 * Hides the screen.
	 * 
	 */
	public void hide() {

		frame.setVisible(false);
	}

	public void addEventListeners(ActionListener listener) {
		
		play.addActionListener(listener);
		instructions.addActionListener(listener);
		exit.addActionListener(listener);
	}
}