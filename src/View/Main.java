package View;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This class creates a GUI for the Gerbil Screen (Opening Screen).
 *
 */
public class Main extends Screen {

	public JButton play, instructions, exit;
	private JPanel panel, buttonPanel;
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
		
		buttonPanel = new JPanel();
	
		createScreen();
	}

	/**
	 * Creates the screen by putting the GUI components together.
	 * 
	 */
	protected void createScreen() {

		Dimension dimension = new Dimension(1024, 668);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(play);
		buttonPanel.add(Box.createRigidArea(new Dimension(50,0)));
		buttonPanel.add(instructions);
		buttonPanel.add(Box.createRigidArea(new Dimension(50,0)));
		buttonPanel.add(exit);
		buttonPanel.add(Box.createRigidArea(new Dimension(50,0)));
		panel.add(Box.createRigidArea(new Dimension(0,500)));
		panel.add(buttonPanel);
		play.setPreferredSize(new Dimension(125,85));
		play.setFont(new Font(null, Font.BOLD,20));
		instructions.setPreferredSize(new Dimension(125,65));
		instructions.setFont(new Font(null, Font.BOLD,20));
		exit.setPreferredSize(new Dimension(125,65));
		exit.setFont(new Font(null, Font.BOLD,20));

		frame.add(panel);
		frame.setSize(dimension);
		frame.setMinimumSize(dimension);
		frame.setVisible(true);
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